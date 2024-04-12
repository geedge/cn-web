package net.geedge.modules.election.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.hutool.log.Log;
import net.geedge.common.utils.Constant;
import net.geedge.modules.election.dao.LeaderElectionDao;
import java.util.concurrent.TimeUnit;

@DisallowConcurrentExecution
public class HaJob extends QuartzJobBean {

    private static final Log log = Log.get();
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Thread.currentThread().setName("HaJob");
        try {
            LeaderElectionDao dao = LeaderElectionDao.getInstance();
            // ha lock
            dao.attemptLeadership(Constant.SYS_HA_LOCK, Constant.SERVER_ID, TimeUnit.SECONDS.toMillis(10));

            if (log.isDebugEnabled()) {
                log.debug("[HaJob] [current server] [isLeader: {}]", dao.isLeader(Constant.SYS_HA_LOCK, Constant.SERVER_ID));
            }
        } catch (Exception e) {
            log.error(e, "[HaJob] [error]");
        }
    }
}
