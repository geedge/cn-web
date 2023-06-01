package net.geedge.modules.knowledge.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.geedge.modules.knowledge.dao.IdcRenterBuiltInDao;
import net.geedge.modules.knowledge.entity.IdcRenterBuiltIn;
import net.geedge.modules.knowledge.service.IdcRenterBuiltInService;

@Service("idcRenterBuiltInService")
public class IdcRenterBuiltInServiceImpl extends ServiceImpl<IdcRenterBuiltInDao,IdcRenterBuiltIn> implements IdcRenterBuiltInService{

}
