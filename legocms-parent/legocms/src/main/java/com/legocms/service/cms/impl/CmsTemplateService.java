package com.legocms.service.cms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.cms.CmsTemplateInfo;
import com.legocms.core.vo.cms.CmsTemplateVo;
import com.legocms.data.act.cms.AddCmsTemplateAction;
import com.legocms.data.act.cms.DeleteCmsTemplateAction;
import com.legocms.data.act.cms.ModifyCmsTemplateAction;
import com.legocms.data.assembler.cms.CmsTemplateAssembler;
import com.legocms.data.dao.cms.ICmsTemplateDao;
import com.legocms.data.dao.sys.ISysSiteDao;
import com.legocms.data.entities.cms.CmsTemplate;
import com.legocms.service.BaseService;
import com.legocms.service.cms.ICmsTemplateService;

@Service
public class CmsTemplateService extends BaseService implements ICmsTemplateService {

    @Autowired
    private ICmsTemplateDao templateDao;

    @Autowired
    private ISysSiteDao siteDao;

    @Autowired
    private CmsTemplateAssembler templateAssembler;

    @Override
    public List<SimpleTreeInfo> findSimpleTree() {
        List<CmsTemplate> templates = templateDao.findAll(Sort.by("type.code", "name"));
        return templateAssembler.createSimpleTree(templates);
    }

    @Override
    public CmsTemplateInfo findBy(String code) {
        CmsTemplate template = templateDao.findByCode(code);
        return templateAssembler.create(template);
    }

    @Override
    public String add(String operator, CmsTemplateVo vo) {
        return new AddCmsTemplateAction(operator, vo).run();
    }

    @Override
    public String modify(String operator, CmsTemplateVo vo) {
        return new ModifyCmsTemplateAction(operator, vo).run();
    }

    @Override
    public void delete(String operator, String code) {
        new DeleteCmsTemplateAction(operator, code).run();
    }
}