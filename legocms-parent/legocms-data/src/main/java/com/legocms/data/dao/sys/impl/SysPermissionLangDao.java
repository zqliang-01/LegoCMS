package com.legocms.data.dao.sys.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.legocms.data.base.impl.GenericDao;
import com.legocms.data.dao.sys.ISysPermissionLangDao;
import com.legocms.data.entities.sys.SysPermission;
import com.legocms.data.entities.sys.SysPermissionLang;
import com.legocms.data.handler.QueryHandler;

public class SysPermissionLangDao extends GenericDao<SysPermissionLang> implements ISysPermissionLangDao {

    public SysPermissionLangDao(Class<SysPermissionLang> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public SysPermissionLang findBy(SysPermission permission, String code) {
        QueryHandler<SysPermissionLang> query = createQueryHandler("FROM {0} ml");
        query.condition("ml.permission = :permission").setParameter("permission", permission);
        query.condition("ml.code = :code").setParameter("code", code);
        return query.findUnique();
    }

    @Override
    public List<SysPermissionLang> findBy(SysPermission permission) {
        QueryHandler<SysPermissionLang> query = createQueryHandler("FROM {0} ml");
        query.condition("ml.permission = :permission").setParameter("permission", permission);
        return query.findList();
    }
}
