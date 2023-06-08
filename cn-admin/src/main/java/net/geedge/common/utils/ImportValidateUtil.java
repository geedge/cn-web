package net.geedge.common.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Builder;
import lombok.Data;
import net.geedge.modules.sys.service.BasicImportAndExportService;
import net.geedge.modules.sys.service.SysI18nService;

@Data
@Builder
public class ImportValidateUtil {

    private static BasicImportAndExportService basicImportAndExportService;

    static {
        basicImportAndExportService = SpringContextUtils.getBean(BasicImportAndExportService.class);
    }

    // 错误提示表头信息
    private List<String> headerInfos;

    /**
     * 已存在的内容处理方式，可选值：0：错误 error  1：覆盖 override   2：忽略 ignore
     * 校验工具类只对 1：覆盖 override 进行处理
     */
    private String existed;

    // 存储导入错误信息
    private final ThreadLocal<List<Map<String, String>>> threadLocal = new ThreadLocal();

    /**
     * 此行忽略并重置错误信息，只提示忽略本行
     *
     * @param lineNum
     * @param headerIndex
     */
    public void ignoreThisRow(Integer lineNum, int headerIndex) {
        // 删除之前的错误信息
        List<Map<String, String>> errMsgList = threadLocal.get();
        errMsgList = Tool.CollUtil.isEmpty(errMsgList) ? Tool.ListUtil.list(true) : errMsgList;

        errMsgList = errMsgList.stream().filter(map -> {
            String lineNo = map.get("lineNo");
            if (!Tool.StrUtil.equals(Tool.StrUtil.toString(lineNum), lineNo)) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());

        // 先删
        threadLocal.remove();
        if (Tool.CollUtil.isNotEmpty(errMsgList)) {
            // 后增
            threadLocal.set(errMsgList);
        }

        // 提示忽略
        String header = headerInfos.get(headerIndex);
        this.recordImportAttrErr(lineNum, RCode.IMPORT_IGNORE);
    }

    /**
     * 校验 value 必填
     * @param value
     * @param lineNum
     * @param headerIndex
     */
    public void validateRequired(Object value, int lineNum, int headerIndex) {
        if (ObjectUtil.isEmpty(value)) {
            String header = headerInfos.get(headerIndex);
            this.recordImportAttrErr(lineNum, RCode.IMPORT_FIELD_REQUIRED);
        }
    }

    /**
     * 校验传入参数是否重复
     *
     * @return
     
    public boolean validateIndexDuplicate(String tableName, Map<String,String> columnVal, int lineNum, int headerIndex) {
        List list = basicImportAndExportService.queryDataId(tableName, columnVal);

        if (Tool.CollectionUtil.isNotEmpty(list) && !StrUtil.equals("1", existed)) {
            String header = headerInfos.get(headerIndex);
            this.recordImportAttrErr(lineNum, RCode.IMPORT_FIELD_DUPLICATE);
            return true;
        }
        return false;
    }*/

    /**
     * 校验出入参数是否在允许范围内
     */
    
    
    /**
     * 记录错误信息
     */
    public void recordImportAttrErr(int lineNum, RCode rCode) {
        
        String msg = rCode.getMsg();
        
        Map failDetail = Tool.MapUtil.builder()
                .put("lineNo", StrUtil.toString(lineNum))
                .put("errorMsg", msg).build();

        List<Map<String, String>> errMsgList = threadLocal.get();
        errMsgList = Tool.CollUtil.isEmpty(errMsgList) ? Tool.ListUtil.list(true) : errMsgList;
        errMsgList.add(failDetail);
        errMsgList = errMsgList.stream().distinct().collect(Collectors.toList());

        threadLocal.set(errMsgList);
    }


    /**
     * 获取导入错误信息
     *
     * @return
     */
    public List<Map<String, String>> getImportErrMsg() {
        List<Map<String, String>> errMsgList = threadLocal.get();
        errMsgList = Tool.CollUtil.isEmpty(errMsgList) ? Tool.ListUtil.list(true) : errMsgList;

        threadLocal.remove();
        return errMsgList;
    }

    /**
     * 指定行号，返回该行错误信息
     *
     * @param lineNum
     * @return
     */
    public List<Map<String, String>> getImportErrMsgForThisLine(Integer lineNum) {
        List<Map<String, String>> errMsgList = threadLocal.get();
        errMsgList = Tool.CollUtil.isEmpty(errMsgList) ? Tool.ListUtil.list(true) : errMsgList;

        List<Map<String, String>> list = errMsgList.stream().filter(map -> {
            String lineNo = map.get("lineNo");
            if (Tool.StrUtil.equals(Tool.StrUtil.toString(lineNum), lineNo)) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        return list;
    }
}
