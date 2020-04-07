package com.legocms.data.assembler.cms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.legocms.core.common.FileUtil;
import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.cms.CmsFileInfo;
import com.legocms.core.vo.cms.CmsTemplateTypeCode;
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

    public List<SimpleTreeInfo> createSimpleTree(List<CmsFile> files) {

        List<SimpleTreeInfo> trees = new ArrayList<SimpleTreeInfo>();
        for (CmsFile file : files) {
            SimpleTreeInfo tree = new SimpleTreeInfo();
            tree.setCode(file.getCode());
            tree.setName(file.getName());
            tree.setPath(file.getPath());
            if (file.getParent() != null) {
                tree.setParentCode(file.getParent().getCode());
            }
            if (CmsTemplateTypeCode.DIR.equals(file.getType().getCode())) {
                tree.setParent(true);
            }
            trees.add(tree);
        }
        return trees;
    }
}
