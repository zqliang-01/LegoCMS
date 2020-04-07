package com.legocms.data.entities.cms;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.legocms.data.base.BaseEntity;
import com.legocms.data.entities.cms.simpletype.CmsModelAttributeType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "cms_model_attribute")
@EqualsAndHashCode(callSuper = true)
public class CmsModelAttribute extends BaseEntity {

    private boolean required;
    private int sort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    private CmsModel model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type", referencedColumnName = "id")
    private CmsModelAttributeType type;

    protected CmsModelAttribute() { }

    public CmsModelAttribute(String code) {
        super(code);
    }
}