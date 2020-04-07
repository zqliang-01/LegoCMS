package com.legocms.service.cms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legocms.core.dto.TypeInfo;
import com.legocms.data.assembler.TypeInfoAssembler;
import com.legocms.data.entities.cms.simpletype.PlaceType;
import com.legocms.data.entities.cms.simpletype.TemplateType;
import com.legocms.service.BaseService;
import com.legocms.service.cms.ICmsSimpleTypeService;

@Service
public class CmsSimpleTypeService extends BaseService implements ICmsSimpleTypeService {

    @Autowired
    private TypeInfoAssembler typeInfoAssembler;

    @Override
    public List<TypeInfo> findTemplateType() {
        List<TemplateType> userStatus = commonDao.findAll(TemplateType.class);
        return typeInfoAssembler.create(userStatus);
    }

    @Override
    public List<TypeInfo> findPlaceType() {
        List<PlaceType> userStatus = commonDao.findAll(PlaceType.class);
        return typeInfoAssembler.create(userStatus);
    }

}
