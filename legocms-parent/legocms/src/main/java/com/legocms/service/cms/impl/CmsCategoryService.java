package com.legocms.service.cms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legocms.core.dto.Page;
import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.TypeInfo;
import com.legocms.core.dto.cms.CmsCategoryInfo;
import com.legocms.core.dto.cms.CmsCategorySimpleInfo;
import com.legocms.core.vo.cms.CmsCategoryVo;
import com.legocms.data.act.cms.AddCmsCategoryAction;
import com.legocms.data.act.cms.ModifyCmsCategoryAction;
import com.legocms.data.assembler.TypeInfoAssembler;
import com.legocms.data.assembler.cms.CmsCategoryAssembler;
import com.legocms.data.dao.cms.ICmsCategoryDao;
import com.legocms.data.entities.cms.CmsCategory;
import com.legocms.service.BaseService;
import com.legocms.service.cms.ICmsCategoryService;

@Service
public class CmsCategoryService extends BaseService implements ICmsCategoryService {

    @Autowired
    private ICmsCategoryDao categoryDao;

    @Autowired
    private CmsCategoryAssembler categoryAssembler;

    @Autowired
    private TypeInfoAssembler typeInfoAssembler;

    @Override
    public List<SimpleTreeInfo> findSimpleTree(String status) {
        List<CmsCategory> categories = categoryDao.findByStatus(status);
        return categoryAssembler.createSimpleTree(categories);
    }

    @Override
    public String add(String operator, String siteCode, CmsCategoryVo vo) {
        return new AddCmsCategoryAction(operator, siteCode, vo).run();
    }

    @Override
    public String modify(String operator, String siteCode, CmsCategoryVo vo) {
        return new ModifyCmsCategoryAction(operator, siteCode, vo).run();
    }

    @Override
    public Page<CmsCategorySimpleInfo> findBy(String code, String name, String status, String parentCode, int pageIndex, int pageSize) {
        Page<CmsCategory> categories = categoryDao.findBy(code, name, status, parentCode, pageIndex, pageSize);
        return categoryAssembler.createSimpleInfoPage(categories);
    }

    @Override
    public TypeInfo findByCode(String code) {
        CmsCategory category = categoryDao.findByUnsureCode(code);
        if (category == null) {
            return null;
        }
        return typeInfoAssembler.create(category);
    }

    @Override
    public CmsCategoryInfo findInfoBy(String code) {
        CmsCategory category = categoryDao.findByUnsureCode(code);
        if (category == null) {
            return null;
        }
        return categoryAssembler.create(category);
    }

}