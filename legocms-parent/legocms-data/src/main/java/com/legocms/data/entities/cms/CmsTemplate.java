package com.legocms.data.entities.cms;

import java.util.Date;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.legocms.core.common.DateUtil;
import com.legocms.data.base.BaseEntity;
import com.legocms.data.entities.cms.simpletype.CmsTemplateType;
import com.legocms.data.entities.sys.SysSite;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "cms_template")
@EqualsAndHashCode(callSuper = true)
public class CmsTemplate extends BaseEntity {

    private String content;
    private Date updateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private CmsTemplateType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private CmsTemplate parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", referencedColumnName = "id")
    private SysSite site;

    protected CmsTemplate() { }

    public CmsTemplate(String code, SysSite site) {
        super(code);
        this.site = site;
        this.updateTime = DateUtil.getCurrentDate();
    }

    @Override
    protected void doBuildReadableSnapshot(Map<String, String> attributes) {
        attributes.put("编码", getCode());
        attributes.put("名称", getName());
        attributes.put("内容", getContent());
        attributes.put("类型", type.getName());
        attributes.put("站点", site.getName());
        String parentName = "";
        if (parent != null) {
            parentName = parent.getName();
        }
        attributes.put("上级目录", parentName);
    }
}