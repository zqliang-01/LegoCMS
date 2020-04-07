package com.legocms.data.act.sys;

import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.sys.SysDomainVo;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.ModifyAction;
import com.legocms.data.dao.sys.ISysDomainDao;
import com.legocms.data.entities.sys.SysDomain;
import com.legocms.data.entities.sys.SysSite;

public class ModifySysDomainAction extends ModifyAction<SysDomain> {

    private SysDomainVo vo;

    private SysSite site;

    private ISysDomainDao domainDao = getDao(ISysDomainDao.class);

    public ModifySysDomainAction(String operator, SysDomainVo vo) {
        super(SysPermissionCode.DOMAIN, operator);
        this.vo = vo;
    }

    @Override
    protected void preprocess() {
        BusinessException.check(StringUtil.isNotBlank(vo.getCode()), "域名编码不能为空，域名修改失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getName()), "域名名称不能为空，域名修改失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getSiteCode()), "当前无管理站点，域名修改失败！");

        site = siteDao.findByCode(vo.getSiteCode());

        SysDomain domain = domainDao.findByUnsureCode(vo.getCode());
        BusinessException.check(domain != null, "不存在编码为[{0}]的域名信息，域名修改失败！", vo.getCode());
        setTargetEntity(domain);
    }

    @Override
    protected void doModify(SysDomain entity) {
        entity.setName(vo.getName());
        entity.setPath(vo.getPath());
        entity.setSite(site);
        domainDao.save(entity);
    }
}
