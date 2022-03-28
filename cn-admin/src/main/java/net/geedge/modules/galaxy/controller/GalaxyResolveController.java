package net.geedge.modules.galaxy.controller;

import com.jcraft.jsch.*;
import net.geedge.common.exception.CNException;
import net.geedge.common.exception.CNExceptionHandler;
import net.geedge.common.utils.AccOauthUtils;
import net.geedge.common.utils.Constant;
import net.geedge.common.utils.R;
import net.geedge.common.utils.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * galaxy 递归解析
 * @Auther: lijinyang
 * @Date: 2022/03/10/15:57
 */
@RestController
@RequestMapping("/galaxy")
public class GalaxyResolveController {
    @Value("${ssh.host}")
    private String host;

    @Value("${ssh.port}")
    private int port;

    @Value("${ssh.user}")
    private String username;

    @Value("${ssh.pin}")
    private String password;

    @RequestMapping("/domainRecursiveResolve")
    public R domainRecursiveResolve(String domain){
        //解密
        String pin = Tool.AesUtil.decrypt(password, Constant.AES_SECRET_KEY);
        ChannelExec exec = null;
        Session session = null;
        try {
            // 创建JSch
            JSch jSch = new JSch();
            // 获取session
            session = jSch.getSession(username,host,port);
            session.setPassword(pin);
            Properties prop = new Properties();
            //主机密钥确认
            prop.put("StrictHostKeyChecking", "no");
            session.setConfig(prop);

            // 启动连接
            session.connect();
            exec = (ChannelExec)session.openChannel("exec");
            exec.setCommand("dig +trace "+domain);
            exec.setInputStream(null);
            exec.setErrStream(System.err);
            exec.connect();

            // 消费所有输入流，必须在exec之后
            String inStr = consumeInputStream(exec.getInputStream());
            Map data = new HashMap();
            data.put("resultType", "single");
            data.put("result",inStr);
            return R.ok().put("data",data);
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            if (exec != null){
                exec.disconnect();
            }
            if (session != null){
                session.disconnect();
            }
        }
        return R.error();
    }
    /**
     *   消费inputstream，并返回
     */
    public static String consumeInputStream(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String s ;
        StringBuilder sb = new StringBuilder();
        while((s=br.readLine())!=null){
            sb.append(s);
            sb.append("\r\n");
        }
        br.close();
        return sb.toString();
    }
}
