package com.legocms.core.dto.sys;

import java.util.Date;

import com.legocms.core.dto.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysRoleInfo extends Dto {
    private static final long serialVersionUID = -675190400224637949L;
    private String code;
    private String name;
    private Date createTime;
}