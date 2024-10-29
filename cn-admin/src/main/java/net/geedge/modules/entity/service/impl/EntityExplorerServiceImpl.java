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
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.util.deparser.ExpressionDeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
                ExpressionDeParser expressionDeParser = null;
                if("entityMetadata".equals(dataName)) {
                    expressionDeParser = parseEntityMetadata(searchColumns);
                } else if("securityEventMetadata".equals(dataName)) {
                    expressionDeParser = parseSecurityEventMetadata(searchColumns);
                }
                expr.accept(expressionDeParser);
                expression = expr.toString();
            } catch (JSQLParserException e) {
                e.printStackTrace();
            }
        }
        return expression;
    }

    private ExpressionDeParser parseEntityMetadata(List<Map<String, Object>> searchColumns) {
        ExpressionDeParser result =  new ExpressionDeParser() {
            @Override
            public void visit(Column column) {
                removeDoubleQuotes(column);
                String columnName = "";
                if(ObjectUtil.isNotEmpty(column.getTable())) {
                    columnName = column.getTable() + "." + column.getColumnName();
                }
                for (Map<String, Object> map : searchColumns) {
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
        return result;
    }

    private ExpressionDeParser parseSecurityEventMetadata(List<Map<String, Object>> searchColumns) {
        ExpressionDeParser result =  new ExpressionDeParser() {
            @Override
            public void visit(Column column) {
                removeDoubleQuotes(column);
                for (Map<String, Object> map : searchColumns) {
                    if (column.getColumnName().equals((String)map.get("label"))){
                        column.setColumnName((String) map.get("name"));
                        break;
                    }
                }
            }
        };
        return result;
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

    @Override
    public List<String> getPrimayForFilter(String resource, String dataName) {
        List<String> primay = new ArrayList<>();
        if(ObjectUtil.isNotEmpty(resource)) {
            List<Map<String, Object>> searchColumns = getSearchColumns(dataName);
            try {
                Expression expr = CCJSqlParserUtil.parseCondExpression(resource);
                ExpressionDeParser expressionDeParser = null;
                if("entityMetadata".equals(dataName)) {
                    primay = getPrimayParse(resource,searchColumns);
                }
            } catch (JSQLParserException e) {
                e.printStackTrace();
            }
        }
        List<String> result = primay.stream().distinct().collect(Collectors.toList());
        return result;
    }

    private List<String> getPrimayParse(String resource, List<Map<String, Object>> searchColumns) throws JSQLParserException {
        List<String> result = new ArrayList<>();
        Expression expr = CCJSqlParserUtil.parseCondExpression(resource);
        ExpressionDeParser expressionDeParser =  new ExpressionDeParser() {
            @Override
            public void visit(Column column) {
                removeDoubleQuotes(column);
                String columnName = "";
                if(ObjectUtil.isNotEmpty(column.getTable())) {
                    columnName = column.getTable() + "." + column.getColumnName();
                }
                for (Map<String, Object> map : searchColumns) {
                    if (column.getColumnName().equals((String)map.get("label")) || columnName.equals((String)map.get("label"))){
                        Map<String, Object> doc =(Map<String, Object>) map.get("doc");
                        Map<String, Object> constraints = (Map<String, Object>)doc.get("constraints");
                        String primay = (String)constraints.get("primay");
                        if(StrUtil.isNotEmpty(primay)){
                            List<String> split = Arrays.asList(primay.split(","));
                            result.addAll(split);
                        }
                    }
                }
            }
        };
        expr.accept(expressionDeParser);
        return result;
    }

    private void removeDoubleQuotes(Column column) {
        if(column.getColumnName().contains("\"")) {
            String newColumn = column.getColumnName().replace("\"", "").trim().replace(" ",".");
            column.setColumnName(newColumn);
        }
    }

    @Override
    public String formatHasFilter(String resource) {
        StringBuffer result = new StringBuffer();
        if(ObjectUtil.isNotEmpty(resource)) {
            try {
                Expression expr = CCJSqlParserUtil.parseCondExpression(resource);
                ExpressionDeParser expressionDeParser = formatHasEdp(result);
                expr.accept(expressionDeParser);
            } catch (JSQLParserException e) {
                e.printStackTrace();
            }
        }
        removeSuperfluousAnd(result);
        return result.toString().trim();
    }

    private ExpressionDeParser formatHasEdp(StringBuffer whereBuffer) {
        ExpressionDeParser result =  new ExpressionDeParser() {
            @Override
            public void visit(Function function) {
                if(function.getName().equals("has")) {
                    ExpressionList parameters = function.getParameters();
                    List<Expression> expressions = parameters.getExpressions();
                    whereBuffer.append(StrUtil.concat(false," AND (has(",expressions.get(0).toString(),", concat('ip.',",expressions.get(1).toString(),
                            ")) OR has(",expressions.get(0).toString(),", concat('domain.',",expressions.get(1).toString(),
                            ")) OR has(",expressions.get(0).toString(),", concat('app.',",expressions.get(1).toString(),
                            ")) OR has(",expressions.get(0).toString(),", concat('subscriber.',",expressions.get(1).toString(),")))"));
                }
            }
            @Override
            public void visit(LikeExpression likeExpression) {
                whereBuffer.append(StrUtil.concat(false," AND ",likeExpression.toString()));
            }
            @Override
            public void visit(EqualsTo equalsTo) {
                whereBuffer.append(StrUtil.concat(false," AND ",equalsTo.toString()));
            }
            @Override
            public void visit(InExpression inExpression) {
                whereBuffer.append(StrUtil.concat(false," AND ",inExpression.toString()));
            }
        };
        return result;
    }

    private void removeSuperfluousAnd(StringBuffer whereBuffer) {
        int firstAndIndex = whereBuffer.indexOf("AND");
        if(firstAndIndex != -1) {
            whereBuffer.delete(firstAndIndex,firstAndIndex + 3);
        }
    }

    @Override
    public List<String> getTagsForFilter(String resource) {
        List<String> result = new ArrayList<>();
        if(ObjectUtil.isNotEmpty(resource)) {
            try {
                Expression expr = CCJSqlParserUtil.parseCondExpression(resource);
                ExpressionDeParser expressionDeParser = getTagsForFilterEdp(result);
                expr.accept(expressionDeParser);
            } catch (JSQLParserException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private ExpressionDeParser getTagsForFilterEdp(List<String> list) {
        ExpressionDeParser result =  new ExpressionDeParser() {
            @Override
            public void visit(Function function) {
                if (function.getName().equals("has")) {
                    ExpressionList parameters = function.getParameters();
                    List<Expression> expressions = parameters.getExpressions();
                    list.add(expressions.get(1).toString());
                }
            }
        };
        return result;
    }

    @Override
    public String formatSingleHasFilter(String resource,String entity) {
        StringBuffer result = new StringBuffer();
        if(ObjectUtil.isNotEmpty(resource) && ObjectUtil.isNotEmpty(entity)) {
            try {
                Expression expr = CCJSqlParserUtil.parseCondExpression(resource);
                ExpressionDeParser expressionDeParser = formatSingleHasEdp(result,entity);
                expr.accept(expressionDeParser);
            } catch (JSQLParserException e) {
                e.printStackTrace();
            }
        }
        removeSuperfluousAnd(result);
        return result.toString().trim();
    }

    private ExpressionDeParser formatSingleHasEdp(StringBuffer whereBuffer, String entity) {
        ExpressionDeParser result =  new ExpressionDeParser() {
            @Override
            public void visit(Function function) {
                if(function.getName().equals("has")) {
                    ExpressionList parameters = function.getParameters();
                    List<Expression> expressions = parameters.getExpressions();
                    if("ip".equals(entity)) {
                        whereBuffer.append(StrUtil.concat(false, " AND has(", expressions.get(0).toString(), ", concat('ip.',", expressions.get(1).toString(),
                                "))"));
                    }else if("app".equals(entity)) {
                        whereBuffer.append(StrUtil.concat(false, " AND has(", expressions.get(0).toString(), ", concat('app.',", expressions.get(1).toString(),
                                "))"));
                    }else if("domain".equals(entity)) {
                        whereBuffer.append(StrUtil.concat(false, " AND has(", expressions.get(0).toString(), ", concat('domain.',", expressions.get(1).toString(),
                                "))"));
                    }
                }
            }
            @Override
            public void visit(LikeExpression likeExpression) {
                whereBuffer.append(StrUtil.concat(false," AND ",likeExpression.toString()));
            }
            @Override
            public void visit(EqualsTo equalsTo) {
                whereBuffer.append(StrUtil.concat(false," AND ",equalsTo.toString()));
            }
            @Override
            public void visit(InExpression inExpression) {
                whereBuffer.append(StrUtil.concat(false," AND ",inExpression.toString()));
            }
        };
        return result;
    }
}
