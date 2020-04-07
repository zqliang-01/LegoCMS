package com.legocms.core.dto.sys;

import com.legocms.core.dto.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysPermissionInfo extends Dto {
    private static final long serialVersionUID = -4475380801583290037L;
    private String code;
    private String url;
    private String icon;
    private String name;
    private boolean hasChildren;
    private int sort;
}
