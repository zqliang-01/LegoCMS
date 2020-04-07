package com.legocms.data.dao.sys;

import java.util.List;

import com.legocms.data.base.IGenericDao;
import com.legocms.data.entities.sys.SysOrganization;

public interface ISysOrganizationDao extends IGenericDao<SysOrganization> {

    List<SysOrganization> findChildren(String parentCode);
}
