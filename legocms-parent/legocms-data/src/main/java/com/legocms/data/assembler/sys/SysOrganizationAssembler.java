package com.legocms.data.assembler.sys;

import com.legocms.core.dto.sys.SysOrganizationInfo;
import com.legocms.data.assembler.AbstractAssembler;
import com.legocms.data.entities.sys.SysOrganization;

public class SysOrganizationAssembler extends AbstractAssembler<SysOrganizationInfo, SysOrganization> {

    @Override
    public SysOrganizationInfo create(SysOrganization entity) {
        SysOrganizationInfo info = new SysOrganizationInfo();
        info.setCode(entity.getCode());
        info.setName(entity.getName());
        info.setState(entity.isState());
        return info;
    }

}
