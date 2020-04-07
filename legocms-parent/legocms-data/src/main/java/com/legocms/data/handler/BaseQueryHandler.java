
package com.legocms.data.handler;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.service.spi.ServiceException;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.Type;

import com.legocms.core.dto.Dto;
import com.legocms.data.sql.SqlExtractor;

public class BaseQueryHandler<T> {

    private EntityManager entityManager;
    private Class<?> domainClass;

    public BaseQueryHandler(EntityManager em, Class<?> domainClass) {
        this.entityManager = em;
        this.domainClass = domainClass;
    }

    @SuppressWarnings({ "hiding", "unchecked" })
    protected <T> T findUnique(String hql, Map<String, ?> values) {
        return (T) uniqueOrNull(findHQL(hql, values));
    }

    @SuppressWarnings({ "unchecked", "hiding" })
    protected <T> T findUnique(String hql, Object... values) {
        return (T) uniqueOrNull(findHQL(hql, values));
    }

    @SuppressWarnings("unchecked")
    public List<T> findHQL(String hql, Map<String, ?> values) {
        Query query = entityManager.createQuery(hql);
        setParameter(values, query);
        return query.getResultList();
    }

    @SuppressWarnings("hiding")
    private <T> T uniqueOrNull(List<T> objects) {
        int size = objects.size();
        if (size >= 2) {
            throw new ServiceException("最多允许一个结果, 实际结果数: " + size);
        }
        if (size == 0) {
            return null;
        }
        return objects.get(0);
    }

    private void setParameter(Map<String, ?> values, Query query) {
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

    protected String format(String pattern, Object... arguments) {
        return MessageFormat.format(pattern, arguments);
    }

    @SuppressWarnings("unchecked")
    public List<T> findHQL(String hql, Object... values) {
        Query query = entityManager.createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<T> findSQL(String sql, Map<String, Object> parameters) {
        Query query = entityManager.createNativeQuery(sql, domainClass);
        setParameter(parameters, query);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<T> findSQL(String sql, Object... values) {
        Query query = entityManager.createNativeQuery(sql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<T> limitedFindHQL(String hql, Map<String, ?> values, int firstResult, int maxResults) {
        Query query = entityManager.createQuery(hql);
        setParameter(values, query);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<T> limitedFindHQL(String hql, int firstResult, int maxResults, Object... values) {
        Query query = entityManager.createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    public long findCount(String hql, Map<String, ?> values) {
        Query query = entityManager.createQuery(hql);
        setParameter(values, query);
        return (Long) query.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public <B extends Dto> B findBeanBy(Class<B> clazz, String sql, Map<String, Object> parameters) {
        Session session = entityManager.unwrap(Session.class);
        NativeQuery<B> query = session.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(new AliasToBeanResultTransformer(clazz));
        Map<String, Type> columnTypes = SqlExtractor.extract(clazz, sql);
        columnTypes.forEach((columnName, type) -> query.addScalar(columnName, columnTypes.get(columnName)));
        setParameter(parameters, query);
        return uniqueOrNull(query.getResultList());
    }

    @SuppressWarnings("unchecked")
    public <B extends Dto> List<B> findBeansBy(Class<B> clazz, String sql, Map<String, Object> parameters) {
        Session session = entityManager.unwrap(Session.class);
        NativeQuery<B> query = session.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(new AliasToBeanResultTransformer(clazz));
        Map<String, Type> columnTypes = SqlExtractor.extract(clazz, sql);
        columnTypes.forEach((columnName, type) -> query.addScalar(columnName, columnTypes.get(columnName)));
        setParameter(parameters, query);
        return query.getResultList();
    }
}