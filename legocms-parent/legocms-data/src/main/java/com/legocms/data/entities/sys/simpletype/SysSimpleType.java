package com.legocms.data.entities.sys.simpletype;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.legocms.data.entities.sys.SimpleType;

@Entity
@Table(name = "sys_simpletypes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "classtype", discriminatorType = DiscriminatorType.STRING)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysSimpleType extends SimpleType {

    protected SysSimpleType() { }

    public SysSimpleType(String code, String name, int sequence) {
        super(code);
        this.setName(name);
        this.setSequence(sequence);
    }
}
