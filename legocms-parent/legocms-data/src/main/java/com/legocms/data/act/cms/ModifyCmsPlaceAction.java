package com.legocms.data.act.cms;

import com.legocms.core.common.DateUtil;
import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.cms.CmsPlaceTypeCode;
import com.legocms.core.vo.cms.CmsPlaceVo;
import com.legocms.core.vo.cms.CmsTemplateTypeCode;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.ModifyAction;
import com.legocms.data.dao.cms.ICmsPlaceDao;
import com.legocms.data.entities.cms.CmsPlace;
import com.legocms.data.entities.cms.simpletype.CmsPlaceType;
import com.legocms.data.entities.sys.SysSite;

public class ModifyCmsPlaceAction extends ModifyAction<CmsPlace> {

    private CmsPlaceVo vo;

    private SysSite site;
    private CmsPlace parent;
    private CmsPlaceType type;

    private ICmsPlaceDao placeDao = getDao(ICmsPlaceDao.class);

    public ModifyCmsPlaceAction(String operator, CmsPlaceVo vo) {
        super(SysPermissionCode.PLACE, operator);
        this.vo = vo;
    }

    @Override
    protected void preprocess() {
        BusinessException.check(StringUtil.isNotBlank(vo.getCode()), "片段编码不能为空，片段修改失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getName()), "片段名称不能为空，片段修改失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getSiteCode()), "无在用站点，片段修改失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getType().getCode()), "片段类型不能为空，片段修改失败！");

        CmsPlace place = placeDao.findByUnsureCode(vo.getCode());
        BusinessException.check(place != null, "不存在编码为[{0}]的片段信息，片段修改失败！", vo.getCode());

        site = siteDao.findByCode(vo.getSiteCode());
        type = commonDao.findByUnsureCode(CmsPlaceType.class, vo.getType().getCode());
        BusinessException.check(type != null, "不存在编码为[{0}]的片段类型，片段修改失败！", vo.getType().getCode());

        if (CmsPlaceTypeCode.FILE.equals(type.getCode())) {
            BusinessException.check(StringUtil.isNotBlank(vo.getContent()), "片段内容不能为空，片段修改失败！");
        }

        parent = placeDao.findByUnsureCode(vo.getParent().getCode());
        if (parent != null) {
            BusinessException.check(CmsTemplateTypeCode.DIR.equals(parent.getType().getCode()), "所选节点非目录节点，片段修改失败！");
        }

        setTargetEntity(place);
    }

    @Override
    protected void doModify(CmsPlace entity) {
        entity.setParent(parent);
        entity.setSite(site);
        entity.setType(type);
        entity.setName(vo.getName());
        entity.setContent(vo.getContent());
        entity.setUpdateTime(DateUtil.getCurrentDate());
        placeDao.save(entity);
    }
}
