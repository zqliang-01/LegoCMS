package com.legocms.data.entities.sys;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.legocms.data.base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "sys_organization")
@EqualsAndHashCode(callSuper = false)
public class SysOrganization extends BaseEntity {

    private String name;
    private boolean state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private SysOrganization parent;
}
