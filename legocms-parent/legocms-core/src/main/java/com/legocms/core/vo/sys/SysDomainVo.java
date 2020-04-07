package com.legocms.core.vo.sys;

import com.legocms.core.vo.Vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SysDomainVo extends Vo {

    private static final long serialVersionUID = -7817582187513020978L;

    private String code;
    private String name;
    private String path;
    private String siteCode;
}
