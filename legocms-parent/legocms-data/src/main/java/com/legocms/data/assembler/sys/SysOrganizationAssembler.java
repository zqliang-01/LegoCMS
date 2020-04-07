package com.legocms.data.assembler.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.dto.sys.SysOrganizationInfo;
import com.legocms.data.assembler.AbstractAssembler;
import com.legocms.data.assembler.TypeInfoAssembler;
import com.legocms.data.entities.sys.SysOrganization;

@Component
public class SysOrganizationAssembler extends AbstractAssembler<SysOrganizationInfo, SysOrganization> {

    @Autowired
    private TypeInfoAssembler typeInfoAssembler;

    @Override
    public SysOrganizationInfo create(SysOrganization entity) {
        SysOrganizationInfo info = new SysOrganizationInfo();
        info.setCode(entity.getCode());
        info.setName(entity.getName());
        info.setState(typeInfoAssembler.create(entity.getStatus()));
        return info;
    }
}
