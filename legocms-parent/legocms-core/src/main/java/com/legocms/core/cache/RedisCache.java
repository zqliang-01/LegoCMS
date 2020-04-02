package com.legocms.core.cache;

import java.util.List;

public interface RedisCache extends Cache {

    /**
         * 包括或者更新缓存
     */
    <T> void saveOrUpdateCache(String key, T obj, final int liveTime);

    <T> void putCache(String key, T obj);

    /**
     *
     * @param key 键值
     * @param obj 缓存对象
     * @param expireTime 秒
     */
    <T> void putCacheWithExpireTime(String key, T obj, final int expireTime);

    <T> void putListCache(String key, List<T> objList);

    <T> void putListCacheWithExpireTime(String key, List<T> objList, final int expireTime);

    <T> T getCache(final String key, Class<T> targetClass);

    <T> List<T> getListCache(final String key, Class<T> targetClass);

    /**
         * 精确删除key
     *
     * @param key
     */
    void deleteCache(String key);

    /**
         * 模糊删除key
     *
     * @param pattern
     */
    void deleteCacheWithPattern(String pattern);

    /**
         * 清空所有缓存
     */
    void clearCache();
}
