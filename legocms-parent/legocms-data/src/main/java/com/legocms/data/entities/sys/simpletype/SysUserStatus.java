package com.legocms.data.entities.sys.simpletype;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("UserStatus")
public class SysUserStatus extends SysSimpleType {

    public SysUserStatus() { }

    public SysUserStatus(String code, String name, int sequence) {
        super(code, name, sequence);
    }
}