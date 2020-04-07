package com.legocms.core.vo.cms;

import java.util.ArrayList;
import java.util.List;

import com.legocms.core.dto.TypeInfo;
import com.legocms.core.vo.Vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmsCategoryVo extends Vo {

    private static final long serialVersionUID = 4127212221593024967L;

    private String code;
    private String name;
    private String path;
    private String contentPath;
    private TypeInfo type;
    private TypeInfo parent;
    private TypeInfo template;
    private TypeInfo status;
    private List<String> modelCodes = new ArrayList<String>();
    private int pageSize;
    private int sort;
}
