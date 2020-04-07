package com.legocms.service.sys;

import com.legocms.core.dto.Page;
import com.legocms.core.dto.sys.SysUserInfo;

public interface ISysUserService {

    SysUserInfo findBy(String code);

    Page<SysUserInfo> findBy(String code, String name, int pageIndex, int pageSize);
}