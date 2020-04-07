package com.legocms.data.dao.sys.impl;

import javax.persistence.EntityManager;

import com.legocms.core.common.StringUtil;
import com.legocms.core.dto.Page;
import com.legocms.data.base.impl.GenericDao;
import com.legocms.data.dao.sys.ISysDomainDao;
import com.legocms.data.entities.sys.SysDomain;
import com.legocms.data.handler.QueryHandler;

public class SysDomainDao extends GenericDao<SysDomain> implements ISysDomainDao {

    public SysDomainDao(Class<SysDomain> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public Page<SysDomain> findBy(String code, String name, String siteCode, int pageIndex, int pageSize) {
        QueryHandler<SysDomain> handler = createQueryHandler("FROM {0} d");
        if (StringUtil.isNotBlank(code)) {
            handler.condition("d.code = :code").setParameter("code", code);
        }
        if (StringUtil.isNotBlank(name)) {
            handler.condition("d.name = :name").setParameter("name", name);
        }
        handler.condition("d.site.code = :siteCode").setParameter("siteCode", siteCode);
        return handler.findPage(pageIndex, pageSize);
    }

    @Override
    public SysDomain findByName(String name) {
        QueryHandler<SysDomain> handler = createQueryHandler("FROM {0} d");
        handler.condition("d.name = :name").setParameter("name", name);
        return handler.findUnique();
    }
}