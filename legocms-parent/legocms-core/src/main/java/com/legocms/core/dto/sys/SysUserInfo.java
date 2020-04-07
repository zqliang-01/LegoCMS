package com.legocms.core.dto.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.legocms.core.dto.Dto;
import com.legocms.core.dto.TypeInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysUserInfo extends Dto {
    private static final long serialVersionUID = -6450105877713244685L;
    private String code;
    private String name;
    private String password;
    private TypeInfo organization;
    private TypeInfo status;
    private Date createTime;
    private SysSiteInfo site;
    private List<String> permissions = new ArrayList<String>();

    public void addPermission(String permission) {
        permissions.add(permission);
    }
}
