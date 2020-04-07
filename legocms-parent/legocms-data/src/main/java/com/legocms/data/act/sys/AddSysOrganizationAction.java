package com.legocms.data.act.sys;

import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.sys.SysOrganizationVo;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.AddAction;
import com.legocms.data.dao.sys.ISysOrganizationDao;
import com.legocms.data.entities.sys.SysOrganization;
import com.legocms.data.entities.sys.simpletype.SysOrganizationStatus;

public class AddSysOrganizationAction extends AddAction<SysOrganization> {

    private SysOrganization parent;
    private SysOrganizationStatus status;
    private SysOrganizationVo vo;

    private ISysOrganizationDao organizationDao = getDao(ISysOrganizationDao.class);

    public AddSysOrganizationAction(String operator, SysOrganizationVo vo) {
        super(SysPermissionCode.ORGANIZATION, operator);
        this.vo = vo;
    }

    @Override
    protected void preprocess() {
        BusinessException.check(StringUtil.isNotBlank(vo.getName()), "名称不能为空，创建部门失败！");
        BusinessException.check(vo.getStatus() != null, "状态不能为空，新建部门失败！");

        SysOrganization organization = organizationDao.findByUnsureCode(vo.getCode());
        BusinessException.check(organization == null, "已存在编号为【{0}】的部门信息，创建部门失败！", vo.getCode());

        status = commonDao.findByCode(SysOrganizationStatus.class, vo.getStatus().getCode());
        if (vo.getParent() != null) {
            parent = organizationDao.findByUnsureCode(vo.getParent().getCode());
        }
    }

    @Override
    protected SysOrganization createTargetEntity() {
        SysOrganization organization = new SysOrganization(vo.getCode());
        organization.setName(vo.getName());
        organization.setStatus(status);
        organization.setParent(parent);
        organizationDao.save(organization);
        return organization;
    }

}
