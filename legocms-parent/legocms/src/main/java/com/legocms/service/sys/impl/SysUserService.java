package com.legocms.service.sys.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legocms.core.dto.Page;
import com.legocms.core.dto.sys.SysUserDetailInfo;
import com.legocms.core.dto.sys.SysUserInfo;
import com.legocms.core.vo.sys.QuerySysUserVo;
import com.legocms.core.vo.sys.SysUserStatusCode;
import com.legocms.core.vo.sys.SysUserVo;
import com.legocms.data.act.sys.AddSysUserAction;
import com.legocms.data.act.sys.ModifySysUserAction;
import com.legocms.data.act.sys.ModifySysUserPasswordAction;
import com.legocms.data.act.sys.ModifySysUserStatusAction;
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

    @Override
    public SysUserInfo findBy(String code) {
        SysUser user = userDao.findByUnsureCode(code);
        if (user == null) {
            return null;
        }
        return userAssembler.create(user);
    }

    @Override
    public SysUserDetailInfo findDetail(String code) {
        SysUser user = userDao.findByUnsureCode(code);
        if (user == null) {
            return null;
        }
        return userAssembler.createDetail(user);
    }

    @Override
    public Page<SysUserInfo> findBy(QuerySysUserVo vo, int pageIndex, int pageSize) {
        Page<SysUser> page = userDao.findBy(vo, pageIndex, pageSize);
        return userAssembler.createInfoPage(page);
    }

    @Override
    public void active(String operator, String code) {
        new ModifySysUserStatusAction(operator, code, SysUserStatusCode.Using).run();
    }

    @Override
    public void invalid(String operator, String code) {
        new ModifySysUserStatusAction(operator, code, SysUserStatusCode.Terminated).run();
    }

    @Override
    public void add(String operator, SysUserVo vo) {
        new AddSysUserAction(operator, vo).run();
    }

    @Override
    public void modify(String operator, SysUserVo vo) {
        new ModifySysUserAction(operator, vo).run();
    }

    @Override
    public void changePassword(String operator, String userCode, String originalPassword, String newPassword) {
        new ModifySysUserPasswordAction(operator, userCode, originalPassword, newPassword).run();
    }
}
