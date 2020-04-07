package com.legocms.core.vo.cms;

import com.legocms.core.dto.TypeInfo;
import com.legocms.core.vo.Vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CmsTemplateVo extends Vo {

    private static final long serialVersionUID = -4679939754273492271L;

    private String code;
    private String name;
    private String content;
    private TypeInfo type;
    private TypeInfo site;
    private TypeInfo parent;
}
