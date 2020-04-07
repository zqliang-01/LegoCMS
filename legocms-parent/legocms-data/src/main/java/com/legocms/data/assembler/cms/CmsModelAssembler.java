package com.legocms.data.assembler.cms;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.cms.CmsModelAttributeInfo;
import com.legocms.core.dto.cms.CmsModelInfo;
import com.legocms.data.assembler.AbstractAssembler;
import com.legocms.data.entities.cms.CmsModel;
import com.legocms.data.entities.cms.CmsModelAttribute;

@Component
public class CmsModelAssembler extends AbstractAssembler<CmsModelInfo, CmsModel> {

    @Autowired
    private CmsModelAttributeAssembler attributeAssembler;

    @Override
    public CmsModelInfo create(CmsModel entity) {
        CmsModelInfo info = new CmsModelInfo();
        info.setCode(entity.getCode());
        info.setName(entity.getName());
        info.setHasFiles(entity.isHasFiles());
        info.setHasImages(entity.isHasImages());
        info.setTemplate(typeInfoAssembler.create(entity.getTemplate()));
        if (entity.getParent() != null) {
            info.setParent(typeInfoAssembler.create(entity.getParent()));
        }
        int i = 0;
        for (CmsModelAttribute attribute : entity.getAttributes()) {
            if (attribute.getSort() > i) {
                int loseNum = attribute.getSort() - i;
                for (int j = 0; j < loseNum; j++) {
                    info.addAttribute(null);
                    ++i;
                }
            }
            ++i;
            CmsModelAttributeInfo attributeInfo = attributeAssembler.create(attribute);
            info.addAttribute(attributeInfo);
        }
        return info;
    }

    public List<SimpleTreeInfo> createSimpleTree(List<CmsModel> models) {
        List<SimpleTreeInfo> trees = new ArrayList<SimpleTreeInfo>();
        for (CmsModel model : models) {
            SimpleTreeInfo tree = new SimpleTreeInfo();
            tree.setCode(model.getCode());
            tree.setName(model.getName());
            if (model.getParent() != null) {
                tree.setParentCode(model.getParent().getCode());
            }
            trees.add(tree);
        }
        return trees;
    }
}
