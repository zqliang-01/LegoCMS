package com.legocms.service.sys.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.sys.SysOrganizationDetailInfo;
import com.legocms.core.vo.sys.SysOrganizationVo;
import com.legocms.data.act.sys.AddSysOrganizationAction;
import com.legocms.data.act.sys.DeleteSysOrganizationAction;
import com.legocms.data.act.sys.ModifySysOrganizationAction;
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

    @Override
    public SysOrganizationDetailInfo findDetailBy(String code) {
        SysOrganization organization = organizationDao.findByCode(code);
        return organizationAssembler.createDetail(organization);
    }

    @Override
    public void add(String operator, SysOrganizationVo vo) {
        new AddSysOrganizationAction(operator, vo).run();
    }

    @Override
    public void modify(String operator, SysOrganizationVo vo) {
        new ModifySysOrganizationAction(operator, vo).run();
    }

    @Override
    public void delete(String operator, String code) {
        new DeleteSysOrganizationAction(operator, code).run();
    }
}
