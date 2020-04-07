package com.legocms.data.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.legocms.core.dto.TypeCheckInfo;
import com.legocms.core.dto.TypeInfo;
import com.legocms.data.base.BaseEntity;

@Component
public class TypeInfoAssembler extends AbstractAssembler<TypeInfo, BaseEntity> {

    @Override
    public TypeInfo create(BaseEntity entity) {
        return new TypeInfo(entity.getCode(), entity.getName());
    }

    public List<String> codes(Collection<? extends BaseEntity> entities) {
        List<String> codes = new ArrayList<String>();
        for (BaseEntity entity : entities) {
            codes.add(entity.getCode());
        }
        return codes;
    }

    public List<TypeInfo> create(Collection<? extends BaseEntity> entities) {
        List<TypeInfo> infos = new ArrayList<TypeInfo>();
        for (BaseEntity entity : entities) {
            infos.add(create(entity));
        }
        return infos;
    }

    public List<TypeCheckInfo> createCheck(Collection<? extends BaseEntity> allEntities, List<? extends BaseEntity> entities) {
        List<TypeCheckInfo> infos = new ArrayList<TypeCheckInfo>();
        for (BaseEntity entity : allEntities) {
            if (entities.contains(entity)) {
                infos.add(new TypeCheckInfo(entity.getCode(), entity.getName(), true));
            }
            else {
                infos.add(new TypeCheckInfo(entity.getCode(), entity.getName(), false));
            }
        }
        return infos;
    }
}
