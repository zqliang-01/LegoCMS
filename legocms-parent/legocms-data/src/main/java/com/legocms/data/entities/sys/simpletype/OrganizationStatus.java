package com.legocms.data.entities.sys.simpletype;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("OrganizationStatus")
public class OrganizationStatus extends SysSimpleType {

    public OrganizationStatus() { }

    public OrganizationStatus(String code, String name, int sequence) {
        super(code, name, sequence);
    }
}
