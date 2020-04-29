package com.legocms.data.dao.sys.impl;

import javax.persistence.EntityManager;

import com.legocms.data.base.impl.GenericDao;
import com.legocms.data.dao.sys.ISysOrganizationDao;
import com.legocms.data.entities.sys.SysOrganization;

public class SysOrganizationDao extends GenericDao<SysOrganization> implements ISysOrganizationDao {

    public SysOrganizationDao(Class<SysOrganization> domainClass, EntityManager em) {
        super(domainClass, em);
    }

}