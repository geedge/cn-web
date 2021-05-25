package net.geedge.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.log.Log;
import net.geedge.common.utils.Constant;
import net.geedge.common.utils.Tool;
import net.geedge.modules.sys.dao.SysConfigDao;
import net.geedge.modules.sys.entity.SysConfigEntity;
import net.geedge.modules.sys.service.SysInfoService;

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
        SysConfigEntity sysVersion = sysConfigDao.selectOne(new QueryWrapper<SysConfigEntity>().lambda().eq(SysConfigEntity::getParamKey, Constant.SYSCONFIG_KEY_SYSTEM_VERSION));
        Map sysVersionMap = new HashMap(4);
        sysVersionMap.put("name", "cn Monitoring System");
        sysVersionMap.put("version", sysVersion.getParamValue());

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
            String versionJson = Tool.FileUtil.readUtf8String("git.properties");
            JSONObject jsonObject = JSON.parseObject(versionJson.toString());
            Set<Map.Entry<String, Object>> entrySet = jsonObject.entrySet();
            if (CollectionUtils.isNotEmpty(entrySet)) {
                // 默认排序 a-z
                Map<String, Object> sortMap = new TreeMap<>(new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        return obj1.compareTo(obj2);
                    }
                });
                Map<String, Object> collect = entrySet.stream()
                        .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (o, n) -> n));
                sortMap.putAll(collect);
                return sortMap;
            }
        } catch (IORuntimeException e) {
            log.error("读取 git.properties 文件信息失败，错误信息是：" + e);
        }

        /*ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("git.properties");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));) {
            StringBuilder versionJson = null;
            String line;
            versionJson = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                versionJson.append(line);
            }

            JSONObject jsonObject = JSON.parseObject(versionJson.toString());
            Set<Map.Entry<String, Object>> entrySet = jsonObject.entrySet();
            if (CollectionUtils.isNotEmpty(entrySet)) {
                // 默认排序 a-z
                Map<String, Object> sortMap = new TreeMap<>(new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        return obj1.compareTo(obj2);
                    }
                });
                Map<String, Object> collect = entrySet.stream()
                        .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (o, n) -> n));
                sortMap.putAll(collect);
                return sortMap;
            }
        } catch (IOException e) {
            log.error("读取 git.properties 文件信息失败，错误信息是：" + e);
        }*/
        return Collections.emptyMap();
    }
}
