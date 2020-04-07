package com.legocms.data.assembler.sys;

import org.springframework.stereotype.Component;

import com.legocms.core.dto.sys.SysDomainInfo;
import com.legocms.data.assembler.AbstractAssembler;
import com.legocms.data.entities.sys.SysDomain;

@Component
public class SysDomainAssembler extends AbstractAssembler<SysDomainInfo, SysDomain> {

    @Override
    public SysDomainInfo create(SysDomain entity) {
        SysDomainInfo info = new SysDomainInfo();
        info.setCode(entity.getCode());
        info.setName(entity.getName());
        info.setPath(entity.getPath());
        info.setSite(typeInfoAssembler.create(entity.getSite()));
        info.setCreateTime(entity.getCreateTime());
        return info;
    }

}
