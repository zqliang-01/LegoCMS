package com.legocms.data.act.cms;

import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.cms.CmsTemplateTypeCode;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.DeleteAction;
import com.legocms.data.dao.cms.ICmsTemplateDao;
import com.legocms.data.entities.cms.CmsTemplate;

public class DeleteCmsTemplateAction extends DeleteAction<CmsTemplate> {

    private String code;

    private ICmsTemplateDao templateDao = getDao(ICmsTemplateDao.class);

    public DeleteCmsTemplateAction(String operator, String code) {
        super(SysPermissionCode.TEMPLATE, operator);
        this.code = code;
    }

    @Override
    protected void preprocess() {
        BusinessException.check(StringUtil.isNotBlank(code), "模板编码不能为空，模板删除失败！");

        CmsTemplate template = templateDao.findByUnsureCode(code);
        BusinessException.check(template != null, "不存在编码为[{0}]的模板信息，模板修改失败！", code);

        if (CmsTemplateTypeCode.DIR.equals(template.getType().getCode())) {
            BusinessException.check(templateDao.findChildren(code).isEmpty(), "目录下存在模板文件，删除失败！");
        }
        setTargetEntity(template);
    }

    @Override
    protected void destory(CmsTemplate entity) {
        templateDao.delete(entity);
    }

}
