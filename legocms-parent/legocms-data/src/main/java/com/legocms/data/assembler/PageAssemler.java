package com.legocms.data.assembler;

import com.legocms.core.common.ClassUtil;
import com.legocms.core.dto.Dto;
import com.legocms.core.dto.Page;
import com.legocms.data.base.BaseEntity;

public class PageAssemler {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <D extends Dto> Page<D> createInfos(Page<? extends BaseEntity> page, Class<? extends IBaseAssembler> clazz) {
        IBaseAssembler assembler = ClassUtil.getInstance(clazz);
        return new Page<D>(assembler.create(page.getResult()), page.getCurrent(), page.getPageSize(), page.getTotalCount());
    }
}
