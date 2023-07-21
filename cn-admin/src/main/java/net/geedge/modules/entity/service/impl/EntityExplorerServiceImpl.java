package net.geedge.modules.entity.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import com.alibaba.fastjson.JSONObject;
import net.geedge.common.utils.Constant;
import net.geedge.common.utils.TrinoConnectionPool;
import net.geedge.modules.entity.service.EntityExplorerService;
import net.geedge.modules.sys.service.SysConfigService;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.util.deparser.ExpressionDeParser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/07/04/14:23
 */
@Service("EntityExplorerService")
public class EntityExplorerServiceImpl implements EntityExplorerService {

    @Autowired
    private TrinoConnectionPool trinoConnectionPool;

    @Autowired
    private SysConfigService sysConfigService;

    private Log log = Log.get();

    @Override
    public String parseExpression(String resource){
        String expression = "";
        if(ObjectUtil.isNotEmpty(resource)) {
            List<Map<String, Object>> searchColumns = getSearchColumns();
            try {
                Expression expr = CCJSqlParserUtil.parseCondExpression(resource);
                ExpressionDeParser expressionDeParser =  new ExpressionDeParser() {
                    @Override
                    public void visit(Column column) {
                        for (Map<String, Object> map : searchColumns) {
                            if (ObjectUtil.isNotEmpty(column.getTable()) && map.get("label").equals(StrUtil.concat(true,column.getTable().toString(),".",column.getColumnName()))) {
                                String table = StringUtils.substringBefore(map.get("name").toString(),".");
                                String columnName = StringUtils.substringAfter(map.get("name").toString(),".");
                                Table newTable = new Table(table);
                                column.setTable(newTable);
                                column.setColumnName(columnName);
                            } else if(ObjectUtil.isEmpty(column.getTable()) && map.get("label").equals(column.getColumnName())){
                                column.setColumnName((String) map.get("name"));
                            }
                        }
                    }
                };
                expr.accept(expressionDeParser);
                expression = expr.toString();
            } catch (JSQLParserException e) {
                e.printStackTrace();
            }
        }
        return expression;
    }

    @Override
    public Boolean isAssociationQuery(String resource) {
        if(ObjectUtil.isNotEmpty(resource)) {
            List<String> fields = new ArrayList<>();
            try {
                Expression expr = CCJSqlParserUtil.parseCondExpression(resource);
                ExpressionDeParser expressionDeParser =  new ExpressionDeParser() {
                    @Override
                    public void visit(Column column) {
                        if (ObjectUtil.isNotEmpty(column.getTable())) {
                            fields.add(StrUtil.concat(false,column.getTable().toString(),".",column.getColumnName()));
                        }
                    }
                };
                expr.accept(expressionDeParser);
                if(ObjectUtil.isNotEmpty(fields)) {
                    return true;
                }
            } catch (JSQLParserException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> getAssociationQueryParam(String resource) {
        Map<String, Object> param = new HashMap<>();
        if(ObjectUtil.isNotEmpty(resource)) {
            try {
                Expression expr = CCJSqlParserUtil.parseCondExpression(resource);
                expr.accept(new ExpressionVisitorAdapter() {
                    @Override
                    public void visit(EqualsTo equalsTo) {
                        if(equalsTo.getLeftExpression().toString().contains(".")) {
                            param.put(equalsTo.getLeftExpression().toString(), equalsTo.getLeftExpression().toString() + " = " + equalsTo.getRightExpression().toString());
                        }
                    }
                    @Override
                    public void visit(InExpression inExpression) {
                        if(inExpression.getLeftExpression().toString().contains(".")) {
                            param.put(inExpression.getLeftExpression().toString(), inExpression.getLeftExpression().toString() + " IN " + inExpression.getRightItemsList().toString());
                        }
                    }
                });
            } catch (JSQLParserException e) {
                e.printStackTrace();
            }
        }
        return param;
    }

    @Override
    public String getBasicQueryFilter(String resource) {
        StringBuffer stringBuffer = new StringBuffer();
        if(ObjectUtil.isNotEmpty(resource)) {
            try {
                Expression expr = CCJSqlParserUtil.parseCondExpression(resource);
                expr.accept(new ExpressionVisitorAdapter() {
                    @Override
                    public void visit(EqualsTo equalsTo) {
                        if(!equalsTo.getLeftExpression().toString().contains(".")) {
                            stringBuffer.append(StrUtil.concat(false," AND ",Constant.ENTITY_RELATION_TABLE_ALIAS,".",equalsTo.getLeftExpression().toString()," = ",equalsTo.getRightExpression().toString()));
                        }
                    }
                    @Override
                    public void visit(InExpression inExpression) {
                        if(!inExpression.getLeftExpression().toString().contains(".")) {
                            stringBuffer.append(StrUtil.concat(false," AND ",Constant.ENTITY_RELATION_TABLE_ALIAS,".",inExpression.getLeftExpression().toString()," IN ",inExpression.getRightItemsList().toString()));
                        }
                    }
                });
            } catch (JSQLParserException e) {
                e.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }

    private List<Map<String, Object>> getSearchColumns(){
        String entityExploreConfigJson = sysConfigService.getValue("schema_entity_explore");
        HashMap entityExploreConfigs = JSONObject.parseObject(entityExploreConfigJson, HashMap.class);
        Map<String, Object> enityMetadata = (Map<String, Object>)entityExploreConfigs.get("enityMetadata");
        List<Map<String, Object>> searchColumns = (List<Map<String, Object>>) enityMetadata.get("searchColumns");
        return searchColumns;
    }

    @Override
    public List<Map<String, Object>> gatTrinoResponseResult(String sql){
        List<Map<String, Object>> data = new ArrayList<>();
        List<String> column = null;
        try {
            column = getColumnList(sql);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = trinoConnectionPool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            int columnNum = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Map<String, Object> obj = new HashMap<>();
                for(int i = 0; i < columnNum; i++) {
                    obj.put(column.get(i),rs.getObject(i+1));
                }
                data.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs!=null) {
                    rs.close();
                }
                if (stmt!=null) {
                    stmt.close();
                }
                if (conn!=null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return data;
    }


    private List<String> getColumnList(String sql) throws JSQLParserException {
        List<String> column = new ArrayList<>();
        net.sf.jsqlparser.statement.Statement statement = CCJSqlParserUtil.parse(sql);
        Select selectStatement = (Select) statement;
        PlainSelect selectBody = (PlainSelect) selectStatement.getSelectBody();
        List<SelectItem> selectItems = selectBody.getSelectItems();
        for (SelectItem selectItem: selectItems) {
            selectItem.accept(new SelectItemVisitorAdapter(){
                @Override
                public void visit(SelectExpressionItem item){
                    column.add(item.getAlias().getName());
                }
            });
        }
        return column;
    }
}
