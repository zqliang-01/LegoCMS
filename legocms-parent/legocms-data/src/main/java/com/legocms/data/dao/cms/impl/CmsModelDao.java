package com.legocms.data.dao.cms.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.legocms.core.common.StringUtil;
import com.legocms.data.base.impl.GenericDao;
import com.legocms.data.dao.cms.ICmsModelDao;
import com.legocms.data.entities.cms.CmsModel;
import com.legocms.data.handler.QueryHandler;

public class CmsModelDao extends GenericDao<CmsModel> implements ICmsModelDao {

    public CmsModelDao(Class<CmsModel> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public List<CmsModel> findByParent(String parentCode) {
        QueryHandler<CmsModel> handler = this.createQueryHandler("FROM {0} m");
        if (StringUtil.isNotBlank(parentCode)) {
            handler.condition("m.parent.code = :parentCode");
            handler.setParameter("parentCode", parentCode);
        }
        else {
            handler.condition("m.parent IS NULL");
        }
        return handler.findList();
    }

}