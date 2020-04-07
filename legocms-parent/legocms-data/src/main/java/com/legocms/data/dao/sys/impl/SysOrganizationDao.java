package com.legocms.data.dao.sys.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.legocms.data.base.impl.GenericDao;
import com.legocms.data.dao.sys.ISysOrganizationDao;
import com.legocms.data.entities.sys.SysOrganization;
import com.legocms.data.handler.QueryHandler;

public class SysOrganizationDao extends GenericDao<SysOrganization> implements ISysOrganizationDao {

    public SysOrganizationDao(Class<SysOrganization> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public List<SysOrganization> findChildren(String parentCode) {
        QueryHandler<SysOrganization> query = this.createQueryHandler("FROM {0} o");
        query.condition("o.parent.code = :parentCode").setParameter("parentCode", parentCode);
        return query.findList();
    }

}