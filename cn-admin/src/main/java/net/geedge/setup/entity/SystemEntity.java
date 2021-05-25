package net.geedge.setup.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class SystemEntity implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7879602213109898774L;
	private String alertPath;
    private String alertPrefix;
    // prometheus_federation_enabled
    private String federationEnabled = "1";
    private String username;
    private String pin;
}
