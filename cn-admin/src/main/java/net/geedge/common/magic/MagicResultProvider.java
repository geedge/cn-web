package net.geedge.common.magic;

import org.springframework.stereotype.Component;
import org.ssssssss.magicapi.core.context.RequestEntity;
import org.ssssssss.magicapi.core.interceptor.ResultProvider;

import java.util.Date;
import java.util.HashMap;

/**
 * @Auther: lijinyang
 * @Date: 2022/12/26/11:44
 */
@Component
public class MagicResultProvider implements ResultProvider {

    @Override
    public Object buildResult(RequestEntity requestEntity, int code, String message, Object data) {
        if(code != 200){
            return new HashMap<String,Object>(){
                {
                    put("code",code);
                    put("message",message);
                    put("time",new Date());
                }
            };
        }
        return data;
    }
}
