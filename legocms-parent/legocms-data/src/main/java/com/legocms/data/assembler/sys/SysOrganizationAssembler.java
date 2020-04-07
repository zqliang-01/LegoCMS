package com.legocms.data.assembler.sys;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.sys.SysOrganizationDetailInfo;
import com.legocms.core.dto.sys.SysOrganizationInfo;
import com.legocms.data.assembler.AbstractAssembler;
import com.legocms.data.entities.sys.SysOrganization;

@Component
public class SysOrganizationAssembler extends AbstractAssembler<SysOrganizationInfo, SysOrganization> {

    @Override
    public SysOrganizationInfo create(SysOrganization entity) {
        SysOrganizationInfo info = new SysOrganizationInfo();
        info.setCode(entity.getCode());
        info.setName(entity.getName());
        info.setStatus(typeInfoAssembler.create(entity.getStatus()));
        if (entity.getParent() != null) {
            info.setParent(typeInfoAssembler.create(entity.getParent()));
        }
        return info;
    }

    public SysOrganizationDetailInfo createDetail(SysOrganization entity) {
        SysOrganizationDetailInfo info = new SysOrganizationDetailInfo();
        info.setCode(entity.getCode());
        info.setName(entity.getName());
        info.setStatus(typeInfoAssembler.create(entity.getStatus()));
        if (entity.getParent() != null) {
            info.setParent(typeInfoAssembler.create(entity.getParent()));
        }
        info.setCreateTime(entity.getCreateTime());
        return info;
    }

    public List<SimpleTreeInfo> createSimpleTree(List<SysOrganization> organizations) {
        List<SimpleTreeInfo> trees = new ArrayList<SimpleTreeInfo>();
        for (SysOrganization organization : organizations) {
            SimpleTreeInfo tree = new SimpleTreeInfo();
            tree.setCode(organization.getCode());
            tree.setName(organization.getName());
            if (organization.getParent() != null) {
                tree.setParentCode(organization.getParent().getCode());
            }
            trees.add(tree);
        }
        return trees;
    }
}
