package com.legocms.data.assembler.cms;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.cms.CmsPlaceInfo;
import com.legocms.core.vo.cms.CmsPlaceType;
import com.legocms.data.assembler.AbstractAssembler;
import com.legocms.data.entities.cms.CmsPlace;

@Component
public class CmsPlaceAssembler extends AbstractAssembler<CmsPlaceInfo, CmsPlace> {

    @Override
    public CmsPlaceInfo create(CmsPlace entity) {
        CmsPlaceInfo info = new CmsPlaceInfo();
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

    public List<SimpleTreeInfo> createSimpleTree(List<CmsPlace> templates) {
        List<SimpleTreeInfo> trees = new ArrayList<SimpleTreeInfo>();
        for (CmsPlace template : templates) {
            SimpleTreeInfo tree = new SimpleTreeInfo();
            tree.setCode(template.getCode());
            tree.setName(template.getName());
            if (template.getParent() != null) {
                tree.setParentCode(template.getParent().getCode());
            }
            if (CmsPlaceType.DIR.equals(template.getType().getCode())) {
                tree.setParent(true);
            }
            trees.add(tree);
        }
        return trees;
    }
}
