package net.geedge.common.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

	@Value("${kafka.bootstrap-servers}")
	private String server;
	@Value("${kafka.consumer.group-id}")
	private String groupId;
	@Value("${kafka.consumer.max-poll-records:1000}")
	private String maxPollRecords;
	@Value("${kafka.consumer.enable-auto-commit:false}")
	private String enableAutoCommit;
	
	@Bean
    public KafkaConsumer<Object, Object> kafkaConsumer() {
        Map<String, Object> consumerProps = new HashMap<>();
        //认证配置
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        consumerProps.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        List<String> topics = new ArrayList<String>();
		topics.add("MATCH_INDICATOR");
		topics.add("MATCH_THRESHOLD");
		topics.add("MATCH_SEQUENCE");
		topics.add("MATCH_UNORDERED_SEQUENCE");
		KafkaConsumer<Object, Object> consumer = new KafkaConsumer<>(consumerProps);
		consumer.subscribe(topics);
		return consumer;
    }

}
