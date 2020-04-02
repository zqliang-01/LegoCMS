package com.legocms.core.cache;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import com.legocms.core.common.SerializerUtil;
import com.legocms.core.web.session.RedisSession;

/**
 * redis单机缓存 采用RedisTemplate
 */
@Component("cache")
@ConditionalOnProperty(name = "spring.session.store-type", havingValue = "redis")
public class RedisCacheImpl implements RedisCache {
    private static ThreadLocal<String> currentToken = new ThreadLocal<String>();

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public <T> void saveOrUpdateCache(String key, T obj, final int liveTime) {
        redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(key.getBytes(), SerializerUtil.serialize(obj), Expiration.seconds(liveTime), RedisStringCommands.SetOption.UPSERT);
                return true;
            }
        });
    }

    @Override
    public <T> void putCache(String key, T obj) {
        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.setNX(key.getBytes(), SerializerUtil.serialize(obj));
            }
        });
    }

    @Override
    public <T> void putCacheWithExpireTime(String key, T obj, final int expireTime) {
        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx(key.getBytes(), expireTime, SerializerUtil.serialize(obj));
                return true;
            }
        });
    }

    @Override
    public <T> void putListCache(String key, List<T> objList) {
        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.setNX(key.getBytes(), SerializerUtil.serializeList(objList));
            }
        });
    }

    @Override
    public <T> void putListCacheWithExpireTime(String key, List<T> objList, int expireTime) {
        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx(key.getBytes(), expireTime, SerializerUtil.serializeList(objList));
                return true;
            }
        });

    }

    @Override
    public <T> T getCache(final String key, Class<T> targetClass) {
        byte[] result = redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                if (key == null) {
                    return null;
                }
                return connection.get(key.getBytes());
            }
        });
        if (result == null) {
            return null;
        }
        return SerializerUtil.deserialize(result, targetClass);
    }

    @Override
    public <T> List<T> getListCache(final String key, Class<T> targetClass) {
        byte[] result = redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.get(key.getBytes());
            }
        });
        if (result == null) {
            return null;
        }
        return SerializerUtil.deserializeList(result, targetClass);
    }

    /**
     * 精确删除key
     */
    @Override
    public void deleteCache(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 模糊删除key
     */
    @Override
    public void deleteCacheWithPattern(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }

    /**
     * 清空所有缓存
     */
    @Override
    public void clearCache() {
        deleteCacheWithPattern(Cache.CAHCENAME + "|*");
    }

    @Override
    public void setToken(String token) {
        currentToken.set(token);
    }

    @Override
    public <T> void set(String key, T obj) {
        RedisSession cache = getCache(currentToken.get(), RedisSession.class);
        if (cache == null) {
            cache = new RedisSession();
        }
        saveOrUpdateCache(currentToken.get(), cache.put(key, obj), Cache.SESSIONTIME);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        RedisSession cache = getCache(currentToken.get(), RedisSession.class);
        if (cache == null) {
            return null;
        }
        return (T) cache.get(key);
    }

    @Override
    public void clear() {
        deleteCache(currentToken.get());
    }

}
