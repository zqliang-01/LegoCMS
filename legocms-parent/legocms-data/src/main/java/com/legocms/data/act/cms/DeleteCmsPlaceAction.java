package com.legocms.data.act.cms;

import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.cms.CmsTemplateTypeCode;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.DeleteAction;
import com.legocms.data.dao.cms.ICmsPlaceDao;
import com.legocms.data.entities.cms.CmsPlace;

public class DeleteCmsPlaceAction extends DeleteAction<CmsPlace> {

    private String code;

    private ICmsPlaceDao placeDao = getDao(ICmsPlaceDao.class);

    public DeleteCmsPlaceAction(String operator, String code) {
        super(SysPermissionCode.PLACE, operator);
        this.code = code;
    }

    @Override
    protected void preprocess() {
        BusinessException.check(StringUtil.isNotBlank(code), "片段编码不能为空，片段删除失败！");

        CmsPlace place = placeDao.findByUnsureCode(code);
        BusinessException.check(place != null, "不存在编码为[{0}]的片段信息，片段修改失败！", code);

        if (CmsTemplateTypeCode.DIR.equals(place.getType().getCode())) {
            BusinessException.check(placeDao.findChildren(code).isEmpty(), "目录下存在片段文件，删除失败！");
        }
        setTargetEntity(place);
    }

    @Override
    protected void destory(CmsPlace entity) {
        placeDao.delete(entity);
    }

}
