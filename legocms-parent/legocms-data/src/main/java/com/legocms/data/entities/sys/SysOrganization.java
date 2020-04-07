package com.legocms.data.entities.sys;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.legocms.data.base.BaseEntity;
import com.legocms.data.entities.sys.simpletype.OrganizationStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "sys_organization")
@EqualsAndHashCode(callSuper = true)
public class SysOrganization extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status", referencedColumnName = "id")
    private OrganizationStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private SysOrganization parent;

    protected SysOrganization() { }

    public SysOrganization(String code) {
        super(code);
    }
}
