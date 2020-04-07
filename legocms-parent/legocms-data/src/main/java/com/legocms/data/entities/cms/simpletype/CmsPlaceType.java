package com.legocms.data.entities.cms.simpletype;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PlaceType")
public class CmsPlaceType extends CmsSimpleType {

    public CmsPlaceType() { }

    public CmsPlaceType(String code, String name, int sequence) {
        super(code, name, sequence);
    }
}
