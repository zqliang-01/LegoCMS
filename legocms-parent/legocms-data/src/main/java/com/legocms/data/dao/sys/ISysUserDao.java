package com.legocms.data.dao.sys;

import com.legocms.core.dto.Page;
import com.legocms.data.base.IGenericDao;
import com.legocms.data.entities.sys.SysUser;

public interface ISysUserDao extends IGenericDao<SysUser> {

    Page<SysUser> findBy(String code, String name, int pageIndex, int pageSize);
}
