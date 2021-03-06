package com.legocms.data.dao.sys.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.legocms.core.common.StringUtil;
import com.legocms.core.dto.Page;
import com.legocms.core.vo.sys.QuerySysUserVo;
import com.legocms.data.base.impl.GenericDao;
import com.legocms.data.dao.sys.ISysUserDao;
import com.legocms.data.entities.sys.SysUser;
import com.legocms.data.handler.QueryHandler;

public class SysUserDao extends GenericDao<SysUser> implements ISysUserDao {

    public SysUserDao(Class<SysUser> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public Page<SysUser> findBy(QuerySysUserVo vo, int pageIndex, int pageSize) {
        QueryHandler<SysUser> queryHandler = this.createQueryHandler("FROM {0} u");
        if (StringUtil.isNotBlank(vo.getCode())) {
            queryHandler.condition("u.code = :code").setParameter("code", vo.getCode());
        }
        if (StringUtil.isNotBlank(vo.getName())) {
            queryHandler.condition("u.name like :name").setParameter("name", "%" + vo.getName() + "%");
        }
        if (StringUtil.isNotBlank(vo.getStatus())) {
            queryHandler.condition("u.status.code = :status").setParameter("status", vo.getStatus());
        }

        if (vo.getCreateStart() != null) {
            queryHandler.condition("u.createTime >= :createStart").setParameter("createStart", vo.getCreateStart());
        }

        if (vo.getCreateEnd() != null) {
            queryHandler.condition("u.createTime < :endStart").setParameter("endStart", vo.getCreateEnd());
        }
        return queryHandler.findPage(pageIndex, pageSize);
    }

    @Override
    public List<SysUser> findBy(String organizationCode) {
        QueryHandler<SysUser> query = createQueryHandler("FROM {0} u");
        query.condition("u.organization.code = :organizationCode").setParameter("organizationCode", organizationCode);
        return query.findList();
    }

    @Override
    public List<SysUser> findBySite(String siteCode) {
        QueryHandler<SysUser> query = createQueryHandler("FROM {0} u");
        query.condition("u.site.code = :siteCode").setParameter("siteCode", siteCode);
        return query.findList();
    }

}
