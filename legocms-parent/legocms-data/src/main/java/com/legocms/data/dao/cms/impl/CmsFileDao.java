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
    public Page<CmsFile> findBy(CmsFile parent, String siteCode, int pageIndex, int pageSize) {
        QueryHandler<CmsFile> handler = this.createQueryHandler("FROM {0} f");
        if (parent == null) {
            handler.condition("f.parent IS NULL");
        }
        else {
            handler.condition("f.parent = :parent").setParameter("parent", parent);
        }
        handler.condition("f.site.code = :siteCode").setParameter("siteCode", siteCode);
        handler.order("f.type.code, f.name DESC");
        return handler.findPage(pageIndex, pageSize);
    }

}
