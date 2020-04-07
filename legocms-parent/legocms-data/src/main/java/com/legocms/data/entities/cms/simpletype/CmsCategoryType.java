package com.legocms.data.entities.cms.simpletype;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CategoryType")
public class CmsCategoryType extends CmsSimpleType {

    public CmsCategoryType() { }

    public CmsCategoryType(String code, String name, int sequence) {
        super(code, name, sequence);
    }
}
