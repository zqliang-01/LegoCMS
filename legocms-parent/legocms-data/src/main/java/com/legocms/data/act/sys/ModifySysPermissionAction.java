package com.legocms.data.act.sys;

import com.legocms.core.common.StringUtil;
import com.legocms.core.dto.TypeInfo;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.core.vo.sys.SysPermissionVo;
import com.legocms.data.act.ModifyAction;
import com.legocms.data.dao.sys.ISysPermissionLangDao;
import com.legocms.data.entities.sys.SysPermission;
import com.legocms.data.entities.sys.SysPermissionLang;

public class ModifySysPermissionAction extends ModifyAction<SysPermission> {

    private SysPermission parent;
    private SysPermissionVo vo;

    private ISysPermissionLangDao permissionLangDao = getDao(ISysPermissionLangDao.class);

    public ModifySysPermissionAction(String operator, SysPermissionVo vo) {
        super(SysPermissionCode.PERMISSION, operator);
        this.vo = vo;
    }

    @Override
    protected void preprocess() {
        BusinessException.check(StringUtil.isNotBlank(vo.getCode()), "权限编码不能为空！");
        for (TypeInfo lang : vo.getLang()) {
            BusinessException.check(StringUtil.isNotBlank(lang.getCode()), "lang code不能为空");
        }

        SysPermission parent = permissionDao.findByUnsureCode(vo.getParent().getCode());
        if (parent != null && !parent.getCode().equals(vo.getCode())) {
            this.parent = parent;
        }

        SysPermission permission = permissionDao.findByUnsureCode(vo.getCode());
        BusinessException.check(permission != null, "不存在编号为【{0}】的权限信息，创建权限失败！", vo.getCode());
        setTargetEntity(permission);
    }

    @Override
    protected void doModify(SysPermission permission) {
        permission.setIcon(vo.getIcon());
        permission.setMenu(vo.isMenu());
        permission.setUrl(vo.getUrl());
        permission.setSort(vo.getSort());
        permission.setParent(parent);
        permissionDao.save(permission);
        for (TypeInfo lang : vo.getLang()) {
            SysPermissionLang permissionLang = permissionLangDao.findBy(permission, lang.getCode());
            if (permissionLang == null) {
                permissionLang = new SysPermissionLang(permission, lang.getCode());
            }
            permissionLang.setName(lang.getName());
            permissionLangDao.save(permissionLang);
        }
    }

}
