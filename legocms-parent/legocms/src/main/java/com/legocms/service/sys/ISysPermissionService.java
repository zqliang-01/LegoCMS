package com.legocms.service.sys;

import java.util.List;

import com.legocms.core.dto.sys.SysPermissionInfo;

public interface ISysPermissionService {

    List<SysPermissionInfo> findBy(String userCode, String parentCode, boolean menu);
}
