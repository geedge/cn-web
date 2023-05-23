package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.modules.knowledge.dao.IpAsnBuiltInDao;
import net.geedge.modules.knowledge.entity.IpAsnBuiltIn;
import net.geedge.modules.knowledge.service.IpAsnBuiltInService;
import org.springframework.stereotype.Service;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/17:31
 */
@Service("IpAsnBuiltInService")
public class IpAsnBuiltInServiceImpl extends ServiceImpl<IpAsnBuiltInDao, IpAsnBuiltIn> implements IpAsnBuiltInService {
}
