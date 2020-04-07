package com.legocms.service.cms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.legocms.core.common.StringUtil;
import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.cms.CmsTemplateInfo;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.cms.CmsTemplateTypeCode;
import com.legocms.core.vo.cms.CmsTemplateVo;
import com.legocms.data.assembler.cms.CmsTemplateAssembler;
import com.legocms.data.dao.cms.ICmsTemplateDao;
import com.legocms.data.dao.sys.ISysSiteDao;
import com.legocms.data.entities.cms.CmsTemplate;
import com.legocms.data.entities.cms.simpletype.CmsTemplateType;
import com.legocms.data.entities.sys.SysSite;
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
        List<CmsTemplate> templates = templateDao.findAll(new Sort(Sort.Direction.ASC, "type.code", "name"));
        return templateAssembler.createSimpleTree(templates);
    }

    @Override
    public CmsTemplateInfo findBy(String code) {
        CmsTemplate template = templateDao.findByCode(code);
        return templateAssembler.create(template);
    }

    @Override
    public void save(CmsTemplateVo vo) {
        CmsTemplate template = templateDao.findByUnsureCode(vo.getCode());
        if (template == null) {
            BusinessException.check(StringUtil.isNotBlank(vo.getSiteCode()), "无在用站点，模板创建失败！");
            SysSite site = siteDao.findByCode(vo.getSiteCode());
            template = new CmsTemplate(vo.getCode(), site);
        }
        CmsTemplate parent = templateDao.findByUnsureCode(vo.getParent().getCode());
        if (parent != null) {
            BusinessException.check(CmsTemplateTypeCode.DIR.equals(parent.getType().getCode()), "所选节点非目录，模板增加失败！");
            template.setParent(parent);
        }
        CmsTemplateType templateType = commonDao.findByCode(CmsTemplateType.class, vo.getType().getCode());
        template.setType(templateType);
        template.setName(vo.getName());
        template.setContent(vo.getContent());
        templateDao.save(template);
    }

    @Override
    public void delete(String code) {
        CmsTemplate template = templateDao.findByCode(code);
        if (CmsTemplateTypeCode.DIR.equals(template.getType().getCode())) {
            BusinessException.check(templateDao.findChildren(code).isEmpty(), "目录下存在模板文件，删除失败！");
        }
        templateDao.delete(template);
    }
}