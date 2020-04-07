package com.legocms.data.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.legocms.core.dto.Page;
import com.legocms.data.base.BaseEntity;

public class QueryHandler<T extends BaseEntity> extends BaseQueryHandler<T> {
    boolean whereFlag = true;
    boolean orderFlag = true;
    boolean groupFlag = true;
    private StringBuilder sqlBuilder;
    private Map<String, Object> param;

    public QueryHandler(String sql, EntityManager em, Class<T> domainClass) {
        super(em, domainClass);
        this.sqlBuilder = new StringBuilder(" ");
        this.sqlBuilder.append(format(sql, new Object[] { domainClass.getName() }));
    }

    public QueryHandler(String sql, EntityManager em, Class<?>... domainClasses) {
        super(em, domainClasses[0]);
        this.sqlBuilder = new StringBuilder(" ");
        Object[] classNames = new Object[domainClasses.length];
        for (int i = 0; i < domainClasses.length; ++i) {
            classNames[i] = domainClasses[i].getName();
        }
        this.sqlBuilder.append(format(sql, classNames));
    }

    public QueryHandler<T> condition(String condition) {
        if (this.whereFlag) {
            this.whereFlag = false;
            this.sqlBuilder.append(" WHERE ");
        }
        else {
            this.sqlBuilder.append(" AND ");
        }
        this.sqlBuilder.append(condition);
        return this;
    }

    public QueryHandler<T> order(String sqlString) {
        if (this.orderFlag) {
            this.orderFlag = false;
            append(" ORDER BY ");
        }
        else {
            this.sqlBuilder.append(",");
        }
        this.sqlBuilder.append(sqlString);
        return this;
    }

    public QueryHandler<T> group(String sqlString) {
        if (this.groupFlag) {
            this.groupFlag = false;
            this.sqlBuilder.append(" GROUP BY ");
        }
        else {
            this.sqlBuilder.append(",");
        }
        this.sqlBuilder.append(sqlString);
        return this;
    }

    public QueryHandler<T> append(String sqlString) {
        this.sqlBuilder.append(" ");
        this.sqlBuilder.append(sqlString);
        return this;
    }

    public QueryHandler<T> setParameter(String key, Object value) {
        if (this.param == null) {
            this.param = new HashMap<String, Object>();
        }
        this.param.put(key, value);
        return this;
    }

    public String getCountSql() {
        String sql = this.sqlBuilder.toString();
        sql = sql.substring(sql.toUpperCase().indexOf(" FROM "));
        int orderIndex = sql.toUpperCase().indexOf(" ORDER BY ");
        if (-1 != orderIndex) {
            sql = sql.substring(0, orderIndex);
        }
        return "SELECT COUNT(*) " + sql;
    }

    public List<T> findList() {
        return findHQL(this.sqlBuilder.toString(), this.param);
    }

    public Page<T> findPage(int pageIndex, int pageSize) {
        int firstResult = (pageIndex - 1) * pageSize;
        List<T> result = limitedFindHQL(this.sqlBuilder.toString(), this.param, firstResult, pageSize);
        long count = findCount(getCountSql(), this.param);
        return new Page<T>(result, pageIndex, pageSize, count);
    }

    public List<T> findSqlList() {
        return findSQL(this.sqlBuilder.toString(), this.param);
    }

    public T findUnique() {
        return findUnique(this.sqlBuilder.toString(), this.param);
    }
}
