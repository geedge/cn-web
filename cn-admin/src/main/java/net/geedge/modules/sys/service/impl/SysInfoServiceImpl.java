package net.geedge.modules.sys.service.impl;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.log.Log;
import net.geedge.common.utils.Tool;
import net.geedge.modules.sys.dao.SysConfigDao;
import net.geedge.modules.sys.entity.SysConfigEntity;
import net.geedge.modules.sys.service.SysInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service("sysInfoService")
public class SysInfoServiceImpl implements SysInfoService {

    private static Log log = Log.get();

    @Autowired
    private SysConfigDao sysConfigDao;


    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map getAboutInfo() {
        Map resultMap = new HashMap(4);
        // 1. nehza 系统版本
        SysConfigEntity sysVersion = new SysConfigEntity();
        Map sysVersionMap = new HashMap(4);
        sysVersionMap.put("name", "cn Monitoring System");
        sysVersionMap.put("version", sysVersion.getCvalue());

        resultMap.put("cn", sysVersionMap);

        // 2. 组件版本信息  mariaDB redis prometheus
        List<Map> componentVersionInfoList = new ArrayList<>();

        // ① mariaDB version
        Map componentVersion = new HashMap(4);
        String dataBasesVersion = sysConfigDao.getDataBasesVersion();
        componentVersion.put("name", "Mariadb");
        componentVersion.put("version", dataBasesVersion);
        componentVersionInfoList.add(componentVersion);

        // ② redis version
        Object redisVersion = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisServerCommands redisServerCommands = connection.serverCommands();
                Properties info = redisServerCommands.info();
                return info.get("redis_version").toString();
            }
        });
        componentVersion = new HashMap(4);
        componentVersion.put("name", "Redis");
        componentVersion.put("version", redisVersion.toString());
        componentVersionInfoList.add(componentVersion);


        resultMap.put("components", componentVersionInfoList);
        return resultMap;
    }

    @Override
    public Map getGitVersionInfo() {
        try {
//            String gitLog = Tool.FileUtil.readUtf8String("git.log");
            InputStream is = this.getClass().getResourceAsStream("/git.log");
            String gitLog = Tool.IoUtil.read(is, "utf-8");
            String[] split = gitLog.split("\n");
            Map map = new HashMap();
            map.put("list", Arrays.asList(split));
            return map;
        } catch (IORuntimeException e) {
            log.error("读取 git.log 文件信息失败，错误信息是：" + e);
        }
        return Collections.emptyMap();
    }
}
