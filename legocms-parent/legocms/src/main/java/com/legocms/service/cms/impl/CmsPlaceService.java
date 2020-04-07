package com.legocms.service.cms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.legocms.core.common.StringUtil;
import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.TypeInfo;
import com.legocms.core.dto.cms.CmsPlaceInfo;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.cms.CmsPlaceType;
import com.legocms.core.vo.cms.CmsPlaceVo;
import com.legocms.data.assembler.cms.CmsPlaceAssembler;
import com.legocms.data.dao.cms.ICmsPlaceDao;
import com.legocms.data.dao.sys.ISysSiteDao;
import com.legocms.data.entities.cms.CmsPlace;
import com.legocms.data.entities.cms.simpletype.PlaceType;
import com.legocms.data.entities.sys.SysSite;
import com.legocms.service.BaseService;
import com.legocms.service.cms.ICmsPlaceService;

@Service
public class CmsPlaceService extends BaseService implements ICmsPlaceService {

    @Autowired
    private ICmsPlaceDao placeDao;

    @Autowired
    private ISysSiteDao siteDao;

    @Autowired
    private CmsPlaceAssembler placeAssembler;

    @Override
    public List<SimpleTreeInfo> findSimpleTree() {
        List<CmsPlace> places = placeDao.findAll(new Sort(Sort.Direction.DESC, "createDate"));
        return placeAssembler.createSimpleTree(places);
    }

    @Override
    public CmsPlaceInfo findBy(String code) {
        CmsPlace place = placeDao.findByCode(code);
        return placeAssembler.create(place);
    }

    @Override
    public void save(CmsPlaceVo vo) {
        CmsPlace place = placeDao.findByUnsureCode(vo.getCode());
        if (place == null) {
            TypeInfo siteInfo = vo.getSite();
            BusinessException.check(siteInfo != null && StringUtil.isNotBlank(siteInfo.getCode()), "无在用站点，模板创建失败！");
            SysSite site = siteDao.findByCode(siteInfo.getCode());
            place = new CmsPlace(vo.getCode(), site);
        }
        CmsPlace parent = placeDao.findByUnsureCode(vo.getParent().getCode());
        if (parent != null) {
            BusinessException.check(CmsPlaceType.DIR.equals(parent.getType().getCode()), "所选节点非目录，模板增加失败！");
            place.setParent(parent);
        }
        PlaceType placeType = commonDao.findByCode(PlaceType.class, vo.getType().getCode());
        place.setType(placeType);
        place.setName(vo.getName());
        place.setContent(vo.getContent());
        placeDao.save(place);
    }

    @Override
    public void delete(String code) {
        CmsPlace template = placeDao.findByCode(code);
        if (CmsPlaceType.DIR.equals(template.getType().getCode())) {
            BusinessException.check(placeDao.findChildren(code).isEmpty(), "目录下存在模板文件，删除失败！");
        }
        placeDao.delete(template);
    }
}