package com.legocms.data.act.sys;

import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.ModifyAction;
import com.legocms.data.dao.sys.ISysSiteDao;
import com.legocms.data.entities.sys.SysUser;

public class ModifySysSiteManageAction extends ModifyAction<SysUser> {

    private String siteCode;

    private ISysSiteDao siteDao = getDao(ISysSiteDao.class);

    public ModifySysSiteManageAction(String operator, String siteCode, String userCode) {
        super(SysPermissionCode.SITE, operator);
        this.siteCode = siteCode;
        setTargetEntity(userDao.findByCode(userCode));
    }

    @Override
    protected void doModify(SysUser entity) {
        entity.setSite(siteDao.findByCode(siteCode));
        userDao.save(entity);
    }

}
