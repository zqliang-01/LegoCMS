package com.legocms.service.cms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.cms.CmsModelInfo;
import com.legocms.core.vo.cms.CmsModelVo;
import com.legocms.data.act.cms.AddCmsModelAction;
import com.legocms.data.act.cms.DeleteCmsModelAction;
import com.legocms.data.act.cms.ModifyCmsModelAction;
import com.legocms.data.assembler.cms.CmsModelAssembler;
import com.legocms.data.dao.cms.ICmsModelDao;
import com.legocms.data.entities.cms.CmsModel;
import com.legocms.service.BaseService;
import com.legocms.service.cms.ICmsModelService;

@Service
public class CmsModelService extends BaseService implements ICmsModelService {

    @Autowired
    private ICmsModelDao modelDao;

    @Autowired
    private CmsModelAssembler modelAssembler;

    @Override
    public List<SimpleTreeInfo> findSimpleTree() {
        List<CmsModel> models = modelDao.findAll();
        return modelAssembler.createSimpleTree(models);
    }

    @Override
    public CmsModelInfo findBy(String code) {
        CmsModel model = modelDao.findByCode(code);
        return modelAssembler.create(model);
    }

    @Override
    public String add(String operator, String siteCode, CmsModelVo vo) {
        return new AddCmsModelAction(operator, siteCode, vo).run();
    }

    @Override
    public String modify(String operator, String siteCode, CmsModelVo vo) {
        return new ModifyCmsModelAction(operator, siteCode, vo).run();
    }

    @Override
    public void delete(String operator, String code) {
        new DeleteCmsModelAction(operator, code).run();
    }

    @Override
    public List<CmsModelInfo> findByParent(String parentCode) {
        List<CmsModel> models = modelDao.findByParent(parentCode);
        return modelAssembler.create(models);
    }

}