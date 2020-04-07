package com.legocms.data.dao.sys.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.legocms.core.common.StringUtil;
import com.legocms.core.dto.Page;
import com.legocms.data.base.impl.GenericDao;
import com.legocms.data.dao.sys.ISysRoleDao;
import com.legocms.data.entities.sys.SysRole;
import com.legocms.data.handler.QueryHandler;

public class SysRoleDao extends GenericDao<SysRole> implements ISysRoleDao {

    public SysRoleDao(Class<SysRole> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public List<SysRole> findBy(String userCode) {
        String sql =
            " SELECT r.* FROM sys_role r " +
            " JOIN sys_user_role ur ON ur.role_id = r.id " +
            " JOIN sys_user u ON u.id = ur.user_id " +
            " WHERE u.code = :userCode ";
        QueryHandler<SysRole> query = createQueryHandler(sql);
        query.setParameter("userCode", userCode);
        return query.findSqlList();
    }

    @Override
    public Page<SysRole> findBy(String code, String name, int pageIndex, int pageSize) {
        QueryHandler<SysRole> query = createQueryHandler("FROM {0} r");
        if (StringUtil.isNotBlank(code)) {
            query.condition("r.code = :code").setParameter("code", code);
        }
        if (StringUtil.isNotBlank(name)) {
            query.condition("r.name = :name").setParameter("name", name);
        }
        query.order("r.createDate");
        return query.findPage(pageIndex, pageSize);
    }
}
