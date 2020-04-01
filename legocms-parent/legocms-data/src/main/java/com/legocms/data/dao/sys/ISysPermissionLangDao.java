package com.legocms.data.dao.sys;

import com.legocms.data.base.IGenericDao;
import com.legocms.data.entities.sys.SysPermission;
import com.legocms.data.entities.sys.SysPermissionLang;

public interface ISysPermissionLangDao extends IGenericDao<SysPermissionLang> {

    SysPermissionLang findBy(SysPermission module, String code);
}
