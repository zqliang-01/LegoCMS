package com.legocms.data.assembler.cms;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.legocms.core.common.FileUtil;
import com.legocms.core.dto.cms.CmsFileInfo;
import com.legocms.data.assembler.AbstractAssembler;
import com.legocms.data.entities.cms.CmsFile;

@Component
public class CmsFileAssembler extends AbstractAssembler<CmsFileInfo, CmsFile> {

    public static final List<String> EDITABLE_TYPE = Arrays.asList(".html", ".js", ".css", ".ftl", ".txt");

    @Override
    public CmsFileInfo create(CmsFile entity) {
        CmsFileInfo info = new CmsFileInfo();
        info.setCode(entity.getCode());
        info.setName(entity.getName());
        info.setPath(entity.getPath());
        info.setSize(entity.getSize());
        info.setCreateTime(entity.getCreateTime());
        info.setUpdateTime(entity.getUpdateTime());
        info.setType(typeInfoAssembler.create(entity.getType()));
        info.setEditable(EDITABLE_TYPE.contains(FileUtil.getFileType(entity.getName())));
        if (entity.getParent() != null) {
            info.setParent(typeInfoAssembler.create(entity.getParent()));
        }
        return info;
    }

}
