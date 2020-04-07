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
@Table(name = "sys_site")
@EqualsAndHashCode(callSuper = true)
public class SysSite extends BaseEntity {

    private String path;
    private String dynamicPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private SysOrganization organization;

    protected SysSite() { }

    public SysSite(String code) {
        super(code);
    }

    @Override
    protected void doBuildReadableSnapshot(Map<String, String> attributes) {
        attributes.put("编码", getCode());
        attributes.put("名称", getName());
        attributes.put("站点地址", getPath());
        attributes.put("动态站点地址", getDynamicPath());
        attributes.put("部门", organization.getName());
    }
}