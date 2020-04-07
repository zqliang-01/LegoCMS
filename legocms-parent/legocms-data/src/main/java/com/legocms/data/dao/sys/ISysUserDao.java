package com.legocms.data.dao.sys;

import java.util.List;

import com.legocms.core.dto.Page;
import com.legocms.core.vo.sys.QuerySysUserVo;
import com.legocms.data.base.IGenericDao;
import com.legocms.data.entities.sys.SysUser;

public interface ISysUserDao extends IGenericDao<SysUser> {

    Page<SysUser> findBy(QuerySysUserVo vo, int pageIndex, int pageSize);

    List<SysUser> findBy(String organizationCode);

    List<SysUser> findBySite(String siteCode);

}
