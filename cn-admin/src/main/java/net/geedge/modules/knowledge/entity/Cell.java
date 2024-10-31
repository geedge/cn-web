package net.geedge.modules.knowledge.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


@Data
@TableName("cn_cell")
public class Cell {
    @TableId(type = IdType.INPUT)
    private String id;

    private String cellId;

    private Integer nodebId;

    private Integer azimuth;

    private String technology;

    private Integer lacTac;

    private Integer mcc;

    private Integer mnc;

    private Double latitude;

    private Double longitude;

    private Integer coverageRadius;

    private String operator;

    private String vendor;

    private String fddSpectrum;

    @TableField(exist = false)
    private String country;
    @TableField(exist = false)
    private String superAdministrativeArea;
    @TableField(exist = false)
    private String administrativeArea;
}
