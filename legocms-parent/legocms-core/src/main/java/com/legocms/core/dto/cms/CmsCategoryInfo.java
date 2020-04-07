package com.legocms.core.dto.cms;

import java.util.ArrayList;
import java.util.List;

import com.legocms.core.dto.Dto;
import com.legocms.core.dto.TypeInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmsCategoryInfo extends Dto {

    private static final long serialVersionUID = 6286481533630574991L;

    private String code;
    private String name;
    private String path;
    private String contentPath;
    private TypeInfo type;
    private TypeInfo template;
    private TypeInfo status;
    private TypeInfo parent;
    private List<String> modelCodes = new ArrayList<String>();
    private int pageSize;
    private int sort;
}
