package net.geedge.modules.sys.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.geedge.modules.sys.dao.SysLoginSessionDao;
import net.geedge.modules.sys.entity.SysLoginSession;
import net.geedge.modules.sys.service.SysLoginSessionService;

@Service
public class SysLoginSessionServiceImpl extends ServiceImpl<SysLoginSessionDao, SysLoginSession> implements SysLoginSessionService {

}
