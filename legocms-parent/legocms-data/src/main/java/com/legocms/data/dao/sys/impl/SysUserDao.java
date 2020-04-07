package com.legocms.data.dao.sys.impl;

import javax.persistence.EntityManager;

import com.legocms.core.common.StringUtil;
import com.legocms.core.dto.Page;
import com.legocms.data.base.impl.GenericDao;
import com.legocms.data.dao.sys.ISysUserDao;
import com.legocms.data.entities.sys.SysUser;
import com.legocms.data.handler.QueryHandler;

public class SysUserDao extends GenericDao<SysUser> implements ISysUserDao {

    public SysUserDao(Class<SysUser> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public Page<SysUser> findBy(String code, String name, int pageIndex, int pageSize) {
        QueryHandler<SysUser> queryHandler = this.createQueryHandler("FROM {0} u");
        if (StringUtil.isNotBlank(code)) {
            queryHandler.condition("u.code = :code").setParameter("code", code);
        }
        if (StringUtil.isNotBlank(name)) {
            queryHandler.condition("u.name like :name").setParameter("name", "%" + name + "%");
        }
        return queryHandler.findPage(pageIndex, pageSize);
    }

}
