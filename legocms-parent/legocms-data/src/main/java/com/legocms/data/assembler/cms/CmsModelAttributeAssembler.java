package com.legocms.data.assembler.cms;

import org.springframework.stereotype.Component;

import com.legocms.core.dto.cms.CmsModelAttributeInfo;
import com.legocms.data.assembler.AbstractAssembler;
import com.legocms.data.entities.cms.CmsModelAttribute;

@Component
public class CmsModelAttributeAssembler extends AbstractAssembler<CmsModelAttributeInfo, CmsModelAttribute> {

    @Override
    public CmsModelAttributeInfo create(CmsModelAttribute entity) {
        CmsModelAttributeInfo info = new CmsModelAttributeInfo();
        info.setCode(entity.getCode());
        info.setName(entity.getName());
        info.setRequired(entity.isRequired());
        info.setSort(entity.getSort());
        info.setType(typeInfoAssembler.create(entity.getType()));
        return info;
    }

}
