package com.legocms.data.dao.sys.impl;

import javax.persistence.EntityManager;

import com.legocms.core.common.StringUtil;
import com.legocms.core.dto.Page;
import com.legocms.data.base.impl.GenericDao;
import com.legocms.data.dao.sys.ISysSiteDao;
import com.legocms.data.entities.sys.SysSite;
import com.legocms.data.handler.QueryHandler;

public class SysSiteDao extends GenericDao<SysSite> implements ISysSiteDao {

    public SysSiteDao(Class<SysSite> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public Page<SysSite> findBy(String code, String name, String organization, int pageIndex, int pageSize) {
        QueryHandler<SysSite> queryHandler = this.createQueryHandler("FROM {0} ss");
        if (StringUtil.isNotBlank(code)) {
            queryHandler.condition("ss.code = :code").setParameter("code", code);
        }
        if (StringUtil.isNotBlank(name)) {
            queryHandler.condition("ss.name like :name").setParameter("name", "%" + name + "%");
        }
        if (StringUtil.isNotBlank(organization)) {
            queryHandler.condition("ss.organization.code = :organization").setParameter("organization", organization);
        }
        return queryHandler.findPage(pageIndex, pageSize);
    }

}
