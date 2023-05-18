package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.modules.knowledge.dao.DomainTagUserDefinedDao;
import net.geedge.modules.knowledge.entity.DomainTagUserDefined;
import net.geedge.modules.knowledge.service.DomainTagUserDefinedService;
import org.springframework.stereotype.Service;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/13:52
 */
@Service("DomainTagUserDefinedService")
public class DomainTagUserDefinedServiceImpl extends ServiceImpl<DomainTagUserDefinedDao, DomainTagUserDefined> implements DomainTagUserDefinedService {
}
