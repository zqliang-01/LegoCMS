package com.legocms.core.dto.sys;

import java.util.Date;

import com.legocms.core.dto.Dto;
import com.legocms.core.dto.TypeInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserDetailInfo extends Dto {

    private static final long serialVersionUID = -538138738907890857L;

    private String code;
    private String name;
    private TypeInfo organization;
    private TypeInfo status;
    private Date createDate;
}
