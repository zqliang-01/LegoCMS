package com.legocms.core.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TypeInfo extends Dto {

    private static final long serialVersionUID = 75501136291357532L;

    private String code;
    private String name;

    public TypeInfo() { }

    public TypeInfo(String code) {
        this.code = code;
    }

    public TypeInfo(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
