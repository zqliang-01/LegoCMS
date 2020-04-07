package com.legocms.data.assembler.sys;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.common.Constants;
import com.legocms.core.dto.SimpleCheckTreeInfo;
import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.TypeInfo;
import com.legocms.core.dto.sys.SysPermissionDetailInfo;
import com.legocms.core.dto.sys.SysPermissionInfo;
import com.legocms.core.vo.sys.SysPermissionLangCode;
import com.legocms.data.assembler.AbstractAssembler;
import com.legocms.data.dao.sys.ISysPermissionLangDao;
import com.legocms.data.entities.sys.SysPermission;
import com.legocms.data.entities.sys.SysPermissionLang;

@Component
public class SysPermissionAssembler extends AbstractAssembler<SysPermissionInfo, SysPermission> {

    @Autowired
    private ISysPermissionLangDao permissionLangDao;

    public SysPermissionInfo create(SysPermission entity, String lang) {
        SysPermissionLang moduleLang = permissionLangDao.findBy(entity, lang);
        SysPermissionInfo info = new SysPermissionInfo();
        info.setCode(entity.getCode());
        info.setIcon(entity.getIcon());
        if (moduleLang != null) {
            info.setName(moduleLang.getName());
        }
        info.setSort(entity.getSort());
        info.setUrl(entity.getUrl());
        return info;
    }

    public SysPermissionDetailInfo createDetail(SysPermission entity) {
        SysPermissionDetailInfo info = new SysPermissionDetailInfo();
        info.setCode(entity.getCode());
        info.setIcon(entity.getIcon());
        info.setSort(entity.getSort());
        info.setUrl(entity.getUrl());
        info.setMenu(entity.isMenu());
        info.setCreateDate(entity.getCreateDate());
        info.setSort(entity.getSort());
        if (entity.getParent() != null) {
            info.setParent(typeInfoAssembler.create(entity));
        }
        List<SysPermissionLang> langs = permissionLangDao.findBy(entity);
        info.setLang(typeInfoAssembler.create(langs));
        List<String> langCodes = createCodes(langs);
        for (String langCode : SysPermissionLangCode.ALL) {
            if (!langCodes.contains(langCode)) {
                info.addLang(new TypeInfo(langCode));
            }
        }
        return info;
    }

    public List<SimpleTreeInfo> createSimpleTree(List<SysPermission> permissions, String lang) {
        List<SimpleTreeInfo> trees = new ArrayList<SimpleTreeInfo>();
        for (SysPermission permission : permissions) {
            SysPermissionLang permissionLang = permissionLangDao.findBy(permission, lang);
            SimpleTreeInfo tree = new SimpleTreeInfo();
            tree.setCode(permission.getCode());
            if (permissionLang != null) {
                tree.setName(permissionLang.getName());
            }
            if (permission.getParent() != null) {
                tree.setParentCode(permission.getParent().getCode());
            }
            else {
                tree.setOpen(true);
            }
            trees.add(tree);
        }
        return trees;
    }

    public List<SimpleCheckTreeInfo> createSimpleCheckTree(List<SysPermission> permissions, List<SysPermission> accessibles, String lang) {
        List<SimpleCheckTreeInfo> trees = new ArrayList<SimpleCheckTreeInfo>();
        for (SysPermission permission : permissions) {
            SysPermissionLang permissionLang = permissionLangDao.findBy(permission, lang);
            SimpleCheckTreeInfo tree = new SimpleCheckTreeInfo();
            tree.setCode(permission.getCode());
            if (permissionLang != null) {
                tree.setName(permissionLang.getName());
            }
            if (permission.getParent() != null) {
                tree.setParentCode(permission.getParent().getCode());
            }
            else {
                tree.setOpen(true);
            }
            if (accessibles.contains(permission)) {
                tree.setChecked(true);
            }
            trees.add(tree);
        }
        return trees;
    }

    @Override
    public SysPermissionInfo create(SysPermission entity) {
        return create(entity, Constants.DEFAULT_LANG);
    }

    public List<SysPermissionInfo> create(List<SysPermission> permissions, String lang) {
        List<SysPermissionInfo> infos = new ArrayList<SysPermissionInfo>();
        for (SysPermission permission : permissions) {
            infos.add(create(permission, lang));
        }
        return infos;
    }

}
