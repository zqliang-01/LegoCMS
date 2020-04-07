package com.legocms.core.dto.sys;

import java.util.Date;

import com.legocms.core.dto.Dto;
import com.legocms.core.dto.TypeInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysSiteInfo extends Dto {

    private static final long serialVersionUID = -6471217681243476255L;

    private String code;
    private String name;
    private String path;
    private String dynamicPath;
    private TypeInfo organization;
    private boolean isManageSite;
    private Date createDate;
}
