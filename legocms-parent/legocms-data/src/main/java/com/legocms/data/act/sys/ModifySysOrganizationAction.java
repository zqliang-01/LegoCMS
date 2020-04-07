package com.legocms.data.act.sys;

import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.sys.SysOrganizationVo;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.ModifyAction;
import com.legocms.data.dao.sys.ISysOrganizationDao;
import com.legocms.data.entities.sys.SysOrganization;
import com.legocms.data.entities.sys.simpletype.SysOrganizationStatus;

public class ModifySysOrganizationAction extends ModifyAction<SysOrganization> {

    private SysOrganization parent;
    private SysOrganizationStatus status;
    private SysOrganizationVo vo;

    private ISysOrganizationDao organizationDao = getDao(ISysOrganizationDao.class);

    public ModifySysOrganizationAction(String operator, SysOrganizationVo vo) {
        super(SysPermissionCode.ORGANIZATION, operator);
        this.vo = vo;
    }

    @Override
    protected void preprocess() {
        SysOrganization organization = organizationDao.findByUnsureCode(vo.getCode());
        BusinessException.check(organization != null, "不存在编号为[{0}]的部门信息，部门修改失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getName()), "名称不能为空，部门修改失败！");
        BusinessException.check(vo.getStatus() != null, "状态不能为空，部门修改失败！");

        status = commonDao.findByCode(SysOrganizationStatus.class, vo.getStatus().getCode());
        if (vo.getParent() != null) {
            parent = organizationDao.findByUnsureCode(vo.getParent().getCode());
        }
        setTargetEntity(organization);
    }

    @Override
    protected void doModify(SysOrganization entity) {
        entity.setName(vo.getName());
        entity.setStatus(status);
        entity.setParent(parent);
        organizationDao.save(entity);
    }

}
