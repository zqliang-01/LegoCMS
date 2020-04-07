package com.legocms.data.act.cms;

import com.legocms.core.common.DateUtil;
import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.cms.CmsPlaceTypeCode;
import com.legocms.core.vo.cms.CmsTemplateTypeCode;
import com.legocms.core.vo.cms.CmsTemplateVo;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.ModifyAction;
import com.legocms.data.dao.cms.ICmsTemplateDao;
import com.legocms.data.dao.sys.ISysSiteDao;
import com.legocms.data.entities.cms.CmsTemplate;
import com.legocms.data.entities.cms.simpletype.CmsTemplateType;
import com.legocms.data.entities.sys.SysSite;

public class ModifyCmsTemplateAction extends ModifyAction<CmsTemplate> {

    private SysSite site;
    private CmsTemplate parent;
    private CmsTemplateType type;

    private CmsTemplateVo vo;
    private ISysSiteDao siteDao = getDao(ISysSiteDao.class);
    private ICmsTemplateDao templateDao = getDao(ICmsTemplateDao.class);

    public ModifyCmsTemplateAction(String operator, CmsTemplateVo vo) {
        super(SysPermissionCode.TEMPLATE, operator);
        this.vo = vo;
    }

    @Override
    protected void preprocess() {
        BusinessException.check(StringUtil.isNotBlank(vo.getCode()), "模板编码不能为空，模板修改失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getName()), "模板名称不能为空，模板修改失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getSiteCode()), "无在用站点，模板修改失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getType().getCode()), "模板类型不能为空，模板修改失败！");

        CmsTemplate template = templateDao.findByUnsureCode(vo.getCode());
        BusinessException.check(template != null, "不存在编码为[{0}]的模板信息，模板修改失败！", vo.getCode());

        site = siteDao.findByCode(vo.getSiteCode());
        type = commonDao.findByUnsureCode(CmsTemplateType.class, vo.getType().getCode());
        BusinessException.check(type != null, "不存在编码为[{0}]的模板类型，模板修改失败！", vo.getType().getCode());

        if (CmsPlaceTypeCode.FILE.equals(type.getCode())) {
            BusinessException.check(StringUtil.isNotBlank(vo.getContent()), "模板内容不能为空，模板修改失败！");
        }

        parent = templateDao.findByUnsureCode(vo.getParent().getCode());
        if (parent != null) {
            BusinessException.check(CmsTemplateTypeCode.DIR.equals(parent.getType().getCode()), "所选节点非目录节点，模板修改失败！");
        }

        setTargetEntity(template);
    }

    @Override
    protected void doModify(CmsTemplate entity) {
        entity.setParent(parent);
        entity.setSite(site);
        entity.setType(type);
        entity.setName(vo.getName());
        entity.setContent(vo.getContent());
        entity.setUpdateTime(DateUtil.getCurrentDate());
        templateDao.save(entity);
    }

}
