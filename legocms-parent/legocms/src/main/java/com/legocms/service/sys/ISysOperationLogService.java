package com.legocms.service.sys;

import java.util.Date;

import com.legocms.core.dto.Page;
import com.legocms.core.dto.sys.SysOperationLogInfo;

public interface ISysOperationLogService {

    Page<SysOperationLogInfo> findBy(String userName, String lane, Date start, Date end, int pageIndex, int pageSize);
}
