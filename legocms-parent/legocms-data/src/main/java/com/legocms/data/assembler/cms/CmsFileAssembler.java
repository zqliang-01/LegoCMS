package com.legocms.data.assembler.cms;

import org.springframework.stereotype.Component;

import com.legocms.core.dto.cms.CmsFileInfo;
import com.legocms.data.assembler.AbstractAssembler;
import com.legocms.data.entities.cms.CmsFile;

@Component
public class CmsFileAssembler extends AbstractAssembler<CmsFileInfo, CmsFile> {

    @Override
    public CmsFileInfo create(CmsFile entity) {
        CmsFileInfo info = new CmsFileInfo();
        info.setCode(entity.getCode());
        info.setName(entity.getName());
        info.setPath(entity.getPath());
        info.setSize(entity.getSize());
        info.setCreateDate(entity.getCreateTime());
        info.setUpdateDate(entity.getUpdateTime());
        info.setType(typeInfoAssembler.create(entity.getType()));
        return info;
    }

}
