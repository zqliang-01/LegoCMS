package com.legocms.data.base;

import java.util.List;
import java.util.Map;

import com.legocms.core.dto.Dto;

public interface ICommonDao {
    <T extends BaseEntity> T findByCode(Class<T> clazz, String code);

    <T extends BaseEntity> T findByUnsureCode(Class<T> clazz, String code);

    <T extends BaseEntity> List<T> findAllEnable(Class<T> clazz);

    <T extends Dto> T findBeanBy(Class<T> clazz, String sql, Map<String, Object> parameters);

    <T extends Dto> List<T> findBeansBy(Class<T> clazz, String sql, Map<String, Object> parameters);
}
