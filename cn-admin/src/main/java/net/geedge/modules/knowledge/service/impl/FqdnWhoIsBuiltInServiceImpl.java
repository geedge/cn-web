package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.modules.knowledge.dao.FqdnWhoIsBuiltInDao;
import net.geedge.modules.knowledge.entity.FqdnWhoIsBuiltIn;
import net.geedge.modules.knowledge.service.FqdnWhoIsBuiltInService;
import org.springframework.stereotype.Service;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/16:59
 */
@Service("FqdnWhoIsBuiltInService")
public class FqdnWhoIsBuiltInServiceImpl extends ServiceImpl<FqdnWhoIsBuiltInDao, FqdnWhoIsBuiltIn> implements FqdnWhoIsBuiltInService {
}
