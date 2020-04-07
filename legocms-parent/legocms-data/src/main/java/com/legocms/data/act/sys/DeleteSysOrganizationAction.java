package com.legocms.data.act.sys;

import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.DeleteAction;
import com.legocms.data.dao.sys.ISysOrganizationDao;
import com.legocms.data.entities.sys.SysOrganization;

public class DeleteSysOrganizationAction extends DeleteAction<SysOrganization> {

    private String code;

    private ISysOrganizationDao organizationDao = getDao(ISysOrganizationDao.class);

    public DeleteSysOrganizationAction(String operator, String code) {
        super(SysPermissionCode.ORGANIZATION, operator);
        this.code = code;
    }

    @Override
    protected void preprocess() {
        BusinessException.check(userDao.findBy(code).isEmpty(), "该部门存在员工，删除失败！");
        BusinessException.check(organizationDao.findChildren(code).isEmpty(), "存在下级组织，删除失败！");

        setTargetEntity(organizationDao.findByCode(code));
    }

    @Override
    protected void destory(SysOrganization entity) {
        organizationDao.delete(entity);
    }

}
