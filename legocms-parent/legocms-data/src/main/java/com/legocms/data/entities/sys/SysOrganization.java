package com.legocms.data.entities.sys;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.legocms.data.base.BaseEntity;
import com.legocms.data.entities.sys.simpletype.SysOrganizationStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "sys_organization")
@EqualsAndHashCode(callSuper = true)
public class SysOrganization extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status", referencedColumnName = "id")
    private SysOrganizationStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private SysOrganization parent;

    protected SysOrganization() { }

    public SysOrganization(String code) {
        super(code);
    }

    @Override
    protected void doBuildReadableSnapshot(Map<String, String> attributes) {
        attributes.put("编码", getCode());
        attributes.put("名称", getName());
        attributes.put("状态", getStatus().getName());
        String parentName = "";
        if (parent != null) {
            parentName = parent.getName();
        }
        attributes.put("上级部门", parentName);
    }
}
