package com.legocms.data.act.sys;

import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.DeleteAction;
import com.legocms.data.dao.sys.ISysRoleDao;
import com.legocms.data.entities.sys.SysRole;

public class DeleteSysRoleAction extends DeleteAction<SysRole> {

    private String code;

    private ISysRoleDao roleDao = getDao(ISysRoleDao.class);

    public DeleteSysRoleAction(String operator, String code) {
        super(SysPermissionCode.ROLE, operator);
        this.code = code;
    }

    @Override
    protected void preprocess() {
        BusinessException.check(StringUtil.isNotBlank(code), "角色编号不能为空，角色删除失败！");

        SysRole role = roleDao.findByUnsureCode(code);
        BusinessException.check(role != null, "不存在编号为[{0}]的角色信息，角色删除失败！", code);
        setTargetEntity(role);
    }

    @Override
    protected void destory(SysRole entity) {
        roleDao.delete(entity);
    }

}
