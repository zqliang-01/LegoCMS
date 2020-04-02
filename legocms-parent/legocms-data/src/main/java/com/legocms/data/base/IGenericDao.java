package com.legocms.data.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IGenericDao<T> extends JpaRepository<T, Long> {

    List<T> findByCodes(List<String> codes);

    T findByCode(String code);

    T findByUnsureCode(String code);

    List<T> findAll();

    List<T> findAllEnable();
}
