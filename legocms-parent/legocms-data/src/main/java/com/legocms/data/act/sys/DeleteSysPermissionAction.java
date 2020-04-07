package com.legocms.data.act.sys;

import java.util.List;

import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.DeleteAction;
import com.legocms.data.dao.sys.ISysPermissionLangDao;
import com.legocms.data.dao.sys.ISysRoleDao;
import com.legocms.data.entities.sys.SysPermission;
import com.legocms.data.entities.sys.SysPermissionLang;

public class DeleteSysPermissionAction extends DeleteAction<SysPermission> {

    private String code;

    private ISysRoleDao roleDao = getDao(ISysRoleDao.class);
    private ISysPermissionLangDao permissionLangDao = getDao(ISysPermissionLangDao.class);

    public DeleteSysPermissionAction(String operator, String code) {
        super(SysPermissionCode.PERMISSION, operator);
        this.code = code;
    }

    @Override
    protected void preprocess() {
        BusinessException.check(StringUtil.isNotBlank(code), "授权编码不能为空，授权删除失败！");

        SysPermission permission = permissionDao.findByUnsureCode(code);
        BusinessException.check(permission != null, "不存在编号为[{0}]的授权信息，授权删除失败！", code);
        BusinessException.check(roleDao.findByPermission(permission).isEmpty(), "该菜单已授权，删除失败！");
        BusinessException.check(permissionDao.findChildren(code).isEmpty(), "存在下级菜单，删除失败！");

        setTargetEntity(permission);

    }

    @Override
    protected void destory(SysPermission entity) {
        List<SysPermissionLang> permissionLangs = permissionLangDao.findBy(entity);
        permissionLangDao.deleteInBatch(permissionLangs);
        permissionDao.delete(entity);
    }

}
