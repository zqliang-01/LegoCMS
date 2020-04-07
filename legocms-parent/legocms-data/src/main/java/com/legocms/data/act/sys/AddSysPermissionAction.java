package com.legocms.data.act.sys;

import com.legocms.core.common.StringUtil;
import com.legocms.core.dto.TypeInfo;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.core.vo.sys.SysPermissionVo;
import com.legocms.data.act.AddAction;
import com.legocms.data.dao.sys.ISysPermissionLangDao;
import com.legocms.data.entities.sys.SysPermission;
import com.legocms.data.entities.sys.SysPermissionLang;

public class AddSysPermissionAction extends AddAction<SysPermission> {

    private SysPermission parent;
    private SysPermissionVo vo;

    private ISysPermissionLangDao permissionLangDao = getDao(ISysPermissionLangDao.class);

    public AddSysPermissionAction(String operator, SysPermissionVo vo) {
        super(SysPermissionCode.PERMISSION, operator);
        this.vo = vo;
    }

    @Override
    protected void preprocess() {
        BusinessException.check(vo.getParent() != null, "请选择上级权限");
        for (TypeInfo lang : vo.getLang()) {
            BusinessException.check(StringUtil.isNotBlank(lang.getCode()), "lang code不能为空");
        }
        SysPermission permission = permissionDao.findByUnsureCode(vo.getCode());
        BusinessException.check(permission == null, "已存在编号为【{0}】的权限信息，创建权限失败！", vo.getCode());

        SysPermission parent = permissionDao.findByUnsureCode(vo.getParent().getCode());
        if (parent != null && !parent.getCode().equals(vo.getCode())) {
            this.parent = parent;
        }
    }

    @Override
    protected SysPermission createTargetEntity() {
        SysPermission permission = new SysPermission(vo.getCode());
        permission.setIcon(vo.getIcon());
        permission.setMenu(vo.isMenu());
        permission.setUrl(vo.getUrl());
        permission.setSort(vo.getSort());
        permission.setParent(parent);
        permissionDao.save(permission);
        for (TypeInfo lang : vo.getLang()) {
            SysPermissionLang permissionLang = new SysPermissionLang(permission, lang.getCode());
            permissionLang.setName(lang.getName());
            permissionLangDao.save(permissionLang);
            permission.addLang(permissionLang);
        }
        return permission;
    }

}
