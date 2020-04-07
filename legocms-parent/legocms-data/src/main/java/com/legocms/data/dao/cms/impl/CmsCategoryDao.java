package com.legocms.data.dao.cms.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.legocms.core.common.StringUtil;
import com.legocms.core.dto.Page;
import com.legocms.data.base.impl.GenericDao;
import com.legocms.data.dao.cms.ICmsCategoryDao;
import com.legocms.data.entities.cms.CmsCategory;
import com.legocms.data.handler.QueryHandler;

public class CmsCategoryDao extends GenericDao<CmsCategory> implements ICmsCategoryDao {

    public CmsCategoryDao(Class<CmsCategory> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public List<CmsCategory> findByStatus(String status) {
        QueryHandler<CmsCategory> handler = this.createQueryHandler("FROM {0} c");
        if (StringUtil.isNotBlank(status)) {
            handler.condition("c.status.code = :status").setParameter("status", status);
        }
        return handler.findList();
    }

    @Override
    public Page<CmsCategory> findBy(String code, String name, String status, String parentCode, int pageIndex, int pageSize) {
        QueryHandler<CmsCategory> handler = this.createQueryHandler("FROM {0} c");
        if (StringUtil.isNotBlank(code)) {
            handler.condition("c.code = :code").setParameter("code", code);
        }
        if (StringUtil.isNotBlank(name)) {
            handler.condition("c.name LIKE :name").setParameter("name", "%" + name + "%");
        }
        if (StringUtil.isNotBlank(status)) {
            handler.condition("c.status.code = :status").setParameter("status", status);
        }
        if (StringUtil.isNotBlank(parentCode)) {
            handler.condition("c.parent.code = :parentCode").setParameter("parentCode", parentCode);
        }
        return handler.findPage(pageIndex, pageSize);
    }

}
