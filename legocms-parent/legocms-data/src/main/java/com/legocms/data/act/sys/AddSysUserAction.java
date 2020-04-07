package com.legocms.data.act.sys;

import com.legocms.core.common.Constants;
import com.legocms.core.common.DateUtil;
import com.legocms.core.common.StringUtil;
import com.legocms.core.dto.TypeInfo;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.core.vo.sys.SysUserVo;
import com.legocms.data.act.AddAction;
import com.legocms.data.dao.sys.ISysOrganizationDao;
import com.legocms.data.dao.sys.ISysRoleDao;
import com.legocms.data.entities.sys.SysOrganization;
import com.legocms.data.entities.sys.SysUser;
import com.legocms.data.entities.sys.simpletype.SysUserStatus;

public class AddSysUserAction extends AddAction<SysUser> {

    private SysUserVo vo;

    private SysUserStatus status;
    private SysOrganization organization;

    private ISysRoleDao roleDao = getDao(ISysRoleDao.class);
    private ISysOrganizationDao organizationDao = getDao(ISysOrganizationDao.class);

    public AddSysUserAction(String operator, SysUserVo vo) {
        super(SysPermissionCode.USER, operator);
        this.vo = vo;
    }

    @Override
    protected void preprocess() {
        TypeInfo organizationInfo = vo.getOrganization();
        SysUser user = userDao.findByUnsureCode(vo.getCode());

        status = commonDao.findByUnsureCode(SysUserStatus.class, vo.getStatus());
        BusinessException.check(organizationInfo != null, "部门不能为空！");
        BusinessException.check(user == null, "已存在编号为【{0}】的用户信息，用户新增失败！", vo.getCode());
        BusinessException.check(status != null, "不存在编号为【{0}】的转态信息，用户新增失败！", vo.getStatus());

        organization = organizationDao.findByUnsureCode(organizationInfo.getCode());
        BusinessException.check(organization != null, "不存在编号为【{0}】的部门信息，用户新增失败！", organizationInfo.getCode());
    }

    @Override
    protected SysUser createTargetEntity() {
        SysUser user = new SysUser(vo.getCode());
        user.setPassword(StringUtil.getMD5(Constants.DEFAULT_USER_PASSWORD));
        user.setName(vo.getName());
        user.setCreateTime(DateUtil.getCurrentDate());
        user.setOrganization(organizationDao.findByCode(vo.getOrganization().getCode()));
        user.setStatus(commonDao.findByCode(SysUserStatus.class, vo.getStatus()));
        user.setRoles(roleDao.findByCodes(vo.getRoles()));
        user.setSite(siteDao.findByCode(vo.getSiteCode()));
        userDao.save(user);
        return user;
    }

}
