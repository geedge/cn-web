package net.geedge.modules.sys.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.hutool.log.Log;

@DisallowConcurrentExecution
public class SessionTimeOutJob extends QuartzJobBean {
	
	private Log log = Log.get();
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		/*// 获取redis中所有的token信息
		Map<String, SysUserEntity> shiroSessionInfoMap = ShiroUtils.getShiroSessionInfoMap();
		
		// 获取所有登录用户的信息
		List<SysUserEntity> users = new ArrayList<SysUserEntity>(shiroSessionInfoMap.values());
		List<Long> onlineUserIds = users.stream().map(SysUserEntity::getId).collect(Collectors.toList());
		
		// 获取terminal session表中所有正在连接的用户信息
		List<TerminalSessionEntity> tableSessions = terminalSessionService.list(new QueryWrapper<TerminalSessionEntity>().lambda().eq(TerminalSessionEntity::getStatus, 0));
		List<Long> allUserTerminals = tableSessions.stream().map(TerminalSessionEntity::getUserId).collect(Collectors.toList());
		Map<Long, List<TerminalSessionEntity>> userIdAndTerminal = tableSessions.stream().collect(Collectors.groupingBy(TerminalSessionEntity::getUserId));
		
		List<Long> removeUserIds = allUserTerminals.stream().filter(item -> !onlineUserIds.contains(item)).collect(Collectors.toList());
		
		List<TerminalSessionEntity> updateDatas = new ArrayList<TerminalSessionEntity>();
		// 将登录超时的用户terminal连接关闭
		for(Long userId : removeUserIds) {
			List<TerminalSessionEntity> userTerminals = userIdAndTerminal.get(userId);
			for(TerminalSessionEntity userTerminal : userTerminals) {
				userTerminal.setEndTime(new Date());
				userTerminal.setStatus(Constants.SessionStatus.FORCE_QUIT.getValue());
				updateDatas.add(userTerminal);
				WebSocketSession session = TerminalSession.findSession(userTerminal.getUuid());
				try {
		            TerminalSession.remove(session);
		            // TerminalHandler.unloginUserHandle(session);
		            StringBuilder sb = new StringBuilder();
		            sb.append("\u001B[01;31m\u001B[K Administrator disconnected \u001B[m\u001B[K  \r\n");
		            sb.append("$: ");
		            session.sendMessage(new TextMessage(sb.toString()));
		        } catch (IOException e) {
		            log.error(String.format("terminal session kill failed,uuid = %s,错误信息 = %s", userTerminal.getUuid(), e.getMessage()), e);
		        }
				// kill 之后，将剩余文件写到数据库中
		        TerminalHandler.handleRecodeAfterClientClose(userTerminal.getUuid(), terminalRecordService);
			}
		}	*/	
	}

}
