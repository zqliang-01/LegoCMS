package com.legocms.data.act.sys;

import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.core.vo.sys.SysUserVo;
import com.legocms.data.act.ModifyAction;
import com.legocms.data.dao.sys.ISysOrganizationDao;
import com.legocms.data.dao.sys.ISysRoleDao;
import com.legocms.data.dao.sys.ISysSiteDao;
import com.legocms.data.entities.sys.SysUser;
import com.legocms.data.entities.sys.simpletype.SysUserStatus;

public class ModifySysUserAction extends ModifyAction<SysUser> {

    private SysUserVo vo;

    private ISysSiteDao siteDao = getDao(ISysSiteDao.class);
    private ISysRoleDao roleDao = getDao(ISysRoleDao.class);
    private ISysOrganizationDao organizationDao = getDao(ISysOrganizationDao.class);

    public ModifySysUserAction(String operator, SysUserVo vo) {
        super(SysPermissionCode.USER, operator);
        this.vo = vo;
    }

    @Override
    protected void preprocess() {
        BusinessException.check(StringUtil.isNotBlank(vo.getCode()), "用户编号不能为空，用户信息修改失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getName()), "用户姓名不能为空，用户信息修改失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getStatus()), "用户状态不能为空，用户信息修改失败！");
        BusinessException.check(vo.getOrganization() != null, "用户部门不能为空，用户信息修改失败！");

        SysUser user = userDao.findByUnsureCode(vo.getCode());
        BusinessException.check(user != null, "不存在编号为{0}的用户信息，用户信息修改失败！", vo.getCode());
        setTargetEntity(user);
    }

    @Override
    protected void doModify(SysUser user) {
        user.setName(vo.getName());
        user.setOrganization(organizationDao.findByCode(vo.getOrganization().getCode()));
        user.setStatus(commonDao.findByCode(SysUserStatus.class, vo.getStatus()));
        user.setRoles(roleDao.findByCodes(vo.getRoles()));
        user.setSite(siteDao.findByCode(vo.getSiteCode()));
        userDao.save(user);
    }

}
