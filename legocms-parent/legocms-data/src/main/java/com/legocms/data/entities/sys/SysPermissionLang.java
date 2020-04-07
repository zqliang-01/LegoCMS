package com.legocms.data.entities.sys;

import java.util.Map;

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
@Table(name = "sys_permission_lang")
@EqualsAndHashCode(callSuper = true)
public class SysPermissionLang extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", nullable = false)
    private SysPermission permission;

    protected SysPermissionLang() { }

    public SysPermissionLang(SysPermission permission, String code) {
        super(code);
        this.permission = permission;
    }

    @Override
    protected void doBuildReadableSnapshot(Map<String, String> attributes) {
        attributes.put("编码", getCode());
        attributes.put("名称", getName());
        attributes.put("所属授权", permission.getCode());
    }
}