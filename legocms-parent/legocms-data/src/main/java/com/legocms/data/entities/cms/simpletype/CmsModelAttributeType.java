package com.legocms.data.entities.cms.simpletype;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ModelAttributeType")
public class CmsModelAttributeType extends CmsSimpleType {

    public CmsModelAttributeType() { }

    public CmsModelAttributeType(String code, String name, int sequence) {
        super(code, name, sequence);
    }
}
