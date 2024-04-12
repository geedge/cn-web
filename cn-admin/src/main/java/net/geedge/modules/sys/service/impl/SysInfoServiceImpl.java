package net.geedge.modules.sys.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.log.Log;
import net.geedge.common.utils.Tool;
import net.geedge.modules.sys.dao.SysConfigDao;
import net.geedge.modules.sys.entity.SysConfigEntity;
import net.geedge.modules.sys.service.SysInfoService;

@Service("sysInfoService")
public class SysInfoServiceImpl implements SysInfoService {

    private static Log log = Log.get();

    @Autowired
    private SysConfigDao sysConfigDao;


    @Override
    public Map getAboutInfo() {
        Map resultMap = new HashMap(4);
        // 1. cn 系统版本
        SysConfigEntity sysVersion = new SysConfigEntity();
        Map sysVersionMap = new HashMap(4);
        sysVersionMap.put("name", "Cyber Narrator");
        sysVersionMap.put("version", sysVersion.getCvalue());

        resultMap.put("cn", sysVersionMap);

        // 2. 组件版本信息  mariaDB
        List<Map> componentVersionInfoList = new ArrayList<>();

        // ① mariaDB version
        Map componentVersion = new HashMap(4);
        String dataBasesVersion = sysConfigDao.getDataBasesVersion();
        componentVersion.put("name", "Mariadb");
        componentVersion.put("version", dataBasesVersion);
        componentVersionInfoList.add(componentVersion);

        componentVersion = new HashMap(4);
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
