package com.legocms.data.act.cms;

import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.cms.CmsModelAttributeVo;
import com.legocms.core.vo.cms.CmsModelVo;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.AddAction;
import com.legocms.data.dao.cms.ICmsModelAttributeDao;
import com.legocms.data.dao.cms.ICmsModelDao;
import com.legocms.data.dao.cms.ICmsTemplateDao;
import com.legocms.data.entities.cms.CmsModel;
import com.legocms.data.entities.cms.CmsModelAttribute;
import com.legocms.data.entities.cms.CmsTemplate;
import com.legocms.data.entities.cms.simpletype.CmsModelAttributeType;

public class AddCmsModelAction extends AddAction<CmsModel> {

    private String siteCode;
    private CmsModelVo vo;

    private CmsModel parent;
    private CmsTemplate template;

    private ICmsModelDao modelDao = getDao(ICmsModelDao.class);
    private ICmsTemplateDao templateDao = getDao(ICmsTemplateDao.class);
    private ICmsModelAttributeDao attributeDao = getDao(ICmsModelAttributeDao.class);

    public AddCmsModelAction(String operator, String siteCode, CmsModelVo vo) {
        super(SysPermissionCode.MODEL, operator);
        this.vo = vo;
        this.siteCode = siteCode;
    }

    @Override
    protected void preprocess() {
        BusinessException.check(StringUtil.isNotBlank(vo.getCode()), "模型编码不能为空，模型创建失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getName()), "模型名称不能为空，模型创建失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getTemplate().getCode()), "模板编码不能为空，模型创建失败！");
        CmsModel model = modelDao.findByUnsureCode(vo.getCode());
        BusinessException.check(model == null, "已存在编码为[{0}]的模型数据，模型创建失败！", vo.getCode());

        if (StringUtil.isNotBlank(vo.getParent().getCode())) {
            parent = modelDao.findByCode(vo.getParent().getCode());
        }
        template = templateDao.findByUnsureCode(vo.getTemplate().getCode());
        BusinessException.check(template != null, "不存在编码为[{0}]的模板信息，模型创建失败！", vo.getTemplate().getCode());
    }

    @Override
    protected CmsModel createTargetEntity() {
        CmsModel model = new CmsModel(vo.getCode());
        model.setName(vo.getName());
        model.setHasFiles(vo.getHasFiles());
        model.setHasImages(vo.getHasImages());
        model.setTemplate(template);
        model.setParent(parent);
        model.setSite(siteDao.findByUnsureCode(siteCode));
        modelDao.save(model);
        for (CmsModelAttributeVo attributeInfo : vo.getAttributes()) {
            if (StringUtil.isBlank(attributeInfo.getCode())) {
                continue;
            }
            CmsModelAttribute attribute = new CmsModelAttribute(attributeInfo.getCode());
            attribute.setName(attributeInfo.getName());
            attribute.setRequired(attributeInfo.getRequired());
            attribute.setSort(attributeInfo.getSort());
            attribute.setType(commonDao.findByCode(CmsModelAttributeType.class, attributeInfo.getType().getCode()));
            attribute.setModel(model);
            attributeDao.save(attribute);
        }
        return model;
    }

}
