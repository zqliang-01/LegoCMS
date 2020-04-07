package com.legocms.data.entities.cms;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.legocms.data.base.BaseEntity;
import com.legocms.data.entities.sys.SysSite;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "cms_model")
@EqualsAndHashCode(callSuper = true)
public class CmsModel extends BaseEntity {

    private boolean hasFiles;
    private boolean hasImages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", referencedColumnName = "id")
    private CmsTemplate template;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private CmsModel parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", referencedColumnName = "id")
    private SysSite site;


    @OrderBy("sort ASC")
    @OneToMany(targetEntity = CmsModelAttribute.class, mappedBy = "model", fetch = FetchType.LAZY)
    private List<CmsModelAttribute> attributes = new ArrayList<CmsModelAttribute>();

    protected CmsModel() { }

    public CmsModel(String code) {
        super(code);
    }

    @Override
    protected void doBuildReadableSnapshot(Map<String, String> attributes) {
        attributes.put("编码", getCode());
        attributes.put("名称", getName());
        attributes.put("拥有图片列表", String.valueOf(hasImages));
        attributes.put("拥有附件列表", String.valueOf(hasFiles));
        attributes.put("模板", template.getName());
        List<String> attributeNames = new ArrayList<String>();
        for (CmsModelAttribute attribute : getAttributes()) {
            String message = MessageFormat.format("[属性编码:{0}，属性文本:{1}，必填:{2}，编辑类型:{3}]", attribute.getCode(), attribute.getName(), attribute.isRequired(), attribute.getType().getCode());
            attributeNames.add(message);
        }
        attributes.put("属性", attributeNames.toString());
    }
}
