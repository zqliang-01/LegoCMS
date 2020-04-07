package com.legocms.data.dao.cms.impl;

import javax.persistence.EntityManager;

import com.legocms.core.dto.Page;
import com.legocms.data.base.impl.GenericDao;
import com.legocms.data.dao.cms.ICmsFileDao;
import com.legocms.data.entities.cms.CmsFile;
import com.legocms.data.handler.QueryHandler;

public class CmsFileDao extends GenericDao<CmsFile> implements ICmsFileDao {

    public CmsFileDao(Class<CmsFile> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public Page<CmsFile> findBy(String parentCode, String siteCode, int pageIndex, int pageSize) {
        QueryHandler<CmsFile> handler = this.createQueryHandler("FROM {0} f");
        handler.condition("f.parent.code = :parentCode").setParameter("parentCode", parentCode);
        handler.condition("f.site.code = :siteCode").setParameter("siteCode", siteCode);
        return handler.findPage(pageIndex, pageSize);
    }

}
