package net.geedge.common.config;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.clickhouse.client.ClickHouseClient;
import com.clickhouse.client.ClickHouseNode;
import com.clickhouse.client.ClickHouseResponse;
import com.clickhouse.data.ClickHouseFormat;
import com.clickhouse.data.ClickHouseRecord;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import net.geedge.common.utils.Constant;
import net.geedge.common.utils.SnowflakeId;
import net.geedge.modules.detection.entity.Event;
import net.geedge.modules.detection.entity.MatchDto;

/**
 * 1.启动时从ck中获取正在进行的event数据 保存到本地缓存中
 * 2.从kafka获取新数据
 * 3.判断内存中是否存在，否-添加到内存中
 * 4.将处理完的数据批量插入到ck中
 */
@Component
public class DetectionRunner implements CommandLineRunner{

	private Log log = Log.get();
	
	@Autowired
	private ClickHouseNode clickHouseNode;
	
	@Autowired
	private KafkaConsumer<Object,Object> kafkaConsumer;
	
	@Value("${datacenter.id}")
	private Integer datacenterId;
	
	@Value("${work.numId}")
	private Integer workNumId;
	
	@Override
	public void run(String... args) throws Exception {
		Map<String, Event> cache = Context.DETECTTION_EVENT_CACHE;
		// 查询ck获取正在进行的event事件数据 更新到缓存中
		queryEventDataToCache(cache);
		log.info("[DetectionRunner] [Get Event Data Cache] [Count : {}]",cache.size());
		SnowflakeId snowflakeId = new SnowflakeId(datacenterId,workNumId);
		try {
			while(true) {
				ClickHouseResponse resp = null;
				ClickHouseClient clickHouseClient = null;
				try {
					Map<String,Event> eventMap = new HashMap<String,Event>();
					ConsumerRecords<Object, Object> datas = kafkaConsumer.poll(Duration.ofSeconds(60));
					log.info("[DetectionRunner] [Get Kafka Data] [Count : {}]",datas.count());
					for(ConsumerRecord<Object, Object> data:datas) {
						String value = (String) data.value();
						MatchDto match = JSON.parseObject(value, MatchDto.class);
						Event dto = new Event();
						Event eventCache = null;
						dto.setEventName(match.getEventName());
						dto.setEventType(match.getEventType());
						dto.setIsBuiltin(match.getIsBuiltin());
						dto.setRuleId(match.getRuleId());
						dto.setRuleType(Constant.RuleType.getIndex(match.getRuleType()));
						dto.setRuleVersion(match.getRuleVersion());
						
						if(match.getRuleType().equals(Constant.RuleType.INDICATOR_MATHCH.getValue())) {
							String keyFields = StrUtil.join(StrUtil.COMMA,"client_ip","server_ip",match.getIndicator_fields());
							String keyValues = StrUtil.join(StrUtil.COMMA, match.getClient_ip(),match.getServer_ip(),match.getIndicator_values());
							dto.setKeyFields(keyFields);
							dto.setKeyValues(keyValues);
						}else{
							// indicator 类型没有开始结束时间
							dto.setKeyFields(match.getKey_fields());
							dto.setKeyValues(match.getKey_values());
						}
						// 从缓存中获取历史数据
						eventCache = cache.get(StrUtil.join(StrUtil.DASHED,dto.getKeyFields(),dto.getKeyValues(),dto.getRuleId(),dto.getRuleVersion()));
						// 判断当前数据是否超过历史数据reset
						if(ObjectUtil.isNotEmpty(eventCache)) {
							dto.setEventId(eventCache.getEventId());
							if(match.getRuleType().equals(Constant.RuleType.INDICATOR_MATHCH.getValue()) 
									||match.getRuleType().equals(Constant.RuleType.THRESHOLD.getValue())) {
								Long endTime = ObjectUtil.isEmpty(match.getEnd_time())? new Date().getTime()/1000 : match.getEnd_time();
								if(endTime - (eventCache.getEndTime() + eventCache.getReset() * 60) > 0) {
									Event event = new Event();
									eventCache.setStatus(1);
									BeanUtil.copyProperties(eventCache, event, true);
									log.info("[DetectionRunner] [Remove Event Data Cache] [id : {}]",eventCache.getEventId());
									eventCache = null;
									eventMap.put(StrUtil.join(StrUtil.DASHED,event.getKeyFields(),event.getKeyValues(),event.getRuleId(),event.getRuleVersion()), event);
									// 从缓存中移除状态变更数据
									cache.remove(StrUtil.join(StrUtil.DASHED,event.getKeyFields(),event.getKeyValues(),event.getRuleId(),event.getRuleVersion()));
								}
							}
						}else {
							dto.setEventId(snowflakeId.nextId());
						}
						
						if(ObjectUtil.isEmpty(eventCache) && match.getRuleType().equals(Constant.RuleType.INDICATOR_MATHCH.getValue())) {
							dto.setStartTime(new Date().getTime()/1000);
							dto.setEndTime(new Date().getTime()/1000);
						}else if(ObjectUtil.isNotEmpty(eventCache) && match.getRuleType().equals(Constant.RuleType.INDICATOR_MATHCH.getValue())) {
							dto.setStartTime(eventCache.getStartTime());
							dto.setEndTime(new Date().getTime()/1000);
						}else if(ObjectUtil.isEmpty(eventCache) && !match.getRuleType().equals(Constant.RuleType.INDICATOR_MATHCH.getValue())) {
							dto.setStartTime(match.getStart_time());
							dto.setEndTime(match.getEnd_time());
						}else if(ObjectUtil.isNotEmpty(eventCache) && !match.getRuleType().equals(Constant.RuleType.INDICATOR_MATHCH.getValue())) {
							dto.setStartTime(eventCache.getStartTime());
							dto.setEndTime(match.getEnd_time());
						}
						
						dto.setDurationS(dto.getEndTime()-dto.getStartTime());
						
						if(match.getRuleType().equals(Constant.RuleType.SEQUENCE.getValue()) ||
								match.getRuleType().equals(Constant.RuleType.UNORDERED_SEQUENCE.getValue())) {
							dto.setStatus(1);
							dto.setMatchIds(match.getMatch_id().toString());
						}else {
							dto.setStatus(0);
							dto.setReset(match.getReset());
							if(ObjectUtil.isNotEmpty(eventCache)) {
								String matchIds = eventCache.getMatchIds();
								if(!StrUtil.contains(matchIds,match.getMatch_id().toString())) {
									matchIds = StrUtil.join(StrUtil.COMMA, matchIds,match.getMatch_id());
									dto.setMatchIds(matchIds);
								}
							}else {
								dto.setMatchIds(match.getMatch_id().toString());
							}
						}
						// 更新缓存中的数据信息
						if(dto.getStatus()!=1){
							cache.put(StrUtil.join(StrUtil.DASHED,dto.getKeyFields(),dto.getKeyValues(),dto.getRuleId(),dto.getRuleVersion()), dto);
						}
						// 更新当前保存数据内容
						eventMap.put(StrUtil.join(StrUtil.DASHED,dto.getKeyFields(),dto.getKeyValues(),dto.getRuleId(),dto.getRuleVersion()), dto);
					}
					log.info("[DetectionRunner] [Kafka Data Handler Finished]");
					if(ObjectUtil.isNotEmpty(eventMap.values())) {
						log.info("[DetectionRunner] [Ck Save Data] [count : {}]",eventMap.values().size());
						StringBuilder sb = new StringBuilder();
						sb.append("insert into cn_event_local values ");
						for(Event dto : eventMap.values()) {
							sb.append("(");
							sb.append(dto.getEventId()+",'"+dto.getMatchIds()+"','"+dto.getKeyFields()+"','"+dto.getKeyValues()+"',"+dto.getRuleId()+",'"
									+ dto.getRuleVersion()+"','"+dto.getRuleType()+"',"+dto.getIsBuiltin()+",'"+dto.getEventType()+"','"+dto.getEventName()
									+ "',"+dto.getReset()+","+ dto.getStartTime()+","+dto.getEndTime()+","+dto.getDurationS()+","+dto.getStatus());
							sb.append("),");
						}
						String batchInsertSql = sb.substring(0, sb.length()-1);
						
						//批量存储到clickhouse
						clickHouseClient = ClickHouseClient.newInstance(clickHouseNode.getProtocol());
						resp = clickHouseClient.write(clickHouseNode)
								.format(ClickHouseFormat.RowBinaryWithNamesAndTypes)
								.query(batchInsertSql)
								.executeAndWait();

						log.info("[DetectionRunner] [Ck Save Data Finished]");
					}else {
						log.info("[DetectionRunner] [ Data None]");
					}
					kafkaConsumer.commitAsync();
				}catch(Exception e) {
					e.printStackTrace();
					log.error("[DetectionRunner] [Error Info :{}]",e.getMessage());
				}finally{
					if(ObjectUtil.isNotEmpty(resp)){
						resp.close();
					}
					if(ObjectUtil.isNotEmpty(clickHouseClient)){
						clickHouseClient.close();
					}
				}
			}
		}finally {
			kafkaConsumer.close();
		}
	}
	
	
	private void queryEventDataToCache(Map<String, Event> eventCache) throws Exception {
		log.info("[DetectionRunner] [Get Ck Data To Cache ] [Start]");
		// 将ck中状态还在执行的数据查询存入本地缓存
		String cacheSql = "SELECT "
				+ "        anyLast(key_fields) AS key_fields,"
				+ "        anyLast(key_values) AS key_values,"
				+ "        anyLast(rule_id) AS rule_id,"
				+ "        anyLast(rule_version) AS rule_version,"
				+ "        anyLast(match_ids) AS match_ids,"
				+ "        max(end_time) AS end_time,"
				+ "        anyLast(start_time) AS start_time,"
				+ "        anyLast(rule_type) AS rule_type,"
				+ "        anyLast(is_builtin) AS is_builtin,"
				+ "        anyLast(event_type) AS event_type,"
				+ "        anyLast(event_name) AS event_name,"
				+ "        max(duration_s) AS duration_s,"
				+ "        anyLast(reset) AS reset,"
				+ "        event_id AS event_id,"
				+ "        max(status) AS status1"
				+ "        FROM cn_event "
				+ "        GROUP BY"
				+ "        		event_id "
				+ "		   HAVING"
				+ "        		status1 = 0"
				+ "		   ORDER BY end_time DESC";
		ClickHouseClient clickHouseClient = ClickHouseClient.newInstance(clickHouseNode.getProtocol());
		ClickHouseResponse response = clickHouseClient.write(clickHouseNode)
				.format(ClickHouseFormat.RowBinaryWithNamesAndTypes)
				.query(cacheSql)
				.executeAndWait();
		Iterable<ClickHouseRecord> records = response.records();
		for(ClickHouseRecord record : records) {
			Event event = new Event();
			event.setKeyFields(record.getValue(0).asString());
			event.setKeyValues(record.getValue(1).asString());
			event.setRuleId(record.getValue(2).asLong());
			event.setRuleVersion(record.getValue(3).asString());
			event.setMatchIds(record.getValue(4).asString());
			event.setEndTime(record.getValue(5).asLong());
			event.setStartTime(record.getValue(6).asLong());
			event.setRuleType(record.getValue(7).asInteger());
			event.setIsBuiltin(record.getValue(8).asInteger());
			event.setEventType(record.getValue(9).asString());
			event.setEventName(record.getValue(10).asString());
			event.setDurationS(record.getValue(11).asLong());
			event.setReset(record.getValue(12).asInteger());
			event.setEventId(record.getValue(13).asLong());
			event.setStatus(0);
			eventCache.put(StrUtil.join(StrUtil.DASHED,event.getKeyFields(),event.getKeyValues(),event.getRuleId(),event.getRuleVersion()), event);
		}
		response.close();
		clickHouseClient.close();
		log.info("[DetectionRunner] [Get Ck Data To Cache ] [Finished]");
	}
}
