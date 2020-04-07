package com.legocms.data.entities.cms.simpletype;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FileType")
public class CmsFileType extends CmsSimpleType {

    public CmsFileType() { }

    public CmsFileType(String code, String name, int sequence) {
        super(code, name, sequence);
    }
}
