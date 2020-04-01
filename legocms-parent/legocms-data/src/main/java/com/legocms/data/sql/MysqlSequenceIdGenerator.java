package com.legocms.data.sql;

import java.math.BigInteger;
import java.text.MessageFormat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.legocms.data.base.BaseEntity;

@Component
@ConditionalOnProperty(name = { "spring.jpa.database" }, havingValue = "MYSQL")
public class MysqlSequenceIdGenerator extends IdGenerator implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;

    public Long nextId(BaseEntity baseEntity) {
        String sql = MessageFormat.format("SELECT nextval({0}) FROM DUAL", new Object[] { "'idSequence'" });
        Query query = this.entityManager.createNativeQuery(sql);
        BigInteger id = (BigInteger) query.getSingleResult();
        return Long.valueOf(id.longValue());
    }

    public void afterPropertiesSet() throws Exception {
        setIdGenerator(this);
    }
}
