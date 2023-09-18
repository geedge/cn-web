package net.geedge.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.R;
import net.geedge.modules.sys.entity.SysRoleEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface SysRoleService extends IService<SysRoleEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveRole(SysRoleEntity role);

    void update(SysRoleEntity role);

    R deleteBatch(Integer[] ids, Map<String, Object> params);

    Map getMenuListByRoleId(Integer roleId);
}
