package com.legocms.data.entities.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.legocms.data.base.BaseEntity;
import com.legocms.data.entities.sys.simpletype.SysUserStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "sys_user")
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity {

    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private SysOrganization organization;

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "sys_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private List<SysRole> roles = new ArrayList<SysRole>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private SysUserStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id")
    private SysSite site;

    protected SysUser() { }

    public SysUser(String code) {
        super(code);
    }

    @Override
    protected void doBuildReadableSnapshot(Map<String, String> attributes) {
        attributes.put("账号", getCode());
        attributes.put("用户名", getName());
        attributes.put("密码", password);
        attributes.put("部门 ", organization.getName());
        attributes.put("状态", status.getName());
        List<String> roleNames = new ArrayList<String>();
        for (SysRole role : roles) {
            roleNames.add(role.getName());
        }
        attributes.put("角色", roleNames.toString());
        String siteName = "";
        if (site != null) {
            siteName = site.getName();
        }
        attributes.put("管理站点", siteName);
    }
}
