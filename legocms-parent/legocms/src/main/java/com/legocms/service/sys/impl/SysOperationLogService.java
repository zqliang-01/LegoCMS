package com.legocms.service.sys.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legocms.core.dto.Page;
import com.legocms.core.dto.sys.SysOperationLogInfo;
import com.legocms.data.assembler.sys.SysOperationLogAssembler;
import com.legocms.data.dao.sys.ISysOperationLogDao;
import com.legocms.data.entities.sys.SysOperationLog;
import com.legocms.service.BaseService;
import com.legocms.service.sys.ISysOperationLogService;

@Service
public class SysOperationLogService extends BaseService implements ISysOperationLogService {

    @Autowired
    private ISysOperationLogDao operationLogDao;

    @Autowired
    private SysOperationLogAssembler operationLogAssembler;

    @Override
    public Page<SysOperationLogInfo> findBy(String userName, String lane, Date start, Date end, int pageIndex, int pageSize) {
        Page<SysOperationLog> page = operationLogDao.findBy(userName, start, end, pageIndex, pageSize);
        return operationLogAssembler.createInfoPage(page, lane);
    }
}