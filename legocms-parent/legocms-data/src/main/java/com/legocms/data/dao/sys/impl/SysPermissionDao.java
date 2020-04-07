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
        String sql =
                " SELECT p.* FROM sys_permission p " +
                " LEFT JOIN sys_permission pp ON pp.ID = p.PARENT_ID " +
                " JOIN sys_role_permission rp ON rp.PERMISSION_ID = p.id " +
                " JOIN sys_user_role ur ON ur.ROLE_ID = rp.ROLE_ID " +
                " JOIN sys_user u ON u.ID = ur.USER_ID ";
        QueryHandler<SysPermission> query = createQueryHandler(sql, SysPermission.class);
        if (StringUtil.isNoneBlank(parentCode)) {
            query.condition("pp.code = :parentCode").setParameter("parentCode", parentCode);
        }
        else {
            query.condition("p.parent_id IS NULL ");
        }
        query.condition("u.code = :userCode").setParameter("userCode", userCode);
        if (menu) {
            query.condition("p.menu = :menu").setParameter("menu", menu);
        }
        return query.findSqlList();
    }

    @Override
    public List<SysPermission> findAll() {
        QueryHandler<SysPermission> query = createQueryHandler("FROM {0} p", SysPermission.class);
        query.order("p.sort");
        return query.findList();
    }
}
