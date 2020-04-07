package com.legocms.data.dao.cms.impl;

import javax.persistence.EntityManager;

import com.legocms.data.base.impl.GenericDao;
import com.legocms.data.dao.cms.ICmsModelDao;
import com.legocms.data.entities.cms.CmsModel;

public class CmsModelDao extends GenericDao<CmsModel> implements ICmsModelDao {

    public CmsModelDao(Class<CmsModel> domainClass, EntityManager em) {
        super(domainClass, em);
    }

}