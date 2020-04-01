package com.legocms.service.sys;

import java.util.List;

import com.legocms.core.dto.sys.SysPermissionInfo;

public interface ISysPermissionService {

    List<SysPermissionInfo> findByParent(String parentCode, boolean menu);
}
