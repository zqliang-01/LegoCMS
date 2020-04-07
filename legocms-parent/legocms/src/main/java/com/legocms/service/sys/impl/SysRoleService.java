package com.legocms.service.sys.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legocms.core.dto.Page;
import com.legocms.core.dto.TypeCheckInfo;
import com.legocms.core.dto.sys.SysRoleInfo;
import com.legocms.data.act.sys.AddSysRoleAction;
import com.legocms.data.act.sys.DeleteSysRoleAction;
import com.legocms.data.act.sys.ModifySysRoleAction;
import com.legocms.data.act.sys.ModifySysRoleAuthorizeAction;
import com.legocms.data.assembler.TypeInfoAssembler;
import com.legocms.data.assembler.sys.SysRoleAssembler;
import com.legocms.data.dao.sys.ISysRoleDao;
import com.legocms.data.entities.sys.SysRole;
import com.legocms.service.BaseService;
import com.legocms.service.sys.ISysRoleService;

@Service
public class SysRoleService extends BaseService implements ISysRoleService {

    @Autowired
    private ISysRoleDao roleDao;

    @Autowired
    private SysRoleAssembler roleAssembler;

    @Autowired
    private TypeInfoAssembler typeInfoAssembler;

    @Override
    public List<TypeCheckInfo> findSimple(String userCode) {
        List<SysRole> allRoles = roleDao.findAll();
        List<SysRole> roles = roleDao.findByUser(userCode);
        return typeInfoAssembler.createCheck(allRoles, roles);
    }

    @Override
    public Page<SysRoleInfo> findBy(String code, String name, int pageIndex, int pageSize) {
        Page<SysRole> page = roleDao.findBy(code, name, pageIndex, pageSize);
        return roleAssembler.createInfoPage(page);
    }

    @Override
    public void authorize(String operator, String roleCode, List<String> permissionCodes) {
        new ModifySysRoleAuthorizeAction(operator, roleCode, permissionCodes).run();
    }

    @Override
    public void add(String operator, String code, String name) {
        new AddSysRoleAction(operator, code, name).run();
    }

    @Override
    public void modify(String operator, String code, String name) {
        new ModifySysRoleAction(operator, code, name).run();
    }

    @Override
    public void delete(String operator, String code) {
        new DeleteSysRoleAction(operator, code).run();
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
