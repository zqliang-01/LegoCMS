package com.legocms.data.dao.sys.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.legocms.core.common.StringUtil;
import com.legocms.data.base.impl.GenericDao;
import com.legocms.data.dao.sys.ISysPermissionDao;
import com.legocms.data.entities.sys.SysPermission;
import com.legocms.data.handler.QueryHandler;

public class SysPermissionDao extends GenericDao<SysPermission> implements ISysPermissionDao {

    public SysPermissionDao(Class<SysPermission> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    public List<SysPermission> findBy(String userCode, String parentCode, boolean menu) {
        QueryHandler<SysPermission> query = createQueryHandler("FROM {0} m");
        if (StringUtil.isNoneBlank(new CharSequence[] { parentCode })) {
            query.condition("m.parent.code = :parentCode").setParameter("parentCode", parentCode);
        }
        else {
            query.condition("m.parent = null ");
        }
        if (menu) {
            query.condition("m.menu = :menu").setParameter("menu", Boolean.valueOf(menu));
        }
        return query.findList();
    }
}
