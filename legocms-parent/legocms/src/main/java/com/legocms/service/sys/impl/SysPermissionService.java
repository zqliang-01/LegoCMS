package com.legocms.service.sys.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legocms.core.dto.sys.SysPermissionInfo;
import com.legocms.data.assembler.sys.SysModuleAssembler;
import com.legocms.data.dao.sys.ISysPermissionDao;
import com.legocms.data.entities.sys.SysPermission;
import com.legocms.service.BaseService;
import com.legocms.service.sys.ISysPermissionService;

@Service
public class SysPermissionService extends BaseService implements ISysPermissionService {

    @Autowired
    private ISysPermissionDao moduleDao;

    @Autowired
    private SysModuleAssembler moduleAssembler;

    public List<SysPermissionInfo> findBy(String userCode, String parentCode, boolean menu) {
        List<SysPermission> modules = moduleDao.findBy(null, parentCode, menu);
        return this.moduleAssembler.create(modules);
    }
}
