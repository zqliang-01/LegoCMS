package com.legocms.data.assembler;

import java.util.ArrayList;
import java.util.List;

import com.legocms.core.dto.Dto;
import com.legocms.core.dto.Page;
import com.legocms.data.base.BaseEntity;

public abstract class AbstractAssembler<T extends Dto, E extends BaseEntity> implements IBaseAssembler<T, E> {

    @Override
    public List<T> create(List<E> entities) {
        List<T> infos = new ArrayList<T>();
        for (E entity : entities) {
            infos.add(create(entity));
        }
        return infos;
    }

    @Override
    public Page<T> createInfoPage(Page<E> ePage) {
        return new Page<T>(create(ePage.getResult()), ePage.getCurrent(), ePage.getPageSize(), ePage.getTotalCount());
    }
}
