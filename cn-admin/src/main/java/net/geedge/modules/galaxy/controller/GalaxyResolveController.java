package net.geedge.modules.galaxy.controller;

import net.geedge.common.utils.R;
import net.geedge.modules.sys.controller.AbstractController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * galaxy 递归解析
 * @Auther: lijinyang
 * @Date: 2022/03/10/15:57
 */
@RestController
@RequestMapping("/galaxy")
public class GalaxyResolveController extends AbstractController {

    @RequestMapping("/domainRecursiveResolve")
    public R domainRecursiveResolve(String domain){
        String osName = System.getProperties().getProperty("os.name");
        if(osName.equals("Linux")) {
            Process process = null;
            try {
                process = Runtime.getRuntime().exec("dig +trace "+domain);
                String result = printResults(process);
                Map<String,Object> data = new HashMap();
                data.put("resultType", "single");
                data.put("result",result);
                return R.ok().put("data",data);
            } catch (IOException e) {
                logger.error(e.getMessage());
            } finally {
                if(process!=null) {
                    process.destroy();
                }
            }
        }
        String error = "The current system is not linux and cannot be executed";
        logger.debug(error);
        return R.error().put("msg",error);
    }
    /**
     *   消费inputstream，并返回
     */
    public static String printResults(Process process) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
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
