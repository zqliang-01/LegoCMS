package com.legocms.data.act.sys;

import com.legocms.core.common.StringUtil;
import com.legocms.core.dto.TypeInfo;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.core.vo.sys.SysSiteVo;
import com.legocms.data.act.AddAction;
import com.legocms.data.dao.sys.ISysOrganizationDao;
import com.legocms.data.entities.sys.SysOrganization;
import com.legocms.data.entities.sys.SysSite;

public class AddSysSiteAction extends AddAction<SysSite> {

    private SysSiteVo vo;

    private SysOrganization organization;

    private ISysOrganizationDao organizationDao = getDao(ISysOrganizationDao.class);

    public AddSysSiteAction(String operator, SysSiteVo vo) {
        super(SysPermissionCode.SITE, operator);
        this.vo = vo;
    }

    @Override
    protected void preprocess() {
        BusinessException.check(StringUtil.isNotBlank(vo.getName()), "站点名称不能为空，站点新增失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getPath()), "站点地址不能为空，站点新增失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getDynamicPath()), "动态站点地址不能为空，站点新增失败！");

        TypeInfo organizationInfo = vo.getOrganization();
        BusinessException.check(organizationInfo != null, "部门不能为空！");

        SysSite site = siteDao.findByUnsureCode(vo.getCode());
        BusinessException.check(site == null, "已存在编号为【{0}】的站点信息，站点新增失败！", vo.getCode());

        organization = organizationDao.findByUnsureCode(vo.getOrganization().getCode());
        BusinessException.check(organization != null, "不存在存在编号为【{0}】的部门信息，站点新增失败！", vo.getOrganization().getCode());
    }

    @Override
    protected SysSite createTargetEntity() {
        SysSite site = new SysSite(vo.getCode());
        site.setName(vo.getName());
        site.setPath(vo.getPath());
        site.setDynamicPath(vo.getDynamicPath());
        site.setOrganization(organization);
        siteDao.save(site);
        return site;
    }

}
