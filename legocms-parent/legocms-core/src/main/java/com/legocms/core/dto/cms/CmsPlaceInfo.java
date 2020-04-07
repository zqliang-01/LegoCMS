package com.legocms.core.dto.cms;

import com.legocms.core.dto.Dto;
import com.legocms.core.dto.TypeInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmsPlaceInfo extends Dto {

    private static final long serialVersionUID = -1278841496174681951L;

    private String code;
    private String name;
    private String content;
    private TypeInfo type;
    private TypeInfo site;
    private TypeInfo parent;
}
