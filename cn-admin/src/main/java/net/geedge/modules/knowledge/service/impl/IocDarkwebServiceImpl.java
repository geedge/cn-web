package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.modules.knowledge.dao.IocDarkwebDao;
import net.geedge.modules.knowledge.entity.IocDarkweb;
import net.geedge.modules.knowledge.service.IocDarkwebService;
import org.springframework.stereotype.Service;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/15:02
 */
@Service("IocDarkwebService")
public class IocDarkwebServiceImpl extends ServiceImpl<IocDarkwebDao, IocDarkweb> implements IocDarkwebService {
}
