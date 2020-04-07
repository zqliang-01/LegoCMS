package com.legocms.data.entities.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    @OneToMany(targetEntity = SysPermissionLang.class, mappedBy = "permission", fetch = FetchType.LAZY)
    private List<SysPermissionLang> langs = new ArrayList<SysPermissionLang>();

    protected SysPermission() { }

    public SysPermission(String code) {
        super(code);
    }

    public void addLang(SysPermissionLang lang) {
        langs.add(lang);
    }

    @Override
    protected void doBuildReadableSnapshot(Map<String, String> attributes) {
        attributes.put("编码", getCode());
        attributes.put("url", url);
        attributes.put("authorizedUrl", authorizedUrl);
        attributes.put("图标", icon);
        String parentCode = "";
        if (parent != null) {
            parentCode = parent.getCode();
        }
        attributes.put("上级菜单", parentCode);
        attributes.put("是否菜单", String.valueOf(menu));
        attributes.put("序号", String.valueOf(sort));
        List<String> langName = new ArrayList<String>();
        for (SysPermissionLang lang : getLangs()) {
            langName.add(lang.getName());
        }
        attributes.put("语言", langName.toString());
    }
}
