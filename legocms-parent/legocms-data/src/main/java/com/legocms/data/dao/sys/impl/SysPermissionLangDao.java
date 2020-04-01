package com.legocms.data.dao.sys.impl;

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
    public SysPermissionLang findBy(SysPermission module, String code) {
        QueryHandler<SysPermissionLang> query = createQueryHandler("FROM {0} ml");
        query.condition("ml.module = :module").setParameter("module", module);
        query.condition("ml.code = :code").setParameter("code", code);
        return query.findUnique();
    }
}
