package com.legocms.core.dto.cms;

import java.util.ArrayList;
import java.util.List;

import com.legocms.core.dto.Dto;
import com.legocms.core.dto.TypeInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmsModelInfo extends Dto {

    private static final long serialVersionUID = -8053175106129532879L;

    private String code;
    private String name;
    private boolean hasFiles;
    private boolean hasImages;
    private TypeInfo parent;
    private TypeInfo template;

    private List<CmsModelAttributeInfo> attributes = new ArrayList<CmsModelAttributeInfo>();

    public void addAttribute(CmsModelAttributeInfo info) {
        attributes.add(info);
    }
}