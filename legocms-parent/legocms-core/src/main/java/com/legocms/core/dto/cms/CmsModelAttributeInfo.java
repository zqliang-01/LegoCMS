package com.legocms.core.dto.cms;

import com.legocms.core.dto.Dto;
import com.legocms.core.dto.TypeInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmsModelAttributeInfo extends Dto {

    private static final long serialVersionUID = 1180776869572270932L;

    private String code;
    private String name;
    private boolean required;
    private TypeInfo type;
    private int sort;
}