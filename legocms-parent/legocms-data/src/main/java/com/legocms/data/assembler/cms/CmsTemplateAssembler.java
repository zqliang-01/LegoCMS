package com.legocms.data.assembler.cms;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.cms.CmsTemplateInfo;
import com.legocms.core.vo.cms.CmsTemplateTypeCode;
import com.legocms.data.assembler.AbstractAssembler;
import com.legocms.data.entities.cms.CmsTemplate;

@Component
public class CmsTemplateAssembler extends AbstractAssembler<CmsTemplateInfo, CmsTemplate> {

    @Override
    public CmsTemplateInfo create(CmsTemplate entity) {
        CmsTemplateInfo info = new CmsTemplateInfo();
        info.setCode(entity.getCode());
        info.setName(entity.getName());
        info.setContent(entity.getContent());
        info.setType(typeInfoAssembler.create(entity.getType()));
        info.setSite(typeInfoAssembler.create(entity.getSite()));
        if (entity.getParent() != null) {
            info.setParent(typeInfoAssembler.create(entity.getParent()));
        }
        return info;
    }

    public List<SimpleTreeInfo> createSimpleTree(List<CmsTemplate> templates) {
        List<SimpleTreeInfo> trees = new ArrayList<SimpleTreeInfo>();
        for (CmsTemplate template : templates) {
            SimpleTreeInfo tree = new SimpleTreeInfo();
            tree.setCode(template.getCode());
            tree.setName(template.getName());
            if (template.getParent() != null) {
                tree.setParentCode(template.getParent().getCode());
            }
            if (CmsTemplateTypeCode.DIR.equals(template.getType().getCode())) {
                tree.setParent(true);
            }
            trees.add(tree);
        }
        return trees;
    }
}
