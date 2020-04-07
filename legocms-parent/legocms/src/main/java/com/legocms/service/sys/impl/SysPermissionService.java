package com.legocms.service.sys.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legocms.core.dto.SimpleCheckTreeInfo;
import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.sys.SysPermissionDetailInfo;
import com.legocms.core.dto.sys.SysPermissionInfo;
import com.legocms.core.vo.sys.SysPermissionVo;
import com.legocms.data.act.sys.AddSysPermissionAction;
import com.legocms.data.act.sys.DeleteSysPermissionAction;
import com.legocms.data.act.sys.ModifySysPermissionAction;
import com.legocms.data.assembler.sys.SysPermissionAssembler;
import com.legocms.data.dao.sys.ISysPermissionDao;
import com.legocms.data.entities.sys.SysPermission;
import com.legocms.service.BaseService;
import com.legocms.service.sys.ISysPermissionService;

@Service
public class SysPermissionService extends BaseService implements ISysPermissionService {

    @Autowired
    private ISysPermissionDao permissionDao;

    @Autowired
    private SysPermissionAssembler permissionAssembler;

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
    public void add(String operator, SysPermissionVo vo) {
        new AddSysPermissionAction(operator, vo).run();
    }

    @Override
    public void modify(String operator, SysPermissionVo vo) {
        new ModifySysPermissionAction(operator, vo).run();
    }

    @Override
    public void delete(String operator, String code) {
        new DeleteSysPermissionAction(operator, code).run();
    }
}