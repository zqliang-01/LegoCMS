package com.legocms.data.assembler.sys;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.legocms.core.dto.TypeInfo;
import com.legocms.core.dto.sys.SysUserInfo;
import com.legocms.data.assembler.AbstractAssembler;
import com.legocms.data.entities.sys.SysOrganization;
import com.legocms.data.entities.sys.SysPermission;
import com.legocms.data.entities.sys.SysRole;
import com.legocms.data.entities.sys.SysUser;

@Component
public class SysUserAssembler extends AbstractAssembler<SysUserInfo, SysUser> {

    @Override
    public SysUserInfo create(SysUser user) {
        SysUserInfo userInfo = new SysUserInfo();
        userInfo.setCode(user.getCode());
        userInfo.setName(user.getName());
        userInfo.setPassword(user.getPassword());
        SysOrganization organization = user.getOrganization();
        userInfo.setOrganization(new TypeInfo(organization.getCode(), organization.getName()));
        userInfo.setPermissions(getPermissions(user.getRoles()));
        return userInfo;
    }

    private List<String> getPermissions(List<SysRole> roles) {
        List<String> permissions = new ArrayList<String>();
        for (SysRole role : roles) {
            for (SysPermission permission : role.getPermissions()) {
                if (!permissions.contains(permission.getCode())) {
                    permissions.add(permission.getCode());
                }
            }
        }
        return permissions;
    }
}
