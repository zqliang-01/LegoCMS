package com.legocms.service.sys.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legocms.core.common.StringUtil;
import com.legocms.core.dto.SimpleCheckTreeInfo;
import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.TypeInfo;
import com.legocms.core.dto.sys.SysPermissionDetailInfo;
import com.legocms.core.dto.sys.SysPermissionInfo;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.sys.SysPermissionVo;
import com.legocms.data.assembler.sys.SysPermissionAssembler;
import com.legocms.data.dao.sys.ISysPermissionDao;
import com.legocms.data.dao.sys.ISysPermissionLangDao;
import com.legocms.data.dao.sys.ISysRoleDao;
import com.legocms.data.entities.sys.SysPermission;
import com.legocms.data.entities.sys.SysPermissionLang;
import com.legocms.service.BaseService;
import com.legocms.service.sys.ISysPermissionService;

@Service
public class SysPermissionService extends BaseService implements ISysPermissionService {

    @Autowired
    private ISysPermissionDao permissionDao;

    @Autowired
    private ISysRoleDao roleDao;

    @Autowired
    private SysPermissionAssembler permissionAssembler;

    @Autowired
    private ISysPermissionLangDao permissionLangDao;

    @Override
    public List<SysPermissionInfo> findBy(String userCode, String parentCode, String lang, boolean menu) {
        List<SysPermission> permissions = permissionDao.findBy(userCode, parentCode, menu);
        return this.permissionAssembler.create(permissions, lang, menu);
    }

    @Override
    public List<SimpleTreeInfo> findSimpleTree(String lang) {
        List<SysPermission> permissions = permissionDao.findAll();
        return permissionAssembler.createSimpleTree(permissions, lang);
    }

    @Override
    public List<SimpleCheckTreeInfo> findSimpleCheckTree(String roleCode, String lang) {
        List<SysPermission> accessibles = permissionDao.findAccessible(roleCode);
        List<SysPermission> permissions = permissionDao.findAll();
        return permissionAssembler.createSimpleCheckTree(permissions, accessibles, lang);
    }

    @Override
    public SysPermissionDetailInfo findDetailBy(String code) {
        SysPermission permission = permissionDao.findByCode(code);
        return permissionAssembler.createDetail(permission);
    }

    @Override
    public void save(SysPermissionVo vo) {
        BusinessException.check(vo.getParent() != null, "请选择上级权限");
        SysPermission permission = permissionDao.findByUnsureCode(vo.getCode());
        if (permission == null) {
            permission = new SysPermission(vo.getCode());
        }
        permission.setIcon(vo.getIcon());
        permission.setMenu(vo.isMenu());
        permission.setUrl(vo.getUrl());
        permission.setSort(vo.getSort());
        SysPermission parent = permissionDao.findByUnsureCode(vo.getParent().getCode());
        if (parent != null && !parent.equals(permission)) {
            permission.setParent(parent);
        }
        permissionDao.save(permission);
        for (TypeInfo lang : vo.getLang()) {
            BusinessException.check(StringUtil.isNotBlank(lang.getCode()), "lang code不能为空");
            SysPermissionLang permissionLang = permissionLangDao.findBy(permission, lang.getCode());
            if (permissionLang == null) {
                permissionLang = new SysPermissionLang(permission, lang.getCode());
            }
            permissionLang.setName(lang.getName());
            permissionLangDao.save(permissionLang);
        }
    }

    @Override
    public void delete(String code) {
        SysPermission permission = permissionDao.findByCode(code);
        BusinessException.check(roleDao.findByPermission(permission).isEmpty(), "该菜单已授权，删除失败！");
        BusinessException.check(permissionDao.findChildren(code).isEmpty(), "存在下级菜单，删除失败！");
        List<SysPermissionLang> permissionLangs = permissionLangDao.findBy(permission);
        permissionLangDao.deleteInBatch(permissionLangs);
        permissionDao.delete(permission);
    }
}