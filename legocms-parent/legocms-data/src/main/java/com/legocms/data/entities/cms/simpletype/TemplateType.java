package com.legocms.data.entities.cms.simpletype;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("TemplateType")
public class TemplateType extends CmsSimpleType {

    public TemplateType() { }

    public TemplateType(String code, String name, int sequence) {
        super(code, name, sequence);
    }
}
