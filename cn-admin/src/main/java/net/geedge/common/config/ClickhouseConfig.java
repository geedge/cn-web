package net.geedge.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.clickhouse.client.ClickHouseClient;
import com.clickhouse.client.ClickHouseCredentials;
import com.clickhouse.client.ClickHouseNode;

import net.geedge.common.utils.Constant;
import net.geedge.common.utils.Tool;

@Configuration
public class ClickhouseConfig {
	
	@Value("${clickhouse.username}")
	private String clickhouseUsername;
	
	@Value("${clickhouse.pin}")
	private String clickhousePin;
	
	@Value("${clickhouse.url}")
	private String url;
	
	@Bean
	public ClickHouseNode clickHouseNode() {
		clickhousePin = Tool.AesUtil.decrypt(this.clickhousePin,Constant.AES_SECRET_KEY);
		ClickHouseCredentials  credentials= ClickHouseCredentials
				.fromUserAndPassword(clickhouseUsername, clickhousePin);
		ClickHouseNode server = ClickHouseNode.builder(
				ClickHouseNode.of(url)).credentials(credentials).build();
		return server;
	}
	
	@Bean
	public ClickHouseClient clickHouseClient() {
		ClickHouseClient client = ClickHouseClient.newInstance(clickHouseNode().getProtocol());
		return client;
	}
}
