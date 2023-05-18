package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.modules.knowledge.dao.AppTagUserDefinedDao;
import net.geedge.modules.knowledge.entity.AppTagUserDefined;
import net.geedge.modules.knowledge.service.AppTagUserDefinedService;
import org.springframework.stereotype.Service;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/13:50
 */
@Service("AppTagUserDefinedService")
public class AppTagUserDefinedServiceImpl extends ServiceImpl<AppTagUserDefinedDao, AppTagUserDefined> implements AppTagUserDefinedService {
}
