package com.legocms.data.act.sys;

import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.sys.SysDomainVo;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.AddAction;
import com.legocms.data.dao.sys.ISysDomainDao;
import com.legocms.data.entities.sys.SysDomain;
import com.legocms.data.entities.sys.SysSite;

public class AddSysDomainAction extends AddAction<SysDomain> {

    private SysDomainVo vo;

    private SysSite site;

    private ISysDomainDao domainDao = getDao(ISysDomainDao.class);

    public AddSysDomainAction(String operator, SysDomainVo vo) {
        super(SysPermissionCode.DOMAIN, operator);
        this.vo = vo;
    }

    @Override
    protected void preprocess() {
        BusinessException.check(StringUtil.isNotBlank(vo.getCode()), "域名编码不能为空，域名创建失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getName()), "域名名称不能为空，域名创建失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getSiteCode()), "当前无管理站点，域名创建失败！");

        site = siteDao.findByCode(vo.getSiteCode());
    }

    @Override
    protected SysDomain createTargetEntity() {
        SysDomain domain = new SysDomain(vo.getCode());
        domain.setName(vo.getName());
        domain.setPath(vo.getPath());
        domain.setSite(site);
        domainDao.save(domain);
        return domain;
    }

}
