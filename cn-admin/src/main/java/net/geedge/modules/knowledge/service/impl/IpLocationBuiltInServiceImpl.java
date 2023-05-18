package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.modules.knowledge.dao.IpLocationBuiltInDao;
import net.geedge.modules.knowledge.entity.IpLocationBuiltIn;
import net.geedge.modules.knowledge.service.IpLocationBuiltInService;
import org.springframework.stereotype.Service;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/13:53
 */
@Service("IpLocationBuiltInService")
public class IpLocationBuiltInServiceImpl extends ServiceImpl<IpLocationBuiltInDao, IpLocationBuiltIn> implements IpLocationBuiltInService {
}
