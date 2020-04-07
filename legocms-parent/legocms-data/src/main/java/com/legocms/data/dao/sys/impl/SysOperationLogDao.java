package com.legocms.data.dao.sys.impl;

import java.util.Date;

import javax.persistence.EntityManager;

import com.legocms.core.common.StringUtil;
import com.legocms.core.dto.Page;
import com.legocms.data.base.impl.GenericDao;
import com.legocms.data.dao.sys.ISysOperationLogDao;
import com.legocms.data.entities.sys.SysOperationLog;
import com.legocms.data.handler.QueryHandler;

public class SysOperationLogDao extends GenericDao<SysOperationLog> implements ISysOperationLogDao {

    public SysOperationLogDao(Class<SysOperationLog> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public Page<SysOperationLog> findBy(String userName, Date start, Date end, int pageIndex, int pageSize) {
        QueryHandler<SysOperationLog> handler = createQueryHandler("FROM {0} ol");
        if (StringUtil.isNotBlank(userName)) {
            handler.condition("ol.user.name LIKE :userName").setParameter("userName", "%" + userName + "%");
        }
        if (start != null) {
            handler.condition("ol.createTime >= :createStart").setParameter("createStart", start);
        }
        if (end != null) {
            handler.condition("ol.createTime < :createEnd").setParameter("createEnd", end);
        }
        handler.order(" ol.createTime DESC");
        return handler.findPage(pageIndex, pageSize);
    }

}
