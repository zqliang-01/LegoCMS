package com.legocms.data.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.legocms.core.dto.Dto;
import com.legocms.core.dto.Page;
import com.legocms.data.base.BaseEntity;

public abstract class AbstractAssembler<T extends Dto, E extends BaseEntity> implements IBaseAssembler<T, E> {

    @Autowired
    protected TypeInfoAssembler typeInfoAssembler;

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

    public List<String> createCodes(List<? extends BaseEntity> entities) {
        List<String> codes = new ArrayList<String>();
        for (BaseEntity entity : entities) {
            codes.add(entity.getCode());
        }
        return codes;
    }
}
