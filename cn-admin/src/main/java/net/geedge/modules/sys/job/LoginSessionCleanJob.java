package net.geedge.modules.sys.job;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import net.geedge.common.utils.Constant;
import net.geedge.modules.election.dao.LeaderElectionDao;
import net.geedge.modules.sys.entity.SysApiKey;
import net.geedge.modules.sys.entity.SysLoginSession;
import net.geedge.modules.sys.service.SysApiKeyService;
import net.geedge.modules.sys.service.SysConfigService;
import net.geedge.modules.sys.service.SysLoginSessionService;

@DisallowConcurrentExecution
public class LoginSessionCleanJob extends QuartzJobBean {

    private Log log = Log.get();

    @Autowired
    private SysLoginSessionService sessionService;

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private SysApiKeyService sysApiKeyService;


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        Thread.currentThread().setName("loginSessionCleanJob");
        // 检查 HA_LOCK
        LeaderElectionDao dao = LeaderElectionDao.getInstance();
        boolean isLeader = dao.isLeader(Constant.SYS_HA_LOCK, Constant.SERVER_ID);
        log.info("[LoginSessionCleanJob] [Get HA_LOCK] [isLeader: {}]", isLeader);
        if (BooleanUtil.negate(isLeader)) return;

        //获取 session 过期时间
        String sessionTimeOut = StrUtil.emptyToDefault(sysConfigService.getValue(Constant.SYSCONFIG_SESSION_TIMEOUT_KEY), "30");

        //获取所有的 apikey
        List<SysApiKey> apiKeyList = sysApiKeyService.list();
        //获取所有 apikey token
        List<String> allApiKeyTokenList = apiKeyList.stream().map(apiKey -> apiKey.getToken()).collect(Collectors.toList());

        //获取所有的已过期的 api key
        Date now = new Date();
        String time = DateUtil.format(now, "yyyy-MM-dd hh:mm:ss");
        List<SysApiKey> expiredApiKeyList = sysApiKeyService.list(new LambdaQueryWrapper<SysApiKey>()
                .lt(SysApiKey::getEtime, time)
                .and(wapper -> wapper.isNotNull(SysApiKey::getEtime)));
        List<String> expiredApiKeyTokenList = expiredApiKeyList.stream().map(apiKey -> apiKey.getToken()).collect(Collectors.toList());
        if(ObjectUtil.isNotEmpty(expiredApiKeyTokenList)) {
        	sessionService.removeByIds(expiredApiKeyTokenList);
        }
        //获取所有 session
        List<SysLoginSession> sessionList = sessionService.list();
        //过滤 apikey token
        sessionList = sessionList.stream().filter(session -> !allApiKeyTokenList.contains(session.getSessionId())).collect(Collectors.toList());
        for (SysLoginSession session : sessionList) {
            //删除过期的 session
            if (System.currentTimeMillis() > (session.getLasetActiveTs() + Long.valueOf(sessionTimeOut) * DateUnit.MINUTE.getMillis())) {
                sessionService.removeById(session.getSessionId());
            }
        }
    }
}
