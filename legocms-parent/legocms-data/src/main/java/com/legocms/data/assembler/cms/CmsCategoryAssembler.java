package com.legocms.data.assembler.cms;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.legocms.core.dto.Page;
import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.cms.CmsCategoryInfo;
import com.legocms.core.dto.cms.CmsCategorySimpleInfo;
import com.legocms.data.assembler.AbstractAssembler;
import com.legocms.data.entities.cms.CmsCategory;

@Component
public class CmsCategoryAssembler extends AbstractAssembler<CmsCategoryInfo, CmsCategory> {

    @Override
    public CmsCategoryInfo create(CmsCategory entity) {
        CmsCategoryInfo info = new CmsCategoryInfo();
        info.setCode(entity.getCode());
        info.setContentPath(entity.getContentPath());
        info.setName(entity.getName());
        info.setPageSize(entity.getPageSize());
        if (entity.getParent() != null) {
            info.setParent(typeInfoAssembler.create(entity.getParent()));
        }
        info.setPath(entity.getPath());
        info.setPath(entity.getPath());
        info.setSort(entity.getSort());
        info.setModelCodes(typeInfoAssembler.codes(entity.getModels()));
        info.setStatus(typeInfoAssembler.create(entity.getStatus()));
        info.setTemplate(typeInfoAssembler.create(entity.getTemplate()));
        info.setType(typeInfoAssembler.create(entity.getType()));
        return info;
    }

    public CmsCategorySimpleInfo createSimple(CmsCategory entity) {
        CmsCategorySimpleInfo info = new CmsCategorySimpleInfo();
        info.setCode(entity.getCode());
        info.setName(entity.getName());
        info.setPageSize(entity.getPageSize());
        if (entity.getParent() != null) {
            info.setParent(typeInfoAssembler.create(entity.getParent()));
        }
        info.setStatus(typeInfoAssembler.create(entity.getStatus()));
        info.setType(typeInfoAssembler.create(entity.getType()));
        return info;
    }

    public List<CmsCategorySimpleInfo> createSimple(List<CmsCategory> list) {
        List<CmsCategorySimpleInfo> infos = new ArrayList<CmsCategorySimpleInfo>();
        for (CmsCategory category : list) {
            infos.add(createSimple(category));
        }
        return infos;
    }

    public List<SimpleTreeInfo> createSimpleTree(List<CmsCategory> categories) {
        List<SimpleTreeInfo> trees = new ArrayList<SimpleTreeInfo>();
        for (CmsCategory category : categories) {
            SimpleTreeInfo tree = new SimpleTreeInfo();
            tree.setCode(category.getCode());
            tree.setName(category.getName());
            if (category.getParent() != null) {
                tree.setParentCode(category.getParent().getCode());
            }
            trees.add(tree);
        }
        return trees;
    }

    public Page<CmsCategorySimpleInfo> createSimpleInfoPage(Page<CmsCategory> ePage) {
        return new Page<CmsCategorySimpleInfo>(ePage.getParam(), createSimple(ePage.getResult()), ePage.getCurrent(), ePage.getPageSize(), ePage.getTotalCount());
    }

}
