package com.legocms.service.sys.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legocms.core.dto.Page;
import com.legocms.core.dto.sys.SysSiteInfo;
import com.legocms.core.vo.sys.SysSiteVo;
import com.legocms.data.act.sys.AddSysSiteAction;
import com.legocms.data.act.sys.DeleteSysSiteAction;
import com.legocms.data.act.sys.ModifySysSiteAction;
import com.legocms.data.act.sys.ModifySysSiteManageAction;
import com.legocms.data.assembler.sys.SysSiteAssembler;
import com.legocms.data.dao.sys.ISysSiteDao;
import com.legocms.data.entities.sys.SysSite;
import com.legocms.service.BaseService;
import com.legocms.service.sys.ISysSiteService;

@Service
public class SysSiteService extends BaseService implements ISysSiteService {

    @Autowired
    private ISysSiteDao siteDao;

    @Autowired
    private SysSiteAssembler siteAssembler;

    @Override
    public SysSiteInfo findBy(String code) {
        SysSite site = siteDao.findByUnsureCode(code);
        if (site == null) {
            return null;
        }
        return siteAssembler.create(site);
    }

    @Override
    public Page<SysSiteInfo> findBy(String code, String name, String organization, String manageSite, int pageIndex, int pageSize) {
        Page<SysSite> page = siteDao.findBy(code, name, organization, pageIndex, pageSize);
        return siteAssembler.createInfoPage(page, manageSite);
    }

    @Override
    public void add(String operator, SysSiteVo vo) {
        new AddSysSiteAction(operator, vo).run();
    }

    @Override
    public void modify(String operator, SysSiteVo vo) {
        new ModifySysSiteAction(operator, vo).run();
    }

    @Override
    public void manage(String operator, String userCode, String code) {
        new ModifySysSiteManageAction(operator, code, userCode).run();
    }

    @Override
    public void delete(String operator, String code) {
        new DeleteSysSiteAction(operator, code).run();
    }
}
