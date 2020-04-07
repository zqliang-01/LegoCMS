package com.legocms.data.dao.cms;

import java.util.List;

import com.legocms.core.dto.Page;
import com.legocms.data.base.IGenericDao;
import com.legocms.data.entities.cms.CmsFile;

public interface ICmsFileDao extends IGenericDao<CmsFile> {

    Page<CmsFile> findBy(CmsFile parent, String siteCode, int pageIndex, int pageSize);

    CmsFile findBy(String parentCode, String siteCode, String path);

    CmsFile findByPath(String path, String siteCode);

    List<CmsFile> findBy(String parentCode, String siteCode);
}
