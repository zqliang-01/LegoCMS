package com.legocms.service.sys.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legocms.core.dto.Page;
import com.legocms.core.dto.TypeCheckInfo;
import com.legocms.core.dto.sys.SysRoleInfo;
import com.legocms.data.assembler.TypeInfoAssembler;
import com.legocms.data.assembler.sys.SysRoleAssembler;
import com.legocms.data.dao.sys.ISysPermissionDao;
import com.legocms.data.dao.sys.ISysRoleDao;
import com.legocms.data.entities.sys.SysPermission;
import com.legocms.data.entities.sys.SysRole;
import com.legocms.service.BaseService;
import com.legocms.service.sys.ISysRoleService;

@Service
public class SysRoleService extends BaseService implements ISysRoleService {

    @Autowired
    private ISysRoleDao roleDao;

    @Autowired
    private ISysPermissionDao permissionDao;

    @Autowired
    private SysRoleAssembler roleAssembler;

    @Autowired
    private TypeInfoAssembler typeInfoAssembler;

    @Override
    public List<TypeCheckInfo> findSimple(String userCode) {
        List<SysRole> allRoles = roleDao.findAll();
        List<SysRole> roles = roleDao.findBy(userCode);
        return typeInfoAssembler.createCheck(allRoles, roles);
    }

    @Override
    public Page<SysRoleInfo> findBy(String code, String name, int pageIndex, int pageSize) {
        Page<SysRole> page = roleDao.findBy(code, name, pageIndex, pageSize);
        return roleAssembler.createInfoPage(page);
    }

    @Override
    public void authorize(String roleCode, List<String> permissionCodes) {
        SysRole role = roleDao.findByCode(roleCode);
        List<SysPermission> permissions = permissionDao.findByCodes(permissionCodes);
        role.setPermissions(permissions);
        roleDao.save(role);
    }

    @Override
    public void save(String code, String name) {
        SysRole role = roleDao.findByUnsureCode(code);
        if (role == null) {
            role = new SysRole(code);
        }
        role.setName(name);
        roleDao.save(role);
    }

    @Override
    public void delete(String code) {
        SysRole role = roleDao.findByCode(code);
        roleDao.delete(role);
    }

    @Override
    public SysRoleInfo findByCode(String code) {
        SysRole role = roleDao.findByUnsureCode(code);
        if (role == null) {
            return null;
        }
        return roleAssembler.create(role);
    }
}
