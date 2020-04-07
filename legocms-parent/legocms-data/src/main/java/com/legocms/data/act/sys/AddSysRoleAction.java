package com.legocms.data.act.sys;

import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.AddAction;
import com.legocms.data.dao.sys.ISysRoleDao;
import com.legocms.data.entities.sys.SysRole;

public class AddSysRoleAction extends AddAction<SysRole> {

    private String code;
    private String name;

    private ISysRoleDao roleDao = getDao(ISysRoleDao.class);

    public AddSysRoleAction(String operator, String code, String name) {
        super(SysPermissionCode.ROLE, operator);
        this.code = code;
        this.name = name;
    }

    @Override
    protected void preprocess() {
        BusinessException.check(StringUtil.isNotBlank(code), "角色编码不能为空，创建角色失败！");
        BusinessException.check(StringUtil.isNotBlank(name), "角色名称不能为空，创建角色失败！");

        SysRole role = roleDao.findByUnsureCode(code);
        BusinessException.check(role == null, "已存在编码为【{0}】的角色信息，创建角色失败！", code);
    }

    @Override
    protected SysRole createTargetEntity() {
        SysRole role = new SysRole(code);
        role.setName(name);
        roleDao.save(role);
        return role;
    }

}
