package net.geedge.modules.galaxy.service.impl;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.hutool.json.JSONArray;
import com.alibaba.fastjson.JSON;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.*;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.modules.galaxy.dao.GalaxyProxyDao;
import net.geedge.modules.galaxy.entity.GalaxyProxy;
import net.geedge.modules.galaxy.service.GalaxyProxyService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@Service("galaxyProxyService")
public class GalaxyProxyImpl extends ServiceImpl<GalaxyProxyDao, GalaxyProxy> implements GalaxyProxyService {

	private Log log = Log.get();

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GalaxyProxy> page = this.page(new Query(GalaxyProxy.class).getPage(params));
        List<GalaxyProxy> list = this.baseMapper.queryPage(page, params);
        page.setRecords(list);
        return new PageUtils(page);
    }

    @Override
    public GalaxyProxy queryInfo(Integer id) {
        return this.baseMapper.selectInfo(id);
    }

    @Override
    public Integer saveOrUpdateGalaxyProxy(GalaxyProxy galaxyProxy) {
        ValidateGalaxyProxy(galaxyProxy);
        if (ObjectUtil.isEmpty(galaxyProxy.getId())) {
            galaxyProxy.setCtime(new Date());
        }
        galaxyProxy.setUtime(new Date());
        this.saveOrUpdate(galaxyProxy);
        //清空缓存
        Tool.FreeMarkerUtil.clearTemlateCache();
        return galaxyProxy.getId();
    }

    @Override
    public void removeGalaxyProxy(String ids) {
        // 删除
        this.removeByIds(Arrays.asList(ids.split(",")));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importFile(MultipartFile file) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        Reader reader = null;
        try {
            InputStream fileInputStream = file.getInputStream();
            reader = new InputStreamReader(fileInputStream, "utf-8");
            int ch = 0;
            while ((ch = reader.read()) != -1) {
                stringBuffer.append((char) ch);
            }
        } catch (IOException e) {
            throw new CNException(RCode.PANEL_IMPORTFILE_FAILED);
        } finally {
            reader.close();
        }
        JSONArray jsonArray = Tool.JSONUtil.parseArray(stringBuffer.toString());
        List<GalaxyProxy> galaxyProxyList = Tool.JSONUtil.toList(jsonArray, GalaxyProxy.class);
        galaxyProxyList.forEach(galaxyProxy -> {
            galaxyProxy.setUtime(new Date());
            galaxyProxy.setCtime(new Date());
            galaxyProxy.setState(1);
        });
        //清空数据表
        this.baseMapper.deleteTable();
        this.baseMapper.insertGalaxyProxyList(galaxyProxyList);
        Tool.FreeMarkerUtil.clearTemlateCache();
    }

    @Override
    public void exportFile(HttpServletResponse response) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = "setting-" + simpleDateFormat.format(new Date()).replaceAll("-", "") + ".json";
        List<GalaxyProxy> list = this.list();
        if (ObjectUtil.isEmpty(list)) {
            throw new CNException("galaxy配置信息不存在");
        }
        //日期转换与json格式化
        String fileContent = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
        fileContent = JSONUtil.formatJsonStr(fileContent);
        response.setCharacterEncoding("utf-8");
        //设置响应的内容类型
        response.setContentType("application/octet-stream");
        //设置文件的名称和格式
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        ServletOutputStream outStr = null;
        try {
            outStr = response.getOutputStream();
            outStr.write(fileContent.getBytes("UTF-8"));
            outStr.flush();
        } catch (IOException e) {
            throw new CNException("文件导出失败");
        } finally {
            outStr.close();
        }
    }

    public void ValidateGalaxyProxy(GalaxyProxy galaxyProxy) {
        ValidateUtils.is(galaxyProxy.getName()).notNull(RCode.GALAXY_PROXY_NAME_ISNULL)
                .and(galaxyProxy.getMethod()).notNull(RCode.GALAXY_PROXY_METHOD_ISNULL)
                .and(galaxyProxy.getTargetUrl()).notNull(RCode.GALAXY_PROXY_TARGET_URL_ISNULL);

        //设置默认值
        if (Tool.StrUtil.isBlank(galaxyProxy.getTargetParam())) {
            galaxyProxy.setTargetParam(Tool.StrUtil.EMPTY);
        }
        if (Tool.StrUtil.isBlank(galaxyProxy.getTargetHeader())) {
            galaxyProxy.setTargetHeader(Tool.StrUtil.EMPTY_JSON);
        }
        if (Tool.StrUtil.isBlank(galaxyProxy.getPreHandle())) {
            galaxyProxy.setPreHandle(Tool.StrUtil.EMPTY);
        }
        if (Tool.StrUtil.isBlank(galaxyProxy.getPostHandle())) {
            galaxyProxy.setPostHandle(Tool.StrUtil.EMPTY);
        }
    }
}
