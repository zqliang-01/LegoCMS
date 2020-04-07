package com.legocms.data.act.sys;

import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.ModifyAction;
import com.legocms.data.entities.sys.SysUser;
import com.legocms.data.entities.sys.simpletype.SysUserStatus;

public class ModifySysUserStatusAction extends ModifyAction<SysUser> {

    private String statusCode;

    public ModifySysUserStatusAction(String operator, String userCode, String statusCode) {
        super(SysPermissionCode.USER, operator);
        this.statusCode = statusCode;
        setTargetEntity(userDao.findByCode(userCode));
    }

    @Override
    protected void doModify(SysUser entity) {
        entity.setStatus(commonDao.findByCode(SysUserStatus.class, statusCode));
        userDao.save(entity);
    }

}
