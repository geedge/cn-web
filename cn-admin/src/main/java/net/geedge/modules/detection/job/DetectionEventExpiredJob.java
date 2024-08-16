package net.geedge.modules.detection.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.clickhouse.client.ClickHouseClient;
import com.clickhouse.client.ClickHouseNode;
import com.clickhouse.data.ClickHouseFormat;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import net.geedge.common.config.Context;
import net.geedge.common.utils.Constant;
import net.geedge.modules.detection.entity.Event;

/**
 * 1.从本地缓存中获取数据
 * 2.判断内存中数据是否结束
 * 3.将数据批量插入到ck中 同时移除内存中的数据
 */
@DisallowConcurrentExecution
public class DetectionEventExpiredJob extends QuartzJobBean {
	private Log log = Log.get();

	@Autowired
	private ClickHouseClient clickHouseClient;
	
	@Autowired
	private ClickHouseNode clickHouseNode;

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		// 获取缓存中的event数据
		Map<String, Event> cache = Context.DETECTTION_EVENT_CACHE;
		Long currentTime = new Date().getTime()/1000;
		if(ObjectUtil.isNotEmpty(cache)) {
			log.info("[DetectionEventExpiredJob] [Get Event Data Cache] [Count: {}]",cache.size());
			List<Event> events = new ArrayList<Event>();
			for(String key : cache.keySet()) {
				Event event = cache.get(key);
				if(currentTime - (event.getEndTime() + event.getReset() * 60) > 0) {
					event.setStatus(1);
					if(event.getRuleType().equals(Constant.RuleType.INDICATOR_MATHCH.getIndex())) {
						event.setEndTime(new Date().getTime()/1000);
					}
					event.setDurationS(event.getEndTime()-event.getStartTime());
					events.add(event);
					cache.remove(key);
					log.info("[DetectionEventExpiredJob] [Remove Event Data Cache] [id : {}]",event.getEventId());
				}
			}
			log.info("[DetectionEventExpiredJob] [Update data count :{}]",events.size());
			if(ObjectUtils.isNotEmpty(events)) {
				StringBuilder sb = new StringBuilder();
				sb.append("insert into cn_event_local values ");
				for(Event dto : events) {
					sb.append("(");
					sb.append(dto.getEventId()+",'"+dto.getMatchIds()+"','"+dto.getKeyFields()+"','"+dto.getKeyValues()+"',"+dto.getRuleId()+",'"
							+ dto.getRuleVersion()+"','"+dto.getRuleType()+"',"+dto.getIsBuiltin()+",'"+dto.getEventType()+"','"+dto.getEventName()
							+ "',"+dto.getReset()+","+ dto.getStartTime()+","+dto.getEndTime()+","+dto.getDurationS()+","+dto.getStatus());
					sb.append("),");
				}
				String batchInsertSql = sb.substring(0, sb.length()-1);
				log.info("[DetectionEventExpiredJob] [Save Event Data] [SQL: {}]",batchInsertSql);
				//批量存储到clickhouse
				clickHouseClient.write(clickHouseNode)
				        .format(ClickHouseFormat.RowBinaryWithNamesAndTypes)
				        .query(batchInsertSql)
				        .execute();
			}
			log.info("[DetectionEventExpiredJob] [Remaining unprocessed data count :{}]",cache.size());
			log.info("[DetectionEventExpiredJob] [Save Event Data Finished]");
		}else{
			log.info("[DetectionEventExpiredJob] [Get EVENT DATA] [MSG: {}]","NO DATA");
		}
	}

}
