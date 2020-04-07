package com.legocms.data.assembler.sys;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.dto.sys.SysUserDetailInfo;
import com.legocms.core.dto.sys.SysUserInfo;
import com.legocms.data.assembler.AbstractAssembler;
import com.legocms.data.assembler.TypeInfoAssembler;
import com.legocms.data.entities.sys.SysPermission;
import com.legocms.data.entities.sys.SysRole;
import com.legocms.data.entities.sys.SysUser;

@Component
public class SysUserAssembler extends AbstractAssembler<SysUserInfo, SysUser> {

    @Autowired
    private TypeInfoAssembler typeInfoAssembler;

    @Override
    public SysUserInfo create(SysUser user) {
        SysUserInfo userInfo = new SysUserInfo();
        userInfo.setCode(user.getCode());
        userInfo.setName(user.getName());
        userInfo.setPassword(user.getPassword());
        userInfo.setCreateDate(user.getCreateDate());
        userInfo.setOrganization(typeInfoAssembler.create(user.getOrganization()));
        userInfo.setStatus(typeInfoAssembler.create(user.getStatus()));
        userInfo.setPermissions(getPermissions(user.getRoles()));
        return userInfo;
    }

    public SysUserDetailInfo createDetail(SysUser user) {
        SysUserDetailInfo userInfo = new SysUserDetailInfo();
        userInfo.setCode(user.getCode());
        userInfo.setName(user.getName());
        userInfo.setCreateDate(user.getCreateDate());
        userInfo.setOrganization(typeInfoAssembler.create(user.getOrganization()));
        userInfo.setStatus(typeInfoAssembler.create(user.getStatus()));
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

    @Override
    public List<SysUserInfo> create(List<SysUser> entities) {
        List<SysUserInfo> infos = new ArrayList<SysUserInfo>();
        for (SysUser entity : entities) {
            infos.add(create(entity));
        }
        return infos;
    }
}
