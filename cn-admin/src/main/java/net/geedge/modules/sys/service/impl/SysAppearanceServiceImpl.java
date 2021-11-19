package net.geedge.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.modules.sys.dao.SysAppearanceDao;
import net.geedge.modules.sys.entity.SysConfigEntity;
import net.geedge.modules.sys.service.SysAppearanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 系统外观设置
 *
 * @author lijinyang
 * @date 2021/11/17
 * */
@Service("sysAppearanceService")
public class SysAppearanceServiceImpl extends ServiceImpl<SysAppearanceDao, SysConfigEntity> implements SysAppearanceService {

    @Autowired
    private SysAppearanceDao sysAppearanceDao;

    /**
     * 系统外观查询
     */
    @Override
    public String queryAppearance() {
        String appearance = sysAppearanceDao.queryAppearance();
        return appearance;
    }

    /**
     * 系统外观更改
     */
    @Override
    public boolean updateAppearance(String appearance) {
        boolean isUpdate = sysAppearanceDao.updateAppearance(appearance);
        return isUpdate;
    }
}
