package com.legocms.data.entities.sys;

import javax.persistence.MappedSuperclass;

import com.legocms.data.base.BaseEntity;

@MappedSuperclass
public abstract class SimpleType extends BaseEntity {

    private String name;
    private Integer sequence;

    protected SimpleType() { }

    public SimpleType(String code) {
        super(code);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}
