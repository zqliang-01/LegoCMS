package com.legocms.core.dto.sys;

import java.util.Date;

import com.legocms.core.dto.Dto;
import com.legocms.core.dto.TypeInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SysDomainInfo extends Dto {

    private static final long serialVersionUID = -5102622941953999741L;

    private String code;
    private String name;
    private String path;
    private TypeInfo site;
    private Date createTime;
}
