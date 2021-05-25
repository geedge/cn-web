package net.geedge.setup.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class DatabaseEntity implements Serializable {

	private static final long serialVersionUID = 9168478023996506144L;
	private String host;
    private Integer port = 3306;
    private String name = "cn";
    private String username;
    private String pin;

    public String getUrl(){
        return "jdbc:mysql://"+this.getHost()+":"+this.getPort()+"/"+this.getName()+"?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false";
    }

}
