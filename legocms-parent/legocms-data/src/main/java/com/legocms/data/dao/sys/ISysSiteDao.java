package com.legocms.data.dao.sys;

import com.legocms.core.dto.Page;
import com.legocms.data.base.IGenericDao;
import com.legocms.data.entities.sys.SysSite;

public interface ISysSiteDao extends IGenericDao<SysSite> {

    Page<SysSite> findBy(String code, String name, String organization, int pageIndex, int pageSize);
}
