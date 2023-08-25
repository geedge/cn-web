package net.geedge.common.magic;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.ssssssss.magicapi.core.context.RequestEntity;
import org.ssssssss.magicapi.core.interceptor.ResultProvider;

import com.alibaba.fastjson.JSON;

import cn.hutool.core.util.StrUtil;

/**
 * @Auther: lijinyang
 * @Date: 2022/12/26/11:44
 */
@Component
public class MagicResultProvider implements ResultProvider {

    @Override
    public Object buildResult(RequestEntity requestEntity, int code, String message, Object data) {
        if(code != 200){
        	/*
        	 * 针对统一异常提示进行处理
        	 */
        	if(StrUtil.contains(message, "为必填项")) {
        		message = this.nullHandler(message);
        	}
            HashMap<String, Object> result = new HashMap<String,Object>();
            result.put("code",code);
            result.put("message",message);
            result.put("time",new Date());	
            return result;
        }else {
        	int status = (int) ((Map)data).get("status");
        	code = status;
        }
        return data;
    }
    
    private String nullHandler(String msg) {
    	String param = "";
    	param = (StrUtil.split(msg, "[")).get(1).split("]")[0];
    	return String.format("param %s can not be empty",param);
    }
}
