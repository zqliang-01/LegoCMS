package com.legocms.core.vo.sys;

import com.legocms.core.dto.TypeInfo;
import com.legocms.core.vo.Vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SysOrganizationVo extends Vo {

    private static final long serialVersionUID = -69797320014566407L;

    private String code;
    private String name;
    private TypeInfo status;
    private TypeInfo parent;
}
