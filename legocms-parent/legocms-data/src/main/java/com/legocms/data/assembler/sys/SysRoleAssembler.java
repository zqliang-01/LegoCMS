package com.legocms.data.assembler.sys;

import org.springframework.stereotype.Component;

import com.legocms.core.dto.sys.SysRoleInfo;
import com.legocms.data.assembler.AbstractAssembler;
import com.legocms.data.entities.sys.SysRole;

@Component
public class SysRoleAssembler extends AbstractAssembler<SysRoleInfo, SysRole> {

    @Override
    public SysRoleInfo create(SysRole entity) {
        SysRoleInfo info = new SysRoleInfo();
        info.setCode(entity.getCode());
        info.setName(entity.getName());
        info.setCreateTime(entity.getCreateTime());
        return info;
    }
}
