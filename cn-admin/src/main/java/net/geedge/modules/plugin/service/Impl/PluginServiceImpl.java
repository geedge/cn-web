package net.geedge.modules.plugin.service.Impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.Constant;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.common.utils.Tool;
import net.geedge.modules.plugin.service.PluginService;
import net.geedge.modules.sys.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Auther: lijinyang
 * @Date: 2024/01/02/14:32
 */
@Service("PluginService")
public class PluginServiceImpl implements PluginService {

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void intelligenceLearningStatus(Integer id, String type) {
        String path = (String) getScheduleParam("url")+Constant.XXL_JOB_ADMIN_JOBINFO+type;
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(Constant.XXL_JOB_ADMIN_COOKIE, getCookie(getScheduleConfig()));
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("id",id);
        ResponseEntity<HashMap> response = restTemplate.postForEntity(path,new HttpEntity<MultiValueMap>(param, requestHeaders),HashMap.class);
        requestError(response);
    }

    private Map<String, Object> getScheduleConfig() {
        Map<String, Object> scheduleTask = JSONObject.parseObject(getScheduleTask());
        return  (Map<String, Object>)scheduleTask.get("config");
    }

    private Object getScheduleParam(String paramName) {
        return getScheduleConfig().get(paramName);
    }

    private String getScheduleTask() {
        return sysConfigService.getValue(Constant.SYSCONFIG_SCHEDULE_TASK);
    }

    private String getCookie(Map<String, Object> config) {
        String url = (String)config.get("url");
        String userName = (String)config.get("userName");
        String pin = (String)config.get("pin");
        pin = Tool.AesUtil.decrypt(pin, Constant.AES_SECRET_KEY);
        String loginPath = url+Constant.XXL_JOB_ADMIN_LOGIN;
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("userName",userName);
        param.add("pin",pin);
        ResponseEntity<HashMap> response = restTemplate.postForEntity(loginPath,new HttpEntity<MultiValueMap>(param),HashMap.class);
        Map<String, Object> body = response.getBody();
        if((Integer) body.get("code") != 200){
            throw new CNException(RCode.ERROR);
        }
        HttpHeaders headers = response.getHeaders();
        return headers.get(HttpHeaders.SET_COOKIE).get(0);
    }

    private void requestError(ResponseEntity<HashMap> response) {
        Map<String, Object> body = response.getBody();
        int code = response.getStatusCodeValue();
        if(code != 200){
            if(ObjectUtil.isNotEmpty(body.get("msg"))) {
                throw new CNException((String) body.get("msg"), code);
            }
            throw new CNException(RCode.ERROR);
        }
    }

    @Override
    public R intelligenceLearningList(Map<String,Object> params) {
        preHandle(params);
        String path = listPath(params);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(Constant.XXL_JOB_ADMIN_COOKIE, getCookie(getScheduleConfig()));
        ResponseEntity<HashMap> response = restTemplate.postForEntity(path,new HttpEntity<MultiValueMap>(requestHeaders),HashMap.class);
        Map<String, Object> body = response.getBody();
        requestError(response);
        R r = creatResult(params, formatResult(body));
        return r;
    }

    private R creatResult(Map<String,Object> params, List<Map<String,Object>> list) {
        R result = new R();
        if(ObjectUtil.isNotEmpty(params.get("pageSize"))) {
            result.put("pageSize",Integer.parseInt((String)params.get("pageSize")));
        }
        if(ObjectUtil.isNotEmpty(params.get("pageNo"))) {
            result.put("pageNo",Integer.parseInt((String)params.get("pageNo")));
        }
        if(ObjectUtil.isNotEmpty(list)) {
            result.put("total", list.size());
            result.putData("list",list);
        } else {
            result.put("total", 0);
            result.putData("list",new ArrayList<Map<String,Object>>());
        }
        return result;
    }

    private void preHandle(Map<String,Object> params) {
        Integer jobGroup = Integer.parseInt((String)params.get("jobGroup"));
        Integer triggerStatus = ObjectUtil.isNotEmpty(params.get("triggerStatus"))?Integer.parseInt((String)params.get("triggerStatus")):(Integer) getScheduleParam(Constant.DEFAULT_TRIGGER_STATUS);
        Integer length = ObjectUtil.isNotEmpty(params.get("pageSize"))?Integer.parseInt((String)params.get("pageSize")):Constant.DEFAULT_LENGTH;
        Integer start = ObjectUtil.isNotEmpty(params.get("pageNo"))?start(length, Integer.parseInt((String)params.get("pageNo"))):Constant.DEFAULT_START;
        params.put("jobGroup",jobGroup);
        params.put("triggerStatus",triggerStatus);
        params.put("length",length);
        params.put("start",start);
    }

    private String listPath(Map<String,Object> params) {
        String path = (String) getScheduleParam("url")+Constant.XXL_JOB_ADMIN_JOBINFO_PAGELIST;
        path = MessageFormat.format(Constant.XXL_JOB_ADMIN_JOBINFO_PAGELIST_PARAMS,path,params.get("jobGroup"),params.get("triggerStatus"),params.get("start"),params.get("length"));
        return path;
    }

    private Integer start(Integer length,Integer pageNo) {
        return (pageNo - 1) * length;
    }

    private List<Map<String,Object>> formatResult(Map<String, Object> body) {
        if(ObjectUtil.isNotEmpty(body) && ObjectUtil.isNotEmpty(body.get("data"))) {
            List<Map<String,Object>> result = new ArrayList<>();
            List<Map<String,Object>> list = (List<Map<String,Object>>)body.get("data");
            for (Map<String,Object> data : list) {
                Map<String, Object> map = new HashMap<>();
                if(ObjectUtil.isNotEmpty(data.get(Constant.LEARNING_JOB_ID))) {
                    map.put(Constant.LEARNING_JOB_ID,data.get(Constant.LEARNING_JOB_ID));
                }
                if(ObjectUtil.isNotEmpty(data.get(Constant.LEARNING_JOB_DESC))) {
                    map.put(Constant.LEARNING_JOB_DESC,data.get(Constant.LEARNING_JOB_DESC));
                }
                if(ObjectUtil.isNotEmpty(data.get(Constant.LEARNING_JOB_TRIGGER_STATUS))) {
                    map.put(Constant.LEARNING_JOB_TRIGGER_STATUS,data.get(Constant.LEARNING_JOB_TRIGGER_STATUS));
                }
                result.add(map);
            }
            return result;
        }
        return null;
    }
}
