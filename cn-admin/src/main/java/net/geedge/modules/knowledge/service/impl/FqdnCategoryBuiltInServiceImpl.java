package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.modules.knowledge.dao.FqdnCategoryBuiltInDao;
import net.geedge.modules.knowledge.entity.FqdnCategoryBuiltIn;
import net.geedge.modules.knowledge.service.FqdnCategoryBuiltInService;
import org.springframework.stereotype.Service;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/17:18
 */
@Service("FqdnCategoryBuiltInService")
public class FqdnCategoryBuiltInServiceImpl extends ServiceImpl<FqdnCategoryBuiltInDao, FqdnCategoryBuiltIn> implements FqdnCategoryBuiltInService {
}
