package com.legocms.service.sys.impl;

import org.springframework.stereotype.Service;

import com.legocms.core.dto.sys.SysUserInfo;
import com.legocms.service.BaseService;
import com.legocms.service.sys.ISysUserService;

@Service
public class SysUserService extends BaseService implements ISysUserService {

    public SysUserInfo findBy(String code) {
        SysUserInfo userInfo = new SysUserInfo();
        userInfo.setCode("1");
        userInfo.setName("test");
        userInfo.setPassword("test");
        userInfo.addPermission("permission:admin");
        return userInfo;
    }
}
