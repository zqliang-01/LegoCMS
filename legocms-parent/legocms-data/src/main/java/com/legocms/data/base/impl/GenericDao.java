package com.legocms.data.base.impl;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.service.spi.ServiceException;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.legocms.core.exception.CoreException;
import com.legocms.data.base.BaseEntity;
import com.legocms.data.base.IGenericDao;
import com.legocms.data.handler.QueryHandler;

public class GenericDao<T extends BaseEntity> extends SimpleJpaRepository<T, Long> implements IGenericDao<T> {
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery<T> criteriaQuery;
    private Root<T> root;
    private Class<T> domainClass;

    public GenericDao(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.entityManager = em;
        this.domainClass = domainClass;
        this.criteriaBuilder = this.entityManager.getCriteriaBuilder();
        this.criteriaQuery = this.criteriaBuilder.createQuery(domainClass);
        this.root = this.criteriaQuery.from(domainClass);
    }

    protected QueryHandler<T> createQueryHandler(String sql) {
        return new QueryHandler<T>(sql, this.entityManager, this.domainClass);
    }

    protected QueryHandler<T> createQueryHandler(String sql, Class<?>... domainClasses) {
        return new QueryHandler<T>(sql, this.entityManager, domainClasses);
    }

    public List<T> findByCodes(List<String> codes) {
        CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get("code"));
        criteriaQuery.where(in.value(codes));
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public T findByCode(String code) {
        T result = findByUnsureCode(code);
        CoreException.check(result != null, "未能找到对象,code:" + code);
        return result;
    }

    public T findByUnsureCode(String code) {
        criteriaQuery.where(criteriaBuilder.equal(root.get("code"), code));
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        return uniqueOrNull(query.getResultList());
    }

    public List<T> findAllEnable() {
        criteriaQuery.where(criteriaBuilder.greaterThan(root.get("lifecycle").get("endTime"), Calendar.getInstance()));
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    protected static <T> T uniqueOrNull(List<T> objects) {
        int size = objects.size();
        if (size >= 2) {
            throw new ServiceException("最多允许一个结果, 实际结果数: " + size);
        }
        if (size == 0) {
            return null;
        }
        return objects.get(0);
    }

}
