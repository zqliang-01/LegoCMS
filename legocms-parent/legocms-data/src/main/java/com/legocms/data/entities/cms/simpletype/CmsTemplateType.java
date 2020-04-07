package com.legocms.data.entities.cms.simpletype;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("TemplateType")
public class CmsTemplateType extends CmsSimpleType {

    public CmsTemplateType() { }

    public CmsTemplateType(String code, String name, int sequence) {
        super(code, name, sequence);
    }
}
