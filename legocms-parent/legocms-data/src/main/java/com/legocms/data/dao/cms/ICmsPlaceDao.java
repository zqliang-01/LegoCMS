package com.legocms.data.dao.cms;

import java.util.List;

import com.legocms.data.base.IGenericDao;
import com.legocms.data.entities.cms.CmsPlace;

public interface ICmsPlaceDao extends IGenericDao<CmsPlace> {

    List<CmsPlace> findChildren(String code);
}
