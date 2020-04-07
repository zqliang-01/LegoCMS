package com.legocms.data.base.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.Type;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.legocms.core.dto.Dto;
import com.legocms.core.exception.CoreException;
import com.legocms.data.base.BaseEntity;
import com.legocms.data.base.ICommonDao;
import com.legocms.data.sql.SqlExtractor;

@Component
public class CommonDao implements ICommonDao, InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;
    private static CommonDao commonDao;

    @Override
    public <T extends BaseEntity> T findByCode(Class<T> clazz, String code) {
        T result = findByUnsureCode(clazz, code);
        CoreException.check(result != null, "未能找到对象,code:" + code + " type:" + clazz.getSimpleName());
        return result;
    }

    @Override
    public <T extends BaseEntity> T findByUnsureCode(Class<T> clazz, String code) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.where(criteriaBuilder.equal(root.get("code"), code));
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        return uniqueOrNull(query.getResultList());
    }

    @Override
    public <T extends BaseEntity> List<T> findAll(Class<T> clazz) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Dto> T findBeanBy(Class<T> clazz, String sql, Map<String, Object> parameters) {
        Session session = entityManager.unwrap(Session.class);
        NativeQuery<T> query = session.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(new AliasToBeanResultTransformer(clazz));
        Map<String, Type> columnTypes = SqlExtractor.extract(clazz, sql);
        columnTypes.forEach((columnName, type) -> query.addScalar(columnName, columnTypes.get(columnName)));
        setParameter(parameters, query);
        return uniqueOrNull(query.getResultList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Dto> List<T> findBeansBy(Class<T> clazz, String sql, Map<String, Object> parameters) {
        Session session = entityManager.unwrap(Session.class);
        NativeQuery<T> query = session.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(new AliasToBeanResultTransformer(clazz));
        Map<String, Type> columnTypes = SqlExtractor.extract(clazz, sql);
        columnTypes.forEach((columnName, type) -> query.addScalar(columnName, columnTypes.get(columnName)));
        setParameter(parameters, query);
        return query.getResultList();
    }

    public static CommonDao getCurrent() {
        return commonDao;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        CommonDao.commonDao = this;
    }

    protected static <T> T uniqueOrNull(List<T> objects) {
        int size = objects.size();
        if (size >= 2) {
            throw new CoreException("最多允许一个结果, 实际结果数: " + size);
        }
        if (size == 0) {
            return null;
        }
        return objects.get(0);
    }

    protected void setParameter(Map<String, ?> values, Query query) {
        if (values != null) {
            for (Map.Entry<String, ?> entry : values.entrySet()) {
                String paramName = entry.getKey();
                Object paramValue = entry.getValue();
                if (paramName.toLowerCase().endsWith("calendar") || paramValue instanceof Calendar) {
                    query.setParameter(paramName, (Calendar) paramValue, TemporalType.TIMESTAMP);
                }
                else {
                    query.setParameter(paramName, paramValue);
                }
            }
        }
    }

}
