package net.geedge.modules.entity.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import com.alibaba.fastjson.JSONObject;
import net.geedge.modules.entity.service.EntityExplorerService;
import net.geedge.modules.sys.service.SysConfigService;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.util.deparser.ExpressionDeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private SysConfigService sysConfigService;

    private Log log = Log.get();

    @Override
    public String parseExpression(String resource){
        String expression = "";
        if(ObjectUtil.isNotEmpty(resource)) {
            String entityExploreConfigJson = sysConfigService.getValue("schema_entity_explore");
            HashMap entityExploreConfigs = JSONObject.parseObject(entityExploreConfigJson, HashMap.class);
            Map<String, Object> enityMetadata = (Map<String, Object>)entityExploreConfigs.get("enityMetadata");
            List<Map<String, Object>> searchColumns = (List<Map<String, Object>>) enityMetadata.get("searchColumns");
            try {
                Expression expr = CCJSqlParserUtil.parseCondExpression(resource);
                ExpressionDeParser expressionDeParser =  new ExpressionDeParser() {
                    @Override
                    public void visit(Column column) {
                        for (Map<String, Object> map : searchColumns) {
                            if (map.get("label").equals(column.getColumnName())) {
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
}
