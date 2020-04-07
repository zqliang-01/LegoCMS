package com.legocms.data.dao.cms.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.legocms.data.base.impl.GenericDao;
import com.legocms.data.dao.cms.ICmsPlaceDao;
import com.legocms.data.entities.cms.CmsPlace;
import com.legocms.data.handler.QueryHandler;

public class CmsPlaceDao extends GenericDao<CmsPlace> implements ICmsPlaceDao {

    public CmsPlaceDao(Class<CmsPlace> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public List<CmsPlace> findChildren(String code) {
        QueryHandler<CmsPlace> query = createQueryHandler("FROM {0} t");
        query.condition("t.parent.code = :code").setParameter("code", code);
        return query.findList();
    }

}
