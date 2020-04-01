package com.legocms.data.base;

import java.lang.reflect.Constructor;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import com.legocms.core.exception.CoreException;
import com.legocms.data.base.impl.GenericDao;

public class RepositoryFactoryBean<R extends JpaRepository<T, Long>, T> extends JpaRepositoryFactoryBean<R, T, Long> {

    public RepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
        return new BaseRepositoryFactory(em);
    }

    @SuppressWarnings("hiding")
    private static class BaseRepositoryFactory<T, Long> extends JpaRepositoryFactory {

        public BaseRepositoryFactory(EntityManager em) {
            super(em);
        }

        @Override
        protected SimpleJpaRepository<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
            return invokeInstance(information.getRepositoryInterface().getName(), information.getDomainType(), entityManager);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return getDaoImplClass(metadata.getRepositoryInterface().getName());
        }

        private Class<?> getDaoImplClass(String interfaceName) {
            String implName = interfaceName.substring(interfaceName.lastIndexOf(".") + 2);
            implName = interfaceName.substring(0, interfaceName.lastIndexOf(".")) + ".impl." + implName;
            try {
                Class<?> implClass = Class.forName(implName);
                if (!GenericDao.class.isAssignableFrom(implClass)) {
                    throw new CoreException("Dao实现类[" + implClass + "]没继承GenericDao");
                }
                return implClass;
            }
            catch (Exception e) {
                throw new CoreException("加载Dao实现类[" + implName + "]出错", e);
            }
        }

        @SuppressWarnings("rawtypes")
        private GenericDao invokeInstance(String interfaceName, Class domainClass, EntityManager em) {
            Class<?> implClass = getDaoImplClass(interfaceName);
            try {
                Constructor c = implClass.getConstructor(Class.class, EntityManager.class);
                return (GenericDao) c.newInstance(domainClass, em);
            }
            catch (Exception e) {
                throw new CoreException("初始化Dao实现类[" + implClass + "]出错", e);
            }
        }
    }
}
