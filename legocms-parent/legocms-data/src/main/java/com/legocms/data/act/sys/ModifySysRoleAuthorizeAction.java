package com.legocms.data.act.sys;

import java.util.List;

import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.ModifyAction;
import com.legocms.data.dao.sys.ISysPermissionDao;
import com.legocms.data.dao.sys.ISysRoleDao;
import com.legocms.data.entities.sys.SysRole;

public class ModifySysRoleAuthorizeAction extends ModifyAction<SysRole> {

    private String code;
    private List<String> permissionCodes;

    private ISysRoleDao roleDao = getDao(ISysRoleDao.class);
    private ISysPermissionDao permissionDao = getDao(ISysPermissionDao.class);

    public ModifySysRoleAuthorizeAction(String operator, String code, List<String> permissionCodes) {
        super(SysPermissionCode.ROLE, operator);
        this.code = code;
        this.permissionCodes = permissionCodes;
    }

    @Override
    protected void preprocess() {
        SysRole role = roleDao.findByUnsureCode(code);
        BusinessException.check(role != null, "不已存在编码为【{0}】的角色信息，角色修改失败！", code);
        setTargetEntity(role);
    }

    @Override
    protected void doModify(SysRole entity) {
        entity.setPermissions(permissionDao.findByCodes(permissionCodes));
        roleDao.save(entity);
    }

}
