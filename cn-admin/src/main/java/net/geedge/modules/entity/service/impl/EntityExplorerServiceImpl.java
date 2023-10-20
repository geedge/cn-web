package net.geedge.modules.entity.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import com.alibaba.fastjson.JSONObject;
import net.geedge.common.utils.Constant;
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
import net.sf.jsqlparser.util.deparser.ExpressionDeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Auther: lijinyang
 * @Date: 2023/07/04/14:23
 */
@Service("EntityExplorerService")
public class EntityExplorerServiceImpl implements EntityExplorerService {

    @Autowired
    private SysConfigService sysConfigService;

    private Log log = Log.get();

    @Override
    public String parseExpression(String resource, String dataName){
        String expression = "";
        if(ObjectUtil.isNotEmpty(resource)) {
            List<Map<String, Object>> searchColumns = getSearchColumns(dataName);
            try {
                Expression expr = CCJSqlParserUtil.parseCondExpression(resource);
                ExpressionDeParser expressionDeParser =  new ExpressionDeParser() {
                    @Override
                    public void visit(Column column) {
                        for (Map<String, Object> map : searchColumns) {
                            if(column.getColumnName().contains("\"")) {
                                String newColumn = column.getColumnName().replace("\"", "").trim().replace(" ",".");
                                column.setColumnName(newColumn);
                            }
                            String columnName = "";
                            if(ObjectUtil.isNotEmpty(column.getTable())) {
                                columnName = column.getTable() + "." + column.getColumnName();
                            }
                            if (column.getColumnName().equals((String)map.get("label")) || columnName.equals((String)map.get("label"))){
                                Map<String, Object> connector =(Map<String, Object>) map.get("connector");
                                String table = (String)connector.get("alias");
                                Table newTable = new Table(table);
                                column.setTable(newTable);
                                column.setColumnName((String) map.get("name"));
                                break;
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
    public Boolean isAssociationQuery(String resource, String dataName) {
        if(ObjectUtil.isNotEmpty(resource)) {
            List<Map<String, Object>> searchColumns = getSearchColumns(dataName);
            Set<String> catalogs = new TreeSet<>();
            try {
                Expression expr = CCJSqlParserUtil.parseCondExpression(resource);
                ExpressionDeParser expressionDeParser =  new ExpressionDeParser() {
                    @Override
                    public void visit(Column column) {
                        for (Map<String, Object> map : searchColumns) {
                            if (map.get("name").equals(column.getColumnName())){
                                Map<String, Object> connector =(Map<String, Object>) map.get("connector");
                                String catalog = (String) connector.get("catalog");
                                catalogs.add(catalog);
                            }
                        }
                    }
                };
                expr.accept(expressionDeParser);
                if(catalogs.size() > 1) {
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

    private List<Map<String, Object>> getSearchColumns(String dataName){
        String entityExploreConfigJson = sysConfigService.getValue("schema_explore");
        HashMap entityExploreConfigs = JSONObject.parseObject(entityExploreConfigJson, HashMap.class);
        Map<String, Object> entityMetadata = (Map<String, Object>)entityExploreConfigs.get(dataName);
        List<Map<String, Object>> searchColumns = (List<Map<String, Object>>) entityMetadata.get("searchColumns");
        return searchColumns;
    }
}
