package com.legocms.data.act.cms;

import com.legocms.core.common.DateUtil;
import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.cms.CmsPlaceTypeCode;
import com.legocms.core.vo.cms.CmsTemplateTypeCode;
import com.legocms.core.vo.cms.CmsTemplateVo;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.AddAction;
import com.legocms.data.dao.cms.ICmsTemplateDao;
import com.legocms.data.entities.cms.CmsTemplate;
import com.legocms.data.entities.cms.simpletype.CmsTemplateType;
import com.legocms.data.entities.sys.SysSite;

public class AddCmsTemplateAction extends AddAction<CmsTemplate> {

    private SysSite site;
    private CmsTemplate parent;
    private CmsTemplateType type;

    private CmsTemplateVo vo;
    private ICmsTemplateDao templateDao = getDao(ICmsTemplateDao.class);

    public AddCmsTemplateAction(String operator, CmsTemplateVo vo) {
        super(SysPermissionCode.TEMPLATE, operator);
        this.vo = vo;
    }

    @Override
    protected void preprocess() {
        BusinessException.check(StringUtil.isNotBlank(vo.getName()), "模板名称不能为空，模板创建失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getSiteCode()), "无在用站点，模板创建失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getType().getCode()), "模板类型不能为空，模板创建失败！");

        site = siteDao.findByCode(vo.getSiteCode());
        type = commonDao.findByUnsureCode(CmsTemplateType.class, vo.getType().getCode());
        BusinessException.check(type != null, "不存在编码为[{0}]的模板类型，模板修改失败！", vo.getType().getCode());

        if (CmsPlaceTypeCode.FILE.equals(type.getCode())) {
            BusinessException.check(StringUtil.isNotBlank(vo.getContent()), "模板内容不能为空，模板创建失败！");
        }

        parent = templateDao.findByUnsureCode(vo.getParent().getCode());
        if (parent != null) {
            BusinessException.check(CmsTemplateTypeCode.DIR.equals(parent.getType().getCode()), "所选节点非目录节点，模板增加失败！");
        }
    }

    @Override
    protected CmsTemplate createTargetEntity() {
        CmsTemplate template = new CmsTemplate(vo.getCode(), site);
        template.setParent(parent);
        template.setSite(site);
        template.setType(type);
        template.setName(vo.getName());
        template.setContent(vo.getContent());
        template.setUpdateTime(DateUtil.getCurrentDate());
        templateDao.save(template);
        return template;
    }

}
