package com.legocms.data.entities.sys.simpletype;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("UserStatus")
public class UserStatus extends SysSimpleType {

    public UserStatus() { }

    public UserStatus(String code, String name, int sequence) {
        super(code, name, sequence);
    }
}
