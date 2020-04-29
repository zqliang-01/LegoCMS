package com.legocms.service.sys.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.data.assembler.sys.SysOrganizationAssembler;
import com.legocms.data.dao.sys.ISysOrganizationDao;
import com.legocms.data.entities.sys.SysOrganization;
import com.legocms.service.BaseService;
import com.legocms.service.sys.ISysOrganizationService;

@Service
public class SysOrganizationService extends BaseService implements ISysOrganizationService {

    @Autowired
    private ISysOrganizationDao organizationDao;

    @Autowired
    private SysOrganizationAssembler organizationAssembler;

    @Override
    public List<SimpleTreeInfo> findSimpleTree() {
        List<SysOrganization> organizations = organizationDao.findAll();
        return organizationAssembler.createSimpleTree(organizations);
    }

}
