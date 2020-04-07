package com.legocms.service.cms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legocms.core.dto.TypeInfo;
import com.legocms.data.assembler.TypeInfoAssembler;
import com.legocms.data.entities.cms.simpletype.CmsCategoryStatus;
import com.legocms.data.entities.cms.simpletype.CmsCategoryType;
import com.legocms.data.entities.cms.simpletype.CmsFileType;
import com.legocms.data.entities.cms.simpletype.CmsModelAttributeType;
import com.legocms.data.entities.cms.simpletype.CmsPlaceType;
import com.legocms.data.entities.cms.simpletype.CmsTemplateType;
import com.legocms.service.BaseService;
import com.legocms.service.cms.ICmsSimpleTypeService;

@Service
public class CmsSimpleTypeService extends BaseService implements ICmsSimpleTypeService {

    @Autowired
    private TypeInfoAssembler typeInfoAssembler;

    @Override
    public List<TypeInfo> findTemplateType() {
        List<CmsTemplateType> userStatus = commonDao.findAll(CmsTemplateType.class);
        return typeInfoAssembler.create(userStatus);
    }

    @Override
    public List<TypeInfo> findPlaceType() {
        List<CmsPlaceType> userStatus = commonDao.findAll(CmsPlaceType.class);
        return typeInfoAssembler.create(userStatus);
    }

    @Override
    public List<TypeInfo> findFileType() {
        List<CmsFileType> fileTypes = commonDao.findAll(CmsFileType.class);
        return typeInfoAssembler.create(fileTypes);
    }

    @Override
    public List<TypeInfo> findModelAttributeType() {
        List<CmsModelAttributeType> attributeTypes = commonDao.findAll(CmsModelAttributeType.class);
        return typeInfoAssembler.create(attributeTypes);
    }

    @Override
    public List<TypeInfo> findCategoryStatus() {
        List<CmsCategoryStatus> status = commonDao.findAll(CmsCategoryStatus.class);
        return typeInfoAssembler.create(status);
    }

    @Override
    public List<TypeInfo> findCategoryTypes() {
        List<CmsCategoryType> types = commonDao.findAll(CmsCategoryType.class);
        return typeInfoAssembler.create(types);
    }

}
