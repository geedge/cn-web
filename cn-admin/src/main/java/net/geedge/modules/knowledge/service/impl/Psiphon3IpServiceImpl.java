package net.geedge.modules.knowledge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.modules.knowledge.dao.Psiphon3IpDao;
import net.geedge.modules.knowledge.entity.Psiphon3Ip;
import net.geedge.modules.knowledge.service.Psiphon3IpService;
import org.springframework.stereotype.Service;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/22/14:18
 */
@Service("Psiphon3Ip")
public class Psiphon3IpServiceImpl extends ServiceImpl<Psiphon3IpDao, Psiphon3Ip> implements Psiphon3IpService {
}
