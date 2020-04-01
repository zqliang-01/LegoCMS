package com.legocms.data.dao.sys;

import java.util.List;

import com.legocms.data.base.IGenericDao;
import com.legocms.data.entities.sys.SysPermission;

public interface ISysPermissionDao extends IGenericDao<SysPermission> {

    List<SysPermission> findBy(String parentCode, boolean menu);
}