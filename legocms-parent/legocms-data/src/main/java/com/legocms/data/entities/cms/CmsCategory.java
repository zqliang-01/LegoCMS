package com.legocms.data.entities.cms;

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

import com.legocms.core.common.StringUtil;
import com.legocms.data.base.BaseEntity;
import com.legocms.data.entities.cms.simpletype.CmsCategoryStatus;
import com.legocms.data.entities.cms.simpletype.CmsCategoryType;
import com.legocms.data.entities.sys.SysSite;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "cms_category")
@EqualsAndHashCode(callSuper = true)
public class CmsCategory extends BaseEntity {

    private String path;
    private String contentPath;
    private int pageSize;
    private int sort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private CmsCategory parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private CmsCategoryType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", referencedColumnName = "id")
    private CmsTemplate template;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", referencedColumnName = "id")
    private SysSite site;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private CmsCategoryStatus status;

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "cms_category_model", joinColumns = { @JoinColumn(name = "category_id") }, inverseJoinColumns = { @JoinColumn(name = "model_id") })
    private List<CmsModel> models = new ArrayList<CmsModel>();

    protected CmsCategory() { }

    public CmsCategory(String code) {
        super(code);
    }

    @Override
    protected void doBuildReadableSnapshot(Map<String, String> attributes) {
        attributes.put("编码", getCode());
        attributes.put("名称", getName());
        attributes.put("访问路径", path);
        attributes.put("内容访问路径", contentPath);
        attributes.put("每页数据条数", StringUtil.objToStr(pageSize));
        attributes.put("序号", StringUtil.objToStr(sort));
        String parentName = "";
        if (parent != null) {
            parentName = parent.getName();
        }
        attributes.put("父分类", parentName);
        List<String> modelName = new ArrayList<String>();
        for (CmsModel model : models) {
            modelName.add(model.getName());
        }
        attributes.put("模型", StringUtil.objToStr(modelName));
        attributes.put("类型", type.getName());
        attributes.put("模板", template.getName());
        attributes.put("状态", status.getName());
    }
}