package com.legocms.service.cms;

import java.util.List;

import com.legocms.core.dto.TypeInfo;

public interface ICmsSimpleTypeService {

    List<TypeInfo> findTemplateType();

    List<TypeInfo> findPlaceType();

    List<TypeInfo> findFileType();

    List<TypeInfo> findModelAttributeType();

    List<TypeInfo> findCategoryStatus();

    List<TypeInfo> findCategoryTypes();
}
