package com.legocms.data.entities.cms.simpletype;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PlaceType")
public class PlaceType extends CmsSimpleType {

    public PlaceType() { }

    public PlaceType(String code, String name, int sequence) {
        super(code, name, sequence);
    }
}
