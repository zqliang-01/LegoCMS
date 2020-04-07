package com.legocms.data.assembler.sys;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.legocms.core.dto.Page;
import com.legocms.core.dto.sys.SysSiteInfo;
import com.legocms.data.assembler.AbstractAssembler;
import com.legocms.data.entities.sys.SysSite;

@Component
public class SysSiteAssembler extends AbstractAssembler<SysSiteInfo, SysSite> {

    public SysSiteInfo create(SysSite entity, String manageSite) {
        SysSiteInfo info = create(entity);
        if (entity.getCode().equals(manageSite)) {
            info.setManageSite(true);
        }
        return info;
    }

    public Page<SysSiteInfo> createInfoPage(Page<SysSite> ePage, String manageSite) {
        return new Page<SysSiteInfo>(create(ePage.getResult(), manageSite), ePage.getCurrent(), ePage.getPageSize(), ePage.getTotalCount());
    }

    @Override
    public SysSiteInfo create(SysSite entity) {
        SysSiteInfo info = new SysSiteInfo();
        info.setCode(entity.getCode());
        info.setName(entity.getName());
        info.setPath(entity.getPath());
        info.setDynamicPath(entity.getDynamicPath());
        info.setOrganization(typeInfoAssembler.create(entity.getOrganization()));
        info.setCreateDate(entity.getCreateDate());
        return info;
    }

    public List<SysSiteInfo> create(List<SysSite> entities, String manageSite) {
        List<SysSiteInfo> infos = new ArrayList<SysSiteInfo>();
        for (SysSite entity : entities) {
            infos.add(create(entity, manageSite));
        }
        return infos;
    }
}