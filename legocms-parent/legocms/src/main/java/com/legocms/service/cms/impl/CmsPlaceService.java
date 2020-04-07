package com.legocms.service.cms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.cms.CmsPlaceInfo;
import com.legocms.core.vo.cms.CmsPlaceVo;
import com.legocms.data.act.cms.AddCmsPlaceAction;
import com.legocms.data.act.cms.DeleteCmsPlaceAction;
import com.legocms.data.act.cms.ModifyCmsPlaceAction;
import com.legocms.data.assembler.cms.CmsPlaceAssembler;
import com.legocms.data.dao.cms.ICmsPlaceDao;
import com.legocms.data.entities.cms.CmsPlace;
import com.legocms.service.BaseService;
import com.legocms.service.cms.ICmsPlaceService;

@Service
public class CmsPlaceService extends BaseService implements ICmsPlaceService {

    @Autowired
    private ICmsPlaceDao placeDao;

    @Autowired
    private CmsPlaceAssembler placeAssembler;

    @Override
    public List<SimpleTreeInfo> findSimpleTree() {
        List<CmsPlace> places = placeDao.findAll(Sort.by("type.code", "name"));
        return placeAssembler.createSimpleTree(places);
    }

    @Override
    public CmsPlaceInfo findBy(String code) {
        CmsPlace place = placeDao.findByCode(code);
        return placeAssembler.create(place);
    }

    @Override
    public String add(String operator, CmsPlaceVo vo) {
        return new AddCmsPlaceAction(operator, vo).run();
    }

    @Override
    public String modify(String operator, CmsPlaceVo vo) {
        return new ModifyCmsPlaceAction(operator, vo).run();
    }

    @Override
    public void delete(String operator, String code) {
        new DeleteCmsPlaceAction(operator, code).run();
    }
}