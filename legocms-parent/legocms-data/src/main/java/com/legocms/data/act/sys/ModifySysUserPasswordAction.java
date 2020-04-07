package com.legocms.data.act.sys;

import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.ModifyAction;
import com.legocms.data.entities.sys.SysUser;

public class ModifySysUserPasswordAction extends ModifyAction<SysUser> {

    private String newPassword;
    private String originalPassword;

    public ModifySysUserPasswordAction(String operator, String userCode, String originalPassword, String newPassword) {
        super(SysPermissionCode.USER, operator);
        this.newPassword = newPassword;
        this.originalPassword = originalPassword;
        setTargetEntity(userDao.findByCode(userCode));
    }

    @Override
    protected void preprocess() {
        BusinessException.check(StringUtil.isNotBlank(newPassword), "新密码不能为空，密码修改失败！");
        BusinessException.check(StringUtil.isNotBlank(originalPassword), "原密码不能为空，密码修改失败！");
        BusinessException.check(newPassword.equals(originalPassword), "新密码与原密码不能相同，密码修改失败！");
        BusinessException.check(newPassword.length() >= 8, "密码长度必须大于等于八位字符，密码修改失败！");

        boolean passwordEqual = getTargetEntity().getPassword().equals(originalPassword);
        BusinessException.check(passwordEqual, "原密码错误，密码修改失败！");
    }

    @Override
    protected void doModify(SysUser entity) {
        entity.setPassword(StringUtil.getMD5(newPassword));
        userDao.save(entity);
    }

}
