package com.legocms.data.dao.sys;

import com.legocms.core.dto.Page;
import com.legocms.core.vo.sys.QuerySysUserVo;
import com.legocms.data.base.IGenericDao;
import com.legocms.data.entities.sys.SysUser;

public interface ISysUserDao extends IGenericDao<SysUser> {

    Page<SysUser> findBy(QuerySysUserVo vo, int pageIndex, int pageSize);
}
