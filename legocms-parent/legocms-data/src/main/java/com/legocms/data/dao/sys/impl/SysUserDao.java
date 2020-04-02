package com.legocms.data.dao.sys.impl;

import javax.persistence.EntityManager;

import com.legocms.data.base.impl.GenericDao;
import com.legocms.data.dao.sys.ISysUserDao;
import com.legocms.data.entities.sys.SysUser;

public class SysUserDao extends GenericDao<SysUser> implements ISysUserDao {

    public SysUserDao(Class<SysUser> domainClass, EntityManager em) {
        super(domainClass, em);
    }

}
