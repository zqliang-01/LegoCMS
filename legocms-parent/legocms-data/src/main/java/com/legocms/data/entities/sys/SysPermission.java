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
@Table(name = "sys_permission")
@EqualsAndHashCode(callSuper = true)
public class SysPermission extends BaseEntity {
    private String url;
    private String authorizedUrl;
    private String icon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private SysPermission parent;
    private boolean menu;
    private int sort;

    protected SysPermission() { }

    public SysPermission(String code) {
        super(code);
    }

}
