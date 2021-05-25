package net.geedge.setup.entity;

import java.io.Serializable;

import lombok.Data;
@Data
public class SetupEntity implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3573525758058342563L;
	private RedisEntity redis;
    private DatabaseEntity database;
    private SystemEntity system;
    private String code;
}
