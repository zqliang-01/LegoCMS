package com.legocms.data.entities.sys.simpletype;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ActionType")
public class ActionType extends SysSimpleType {

    public ActionType() { }

    public ActionType(String code, String name, int sequence) {
        super(code, name, sequence);
    }
}
