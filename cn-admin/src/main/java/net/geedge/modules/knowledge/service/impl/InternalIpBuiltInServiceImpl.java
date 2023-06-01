package net.geedge.modules.knowledge.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.geedge.modules.knowledge.dao.InternalIpBuiltInDao;
import net.geedge.modules.knowledge.entity.InternalIpBuiltIn;
import net.geedge.modules.knowledge.service.InternalIpBuiltInService;

@Service("internalIpBuiltInService")
public class InternalIpBuiltInServiceImpl extends ServiceImpl<InternalIpBuiltInDao,InternalIpBuiltIn> implements InternalIpBuiltInService{

}
