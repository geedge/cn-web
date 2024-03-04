package net.geedge.modules.location.entity;

import lombok.Data;

@Data
public class SubscriberDto {
	
	private String subscriberId;
	
	private String imei;
	
	private String imsi;
	
	private String phoneNumber;
	
	private String apn;
	
	private String hexId;
}
