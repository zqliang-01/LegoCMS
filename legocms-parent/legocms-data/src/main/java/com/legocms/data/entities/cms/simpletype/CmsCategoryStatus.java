package com.legocms.data.entities.cms.simpletype;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CategoryStatus")
public class CmsCategoryStatus extends CmsSimpleType {

    public CmsCategoryStatus() { }

    public CmsCategoryStatus(String code, String name, int sequence) {
        super(code, name, sequence);
    }
}
