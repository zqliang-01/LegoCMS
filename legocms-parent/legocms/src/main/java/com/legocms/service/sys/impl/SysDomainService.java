package com.legocms.service.sys.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legocms.core.dto.Page;
import com.legocms.core.dto.sys.SysDomainInfo;
import com.legocms.core.vo.sys.SysDomainVo;
import com.legocms.data.act.sys.AddSysDomainAction;
import com.legocms.data.act.sys.DeleteSysDomainAction;
import com.legocms.data.act.sys.ModifySysDomainAction;
import com.legocms.data.assembler.sys.SysDomainAssembler;
import com.legocms.data.dao.sys.ISysDomainDao;
import com.legocms.data.dao.sys.ISysSiteDao;
import com.legocms.data.entities.sys.SysDomain;
import com.legocms.service.BaseService;
import com.legocms.service.sys.ISysDomainService;

@Service
public class SysDomainService extends BaseService implements ISysDomainService {

    @Autowired
    private ISysDomainDao domainDao;

    @Autowired
    private ISysSiteDao siteDao;

    @Autowired
    private SysDomainAssembler domainAssembler;

    @Override
    public SysDomainInfo findBy(String code) {
        SysDomain domain = domainDao.findByUnsureCode(code);
        if (domain == null) {
            return null;
        }
        return domainAssembler.create(domain);
    }

    @Override
    public SysDomainInfo findByName(String name) {
        SysDomain domain = domainDao.findByName(name);
        if (domain == null) {
            return null;
        }
        return domainAssembler.create(domain);
    }

    @Override
    public Page<SysDomainInfo> findBy(String code, String name, String siteCode, int pageIndex, int pageSize) {
        Page<SysDomain> page = domainDao.findBy(code, name, siteCode, pageIndex, pageSize);
        return domainAssembler.createInfoPage(page);
    }

    @Override
    public void add(String operator, SysDomainVo vo) {
        new AddSysDomainAction(operator, vo).run();
    }

    @Override
    public void modify(String operator, SysDomainVo vo) {
        new ModifySysDomainAction(operator, vo).run();
    }

    @Override
    public void delete(String operator, String code) {
        new DeleteSysDomainAction(operator, code).run();
    }
}
