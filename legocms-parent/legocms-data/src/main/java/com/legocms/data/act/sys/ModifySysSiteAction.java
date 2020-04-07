package com.legocms.data.act.sys;

import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.core.vo.sys.SysSiteVo;
import com.legocms.data.act.ModifyAction;
import com.legocms.data.dao.sys.ISysOrganizationDao;
import com.legocms.data.dao.sys.ISysSiteDao;
import com.legocms.data.entities.sys.SysOrganization;
import com.legocms.data.entities.sys.SysSite;

public class ModifySysSiteAction extends ModifyAction<SysSite> {

    private SysSiteVo vo;
    private SysOrganization organization;

    private ISysSiteDao siteDao = getDao(ISysSiteDao.class);
    private ISysOrganizationDao organizationDao = getDao(ISysOrganizationDao.class);

    public ModifySysSiteAction(String operator, SysSiteVo vo) {
        super(SysPermissionCode.SITE, operator);
        this.vo = vo;
    }

    @Override
    protected void preprocess() {
        BusinessException.check(StringUtil.isNotBlank(vo.getCode()), "站点编码不能为空，修改站点信息失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getName()), "站点名称不能为空，修改站点信息失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getPath()), "站点地址不能为空，修改站点信息失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getDynamicPath()), "动态站点地址不能为空，修改站点信息失败！");

        organization = organizationDao.findByUnsureCode(vo.getOrganization().getCode());
        BusinessException.check(organization != null, "不存在存在编号为【{0}】的部门信息，站点新增失败！", vo.getOrganization().getCode());

        SysSite site = siteDao.findByUnsureCode(vo.getCode());
        BusinessException.check(site != null, "不存在存在编号为【{0}】的站点信息，站点新增失败！", vo.getCode());
        setTargetEntity(site);
    }

    @Override
    protected void doModify(SysSite site) {
        site.setName(vo.getName());
        site.setPath(vo.getPath());
        site.setDynamicPath(vo.getDynamicPath());
        site.setOrganization(organization);
        siteDao.save(site);
    }

}