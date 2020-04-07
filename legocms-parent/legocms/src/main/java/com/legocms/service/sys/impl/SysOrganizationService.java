package com.legocms.service.sys.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.sys.SysOrganizationDetailInfo;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.sys.SysOrganizationVo;
import com.legocms.data.assembler.sys.SysOrganizationAssembler;
import com.legocms.data.dao.sys.ISysOrganizationDao;
import com.legocms.data.dao.sys.ISysUserDao;
import com.legocms.data.entities.sys.SysOrganization;
import com.legocms.data.entities.sys.simpletype.OrganizationStatus;
import com.legocms.service.BaseService;
import com.legocms.service.sys.ISysOrganizationService;

@Service
public class SysOrganizationService extends BaseService implements ISysOrganizationService {

    @Autowired
    private ISysOrganizationDao organizationDao;

    @Autowired
    private ISysUserDao userDao;

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
    public void save(SysOrganizationVo vo) {
        SysOrganization organization = organizationDao.findByUnsureCode(vo.getCode());
        if (organization == null) {
            organization = new SysOrganization(vo.getCode());
        }
        organization.setName(vo.getName());
        organization.setStatus(commonDao.findByCode(OrganizationStatus.class, vo.getStatus().getCode()));
        organization.setParent(organizationDao.findByUnsureCode(vo.getParent().getCode()));
        organizationDao.save(organization);
    }

    @Override
    public void delete(String code) {
        BusinessException.check(userDao.findBy(code).isEmpty(), "该部门存在员工，删除失败！");
        BusinessException.check(organizationDao.findChildren(code).isEmpty(), "存在下级组织，删除失败！");
        organizationDao.delete(organizationDao.findByCode(code));
    }

}
