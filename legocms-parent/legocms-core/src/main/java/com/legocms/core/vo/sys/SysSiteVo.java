package com.legocms.core.vo.sys;

import com.legocms.core.dto.TypeInfo;
import com.legocms.core.vo.Vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SysSiteVo extends Vo {

    private static final long serialVersionUID = 733472576995657436L;

    private String code;
    private String name;
    private String path;
    private String dynamicPath;
    private TypeInfo organization;
}
