package com.legocms.service.sys.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legocms.core.dto.sys.SysUserInfo;
import com.legocms.data.assembler.sys.SysUserAssembler;
import com.legocms.data.dao.sys.ISysUserDao;
import com.legocms.data.entities.sys.SysUser;
import com.legocms.service.BaseService;
import com.legocms.service.sys.ISysUserService;

@Service
public class SysUserService extends BaseService implements ISysUserService {

    @Autowired
    private ISysUserDao userDao;

    @Autowired
    private SysUserAssembler userAssembler;

    public SysUserInfo findBy(String code) {
        SysUser user = userDao.findByUnsureCode(code);
        if (user == null) {
            return null;
        }
        return userAssembler.create(user);
    }
}
