package net.geedge.modules.sys.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.common.utils.Tool;
import net.geedge.modules.sys.dao.SysSecurityDao;
import net.geedge.modules.sys.entity.SysConfigEntity;
import net.geedge.modules.sys.service.SysSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 系统安全设定
 *
 * @author lijinyang
 * @date 2021/11/22
 * */
@Service("sysSecurityService")
public class SysSecurityServiceImpl extends ServiceImpl<SysSecurityDao, SysConfigEntity> implements SysSecurityService {
    @Autowired
    private SysSecurityDao sysSecurityDao;
    /**
     * 系统安全设定查询
     *
     */
    @Override
    public JSONObject querySecurity() {
        String paramKey = "security";
        String security = sysSecurityDao.querySecurity(paramKey);
        JSONObject jsonObject = Tool.JSONUtil.parseObj(security);
        return jsonObject;
    }

    /**
     * 系统安全设定修改
     *
     */
    @Override
    public boolean updateSecurity(JSONObject security) {
        String paramKey = "security";
        String jsonStr = Tool.JSONUtil.toJsonStr(security);
        boolean isUpdate = sysSecurityDao.updateSecurity(jsonStr, paramKey);
        return isUpdate;
    }
}
