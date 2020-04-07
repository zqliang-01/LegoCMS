package com.legocms.data.assembler;

import java.util.List;

import com.legocms.core.dto.Dto;
import com.legocms.core.dto.Page;
import com.legocms.data.base.BaseEntity;

public interface IBaseAssembler<T extends Dto, E extends BaseEntity> {

    T create(E entity);

    List<T> create(List<E> entities);

    Page<T> createInfoPage(Page<E> ePage);
}
