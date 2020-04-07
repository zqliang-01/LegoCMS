package com.legocms.core.dto.cms;

import com.legocms.core.dto.Dto;
import com.legocms.core.dto.TypeInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmsCategorySimpleInfo extends Dto {

    private static final long serialVersionUID = 3553718262466989460L;

    private String code;
    private String name;
    private TypeInfo type;
    private TypeInfo parent;
    private int pageSize;
    private TypeInfo status;
}
