package com.legocms.data.dao.cms;

import com.legocms.core.dto.Page;
import com.legocms.data.base.IGenericDao;
import com.legocms.data.entities.cms.CmsFile;

public interface ICmsFileDao extends IGenericDao<CmsFile> {

    Page<CmsFile> findBy(CmsFile parent, String siteCode, int pageIndex, int pageSize);
}
