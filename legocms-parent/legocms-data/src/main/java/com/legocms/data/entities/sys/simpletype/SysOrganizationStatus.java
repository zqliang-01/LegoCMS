package com.legocms.data.entities.sys.simpletype;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("OrganizationStatus")
public class SysOrganizationStatus extends SysSimpleType {

    public SysOrganizationStatus() { }

    public SysOrganizationStatus(String code, String name, int sequence) {
        super(code, name, sequence);
    }
}
