package net.geedge.setup.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class RedisEntity implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 482413912244656102L;
	private String host;
    private Integer port = 6379;
    private String pin ;
}
