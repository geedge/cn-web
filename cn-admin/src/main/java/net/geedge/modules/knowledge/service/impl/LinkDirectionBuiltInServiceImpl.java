package net.geedge.modules.knowledge.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.geedge.modules.knowledge.dao.LinkDirectionBuiltInDao;
import net.geedge.modules.knowledge.entity.LinkDirectionBuiltIn;
import net.geedge.modules.knowledge.service.LinkDirectionBuiltInService;

@Service("linkDirectionBuiltInService")
public class LinkDirectionBuiltInServiceImpl extends ServiceImpl<LinkDirectionBuiltInDao,LinkDirectionBuiltIn> implements LinkDirectionBuiltInService{

}
