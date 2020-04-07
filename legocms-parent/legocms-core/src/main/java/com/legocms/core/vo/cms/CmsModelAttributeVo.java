package com.legocms.core.vo.cms;

import com.legocms.core.dto.TypeInfo;
import com.legocms.core.vo.Vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CmsModelAttributeVo extends Vo {

    private static final long serialVersionUID = 7017808686332843004L;

    private String code;
    private String name;
    private boolean required;
    private int sort;
    private TypeInfo type;

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean getRequired() {
        return this.required;
    }
}