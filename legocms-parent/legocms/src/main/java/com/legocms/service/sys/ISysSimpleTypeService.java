package com.legocms.service.sys;

import java.util.List;

import com.legocms.core.dto.TypeInfo;

public interface ISysSimpleTypeService {

    List<TypeInfo> findUserStatus();

    List<TypeInfo> findOrganizationStatus();
}
