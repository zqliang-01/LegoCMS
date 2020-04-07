package com.legocms.service.sys.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legocms.core.dto.Page;
import com.legocms.core.dto.sys.SysSiteInfo;
import com.legocms.core.vo.sys.SysSiteVo;
import com.legocms.data.assembler.sys.SysSiteAssembler;
import com.legocms.data.dao.sys.ISysOrganizationDao;
import com.legocms.data.dao.sys.ISysSiteDao;
import com.legocms.data.dao.sys.ISysUserDao;
import com.legocms.data.entities.sys.SysOrganization;
import com.legocms.data.entities.sys.SysSite;
import com.legocms.data.entities.sys.SysUser;
import com.legocms.service.BaseService;
import com.legocms.service.sys.ISysSiteService;

@Service
public class SysSiteService extends BaseService implements ISysSiteService {

    @Autowired
    private ISysSiteDao siteDao;

    @Autowired
    private ISysUserDao userDao;

    @Autowired
    private ISysOrganizationDao organizationDao;

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
    public void save(SysSiteVo vo) {
        SysSite site = siteDao.findByUnsureCode(vo.getCode());
        if (site == null) {
            site = new SysSite(vo.getCode());
        }
        site.setName(vo.getName());
        site.setPath(vo.getPath());
        site.setDynamicPath(vo.getDynamicPath());
        SysOrganization organization = organizationDao.findByCode(vo.getOrganization().getCode());
        site.setOrganization(organization);
        siteDao.save(site);
    }

    @Override
    public void manage(String userCode, String code) {
        SysUser user = userDao.findByCode(userCode);
        SysSite site = siteDao.findByCode(code);
        user.setSite(site);
        userDao.save(user);
    }

    @Override
    public void delete(String userCode, String code) {
        SysSite site = siteDao.findByCode(code);
        SysUser user = userDao.findByCode(userCode);
        if (site.equals(user.getSite())) {
            user.setSite(null);
        }
        userDao.save(user);
        siteDao.delete(site);
    }
}
