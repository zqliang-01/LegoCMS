package com.legocms.core.vo.sys;

import java.util.List;

import com.legocms.core.dto.TypeInfo;
import com.legocms.core.vo.Vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SysUserVo extends Vo {

    private static final long serialVersionUID = 8048869132754698516L;

    private String code;
    private String name;
    private String status;
    private String siteCode;
    private TypeInfo organization;
    private List<String> roles;
}