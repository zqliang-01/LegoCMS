package com.legocms.data.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.legocms.core.dto.TypeInfo;
import com.legocms.data.base.BaseEntity;

@Component
public class TypeInfoAssembler extends AbstractAssembler<TypeInfo, BaseEntity> {

    @Override
    public TypeInfo create(BaseEntity entity) {
        return new TypeInfo(entity.getCode(), entity.getName());
    }

    public List<TypeInfo> create(Collection<? extends BaseEntity> entities) {
        List<TypeInfo> infos = new ArrayList<TypeInfo>();
        for (BaseEntity entity : entities) {
            infos.add(create(entity));
        }
        return infos;
    }

}
