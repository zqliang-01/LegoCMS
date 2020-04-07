package com.legocms.data.dao.sys;

import com.legocms.core.dto.Page;
import com.legocms.data.base.IGenericDao;
import com.legocms.data.entities.sys.SysDomain;

public interface ISysDomainDao extends IGenericDao<SysDomain> {

    SysDomain findByName(String name);

    Page<SysDomain> findBy(String code, String name, String siteCode, int pageIndex, int pageSize);
}
