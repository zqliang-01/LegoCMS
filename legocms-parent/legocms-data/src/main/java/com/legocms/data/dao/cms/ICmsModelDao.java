package com.legocms.data.dao.cms;

import java.util.List;

import com.legocms.data.base.IGenericDao;
import com.legocms.data.entities.cms.CmsModel;

public interface ICmsModelDao extends IGenericDao<CmsModel> {

    List<CmsModel> findByParent(String parentCode);
}
