package com.legocms.data.assembler.sys;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.dto.sys.SysPermissionInfo;
import com.legocms.data.assembler.AbstractAssembler;
import com.legocms.data.dao.sys.ISysPermissionLangDao;
import com.legocms.data.entities.sys.SysPermission;
import com.legocms.data.entities.sys.SysPermissionLang;

@Component
public class SysModuleAssembler extends AbstractAssembler<SysPermissionInfo, SysPermission> {

    @Autowired
    private ISysPermissionLangDao moduleLangDao;

    public SysPermissionInfo create(SysPermission entity, String lang) {
        SysPermissionLang moduleLang = this.moduleLangDao.findBy(entity, lang);
        SysPermissionInfo info = new SysPermissionInfo();
        info.setCode(entity.getCode());
        info.setIcon(entity.getIcon());
        info.setName(moduleLang.getValue());
        info.setSort(entity.getSort());
        info.setUrl(entity.getUrl());
        return info;
    }

    public SysPermissionInfo create(SysPermission entity) {
        return create(entity, "zh");
    }

    public List<SysPermissionInfo> create(List<SysPermission> entities) {
        List<SysPermissionInfo> infos = new ArrayList<SysPermissionInfo>();
        for (SysPermission entity : entities) {
            infos.add(create(entity));
        }
        return super.create(entities);
    }

    public List<SysPermissionInfo> create(List<SysPermission> entities, String lang) {
        List<SysPermissionInfo> infos = new ArrayList<SysPermissionInfo>();
        for (SysPermission entity : entities) {
            infos.add(create(entity, lang));
        }
        return super.create(entities);
    }
}