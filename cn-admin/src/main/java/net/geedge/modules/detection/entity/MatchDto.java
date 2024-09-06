package net.geedge.modules.detection.entity;

import lombok.Data;

@Data
public class MatchDto {

	private String key_fields;
	
	private String key_values;
	
	private String indicator_fields;
	
	private String indicator_values;
	
	private Integer match_num;
	
	private Integer reset;
	
	private String client_ip;
	
	private String client_country_region;
	
	private String client_super_admin_area;
	
	private String client_admin_area;
	
	private Double client_longitude;
	
	private Double client_latitude;
	
	private String server_ip;
	
	private String server_country_region;
	
	private String server_super_admin_area;
	
	private String server_admin_area;
	
	private Double server_longitude;
	
	private Double server_latitude;
	
	private String domain;
	
	private String app;
	
	private Integer match_time;
		
	private String event_info;
	
	private Integer threshold_value;
	
	private Integer metric_value;

	private Integer unit;

	private Long start_time;
	
	private Long end_time;
	
	private Long match_id;
	
	private Long ruleId	;
	
	private String ruleVersion;
	
	private String ruleType;
	
	private Integer isBuiltin;
	
	private String eventType;
	
	private String eventName;
	
	private Integer severity;
}
