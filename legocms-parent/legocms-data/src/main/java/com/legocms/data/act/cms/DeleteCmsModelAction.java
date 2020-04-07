package com.legocms.data.act.cms;

import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.DeleteAction;
import com.legocms.data.dao.cms.ICmsModelAttributeDao;
import com.legocms.data.dao.cms.ICmsModelDao;
import com.legocms.data.entities.cms.CmsModel;

public class DeleteCmsModelAction extends DeleteAction<CmsModel> {

    private String code;

    private ICmsModelDao modelDao = getDao(ICmsModelDao.class);
    private ICmsModelAttributeDao attributeDao = getDao(ICmsModelAttributeDao.class);

    public DeleteCmsModelAction(String operator, String code) {
        super(SysPermissionCode.MODEL, operator);
        this.code = code;
    }

    @Override
    protected void preprocess() {
        BusinessException.check(StringUtil.isNotBlank(code), "模型编码不能为空，模型删除失败！");

        CmsModel model = modelDao.findByUnsureCode(code);
        BusinessException.check(model != null, "不存在编码为[{0}]的模型信息，模型删除失败！");

        setTargetEntity(model);
    }

    @Override
    protected void destory(CmsModel entity) {
        attributeDao.deleteAll(entity.getAttributes());
        modelDao.delete(entity);
    }

}
