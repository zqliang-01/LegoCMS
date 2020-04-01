package com.legocms.data.assembler;

import java.util.ArrayList;
import java.util.List;

import com.legocms.core.dto.Dto;
import com.legocms.data.base.BaseEntity;


public abstract class AbstractAssembler<T extends Dto, E extends BaseEntity> implements IBaseAssembler<T, E> {

    public List<T> create(List<E> entities) {
        List<T> ts = new ArrayList<T>();
        for (E e : entities) {
            ts.add(create(e));
        }
        return ts;
    }

}
