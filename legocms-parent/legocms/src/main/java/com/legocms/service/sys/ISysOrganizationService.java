package com.legocms.service.sys;

import java.util.List;

import com.legocms.core.dto.SimpleTreeInfo;

public interface ISysOrganizationService {

    List<SimpleTreeInfo> findSimpleTree();
}
