package com.legocms.data.assembler;

import org.springframework.stereotype.Component;

import com.legocms.core.dto.TypeInfo;
import com.legocms.data.entities.sys.SimpleType;

@Component
public class TypeInfoAssembler extends AbstractAssembler<TypeInfo, SimpleType> {

    @Override
    public TypeInfo create(SimpleType entity) {
        return new TypeInfo(entity.getCode(), entity.getName());
    }

}
