package com.legocms.data.act.cms;

import com.legocms.core.common.DateUtil;
import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.cms.CmsPlaceTypeCode;
import com.legocms.core.vo.cms.CmsPlaceVo;
import com.legocms.core.vo.cms.CmsTemplateTypeCode;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.AddAction;
import com.legocms.data.dao.cms.ICmsPlaceDao;
import com.legocms.data.dao.sys.ISysSiteDao;
import com.legocms.data.entities.cms.CmsPlace;
import com.legocms.data.entities.cms.simpletype.CmsPlaceType;
import com.legocms.data.entities.sys.SysSite;

public class AddCmsPlaceAction extends AddAction<CmsPlace> {

    private CmsPlaceVo vo;

    private SysSite site;
    private CmsPlace parent;
    private CmsPlaceType type;

    private ISysSiteDao siteDao = getDao(ISysSiteDao.class);
    private ICmsPlaceDao placeDao = getDao(ICmsPlaceDao.class);

    public AddCmsPlaceAction(String operator, CmsPlaceVo vo) {
        super(SysPermissionCode.PLACE, operator);
        this.vo = vo;
    }

    @Override
    protected void preprocess() {
        BusinessException.check(StringUtil.isNotBlank(vo.getName()), "片段名称不能为空，片段创建失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getType().getCode()), "片段类型不能为空，片段创建失败！");

        site = siteDao.findByUnsureCode(vo.getSiteCode());
        BusinessException.check(site != null, "无在用站点，片段创建失败！");
        type = commonDao.findByUnsureCode(CmsPlaceType.class, vo.getType().getCode());
        BusinessException.check(type != null, "不存在编码为[{0}]的片段类型，片段修改失败！", vo.getType().getCode());

        if (CmsPlaceTypeCode.FILE.equals(type.getCode())) {
            BusinessException.check(StringUtil.isNotBlank(vo.getContent()), "片段内容不能为空，片段修改失败！");
        }

        parent = placeDao.findByUnsureCode(vo.getParent().getCode());
        if (parent != null) {
            BusinessException.check(CmsTemplateTypeCode.DIR.equals(parent.getType().getCode()), "所选节点非目录节点，片段增加失败！");
        }
    }

    @Override
    protected CmsPlace createTargetEntity() {
        CmsPlace place = new CmsPlace(vo.getCode(), site);
        place.setParent(parent);
        place.setSite(site);
        place.setType(type);
        place.setName(vo.getName());
        place.setContent(vo.getContent());
        place.setUpdateTime(DateUtil.getCurrentDate());
        placeDao.save(place);
        return place;
    }

}
