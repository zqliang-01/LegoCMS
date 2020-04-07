package com.legocms.data.dao.cms.impl;

import javax.persistence.EntityManager;

import com.legocms.data.base.impl.GenericDao;
import com.legocms.data.dao.cms.ICmsModelAttributeDao;
import com.legocms.data.entities.cms.CmsModelAttribute;

public class CmsModelAttributeDao extends GenericDao<CmsModelAttribute> implements ICmsModelAttributeDao {

    public CmsModelAttributeDao(Class<CmsModelAttribute> domainClass, EntityManager em) {
        super(domainClass, em);
    }

}
