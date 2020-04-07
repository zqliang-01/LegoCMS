package com.legocms.data.dao.sys;

import java.util.Date;

import com.legocms.core.dto.Page;
import com.legocms.data.base.IGenericDao;
import com.legocms.data.entities.sys.SysOperationLog;

public interface ISysOperationLogDao extends IGenericDao<SysOperationLog> {

    Page<SysOperationLog> findBy(String userName, Date start, Date end, int pageIndex, int pageSize);
}
