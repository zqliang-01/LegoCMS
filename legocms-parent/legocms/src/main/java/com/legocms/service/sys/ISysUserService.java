package com.legocms.service.sys;

import com.legocms.core.dto.sys.SysUserInfo;

public interface ISysUserService {

    SysUserInfo findBy(String code);
}