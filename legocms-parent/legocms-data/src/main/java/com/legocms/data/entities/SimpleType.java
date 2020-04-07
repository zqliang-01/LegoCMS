package com.legocms.data.entities;

import javax.persistence.MappedSuperclass;

import com.legocms.data.base.BaseEntity;

@MappedSuperclass
public abstract class SimpleType extends BaseEntity {

    private Integer sequence;

    protected SimpleType() { }

    public SimpleType(String code) {
        super(code);
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}
