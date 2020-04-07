package com.legocms.data.dao.sys;

import java.util.List;

import com.legocms.core.dto.Page;
import com.legocms.data.base.IGenericDao;
import com.legocms.data.entities.sys.SysPermission;
import com.legocms.data.entities.sys.SysRole;

public interface ISysRoleDao extends IGenericDao<SysRole> {

    List<SysRole> findByUser(String userCode);

    List<SysRole> findByPermission(SysPermission permission);

    Page<SysRole> findBy(String code, String name, int pageIndex, int pageSize);
}
