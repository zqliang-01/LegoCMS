package com.legocms.core.vo.sys;

import java.util.ArrayList;
import java.util.List;

import com.legocms.core.dto.TypeInfo;
import com.legocms.core.vo.Vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SysPermissionVo extends Vo {

    private static final long serialVersionUID = 3468995611668719228L;

    private String code;
    private String url;
    private String icon;
    private int sort;
    private boolean menu;
    private TypeInfo parent;
    private List<TypeInfo> lang = new ArrayList<TypeInfo>();
}
