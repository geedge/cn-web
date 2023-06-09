package net.geedge.modules.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/11:30
 */
@Data
@TableName("cn_ip_location_built_in")
public class IpLocationBuiltIn {
    @TableId
    private Long id;

    private Integer addrType;

    private String startIp;
    
    private String endIp;
    
    private String minip;

    private String maxip;

    private double lngwgs;

    private double latwgs;

    private String continent;

    private String areacode;

    private String country;

    private String province;

    private String city;

    private String owner;

    private String isp;

    private String asnumber;

    private String timezone;

    private Integer isValid;

    private Date opTime;

    private Integer opUser;

    private Integer knowledgeId;

}
