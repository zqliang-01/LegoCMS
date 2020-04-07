package com.legocms.data.entities.cms.simpletype;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.legocms.data.entities.SimpleType;

@Entity
@Table(name = "cms_simple_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "class_type", discriminatorType = DiscriminatorType.STRING)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CmsSimpleType extends SimpleType {

    protected CmsSimpleType() { }

    public CmsSimpleType(String code, String name, int sequence) {
        super(code);
        this.setName(name);
        this.setSequence(sequence);
    }
}
