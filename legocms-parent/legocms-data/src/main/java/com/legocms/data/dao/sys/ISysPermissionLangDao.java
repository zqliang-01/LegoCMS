package com.legocms.data.dao.sys;

import java.util.List;

import com.legocms.data.base.IGenericDao;
import com.legocms.data.entities.sys.SysPermission;
import com.legocms.data.entities.sys.SysPermissionLang;

public interface ISysPermissionLangDao extends IGenericDao<SysPermissionLang> {

    SysPermissionLang findBy(SysPermission permission, String code);

    List<SysPermissionLang> findBy(SysPermission permission);
}
