package com.legocms.data.dao.cms.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.legocms.data.base.impl.GenericDao;
import com.legocms.data.dao.cms.ICmsTemplateDao;
import com.legocms.data.entities.cms.CmsTemplate;
import com.legocms.data.handler.QueryHandler;

public class CmsTemplateDao extends GenericDao<CmsTemplate> implements ICmsTemplateDao {

    public CmsTemplateDao(Class<CmsTemplate> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public List<CmsTemplate> findChildren(String code) {
        QueryHandler<CmsTemplate> query = createQueryHandler("FROM {0} t");
        query.condition("t.parent.code = :code").setParameter("code", code);
        return query.findList();
    }

}