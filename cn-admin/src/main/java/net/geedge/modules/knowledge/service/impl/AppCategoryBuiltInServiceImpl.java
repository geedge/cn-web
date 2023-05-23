package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.modules.knowledge.dao.AppCategoryBuiltInDao;
import net.geedge.modules.knowledge.entity.AppCategoryBuiltIn;
import net.geedge.modules.knowledge.service.AppCategoryBuiltInService;
import org.springframework.stereotype.Service;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/15:44
 */
@Service("AppCategoryBuiltInService")
public class AppCategoryBuiltInServiceImpl extends ServiceImpl<AppCategoryBuiltInDao, AppCategoryBuiltIn> implements AppCategoryBuiltInService {
}
