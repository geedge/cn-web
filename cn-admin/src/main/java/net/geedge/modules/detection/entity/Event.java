package net.geedge.modules.detection.entity;

import lombok.Data;

@Data
public class Event {

	private Long eventId;
	
	private String matchIds;
	
	private String keyFields;
	
	private String keyValues;
	
	private Long ruleId;
	
	private String ruleVersion;
	
	private Integer ruleType;
	
	private Integer isBuiltin;
	
	private String eventType;
	
	private String eventName;
	
	private Long startTime;
	
	private Long endTime;
	
	private Long durationS;
	
	private Integer status;
	
	private Integer reset;
}
