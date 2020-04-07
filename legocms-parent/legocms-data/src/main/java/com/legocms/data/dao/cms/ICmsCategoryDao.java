package com.legocms.data.dao.cms;

import java.util.List;

import com.legocms.core.dto.Page;
import com.legocms.data.base.IGenericDao;
import com.legocms.data.entities.cms.CmsCategory;

public interface ICmsCategoryDao extends IGenericDao<CmsCategory> {

    List<CmsCategory> findByStatus(String status);

    Page<CmsCategory> findBy(String code, String name, String status, String parentCode, int pageIndex, int pageSize);
}
