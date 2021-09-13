package net.geedge.modules.galaxy.service;

import java.io.IOException;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import net.geedge.common.utils.PageUtils;
import net.geedge.modules.galaxy.entity.GalaxyProxy;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface GalaxyProxyService extends IService<GalaxyProxy> {

    PageUtils queryPage(Map<String, Object> params);

    GalaxyProxy queryInfo(Integer id);

    Integer saveOrUpdateGalaxyProxy(GalaxyProxy galaxyProxy);

    void removeGalaxyProxy(String ids);

    void importFile(MultipartFile file) throws IOException;

    void exportFile(HttpServletResponse response) throws Exception;
}
