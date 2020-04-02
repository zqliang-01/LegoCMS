package com.legocms.core.cache;

/**
 * 缓存接口，所有缓存实现类必需扩展该接口
 */
public interface Cache {

    /**
         * 默认缓存名
     */
    String CAHCENAME = "Cache";

    /**
         * 默认session缓存时间30分钟
     */
    int SESSIONTIME = 1800;

    void setToken(String token);

    /**
         * 新增或者更新缓存
     */
    <T> void set(String key, T obj);

    /**
         * 读缓存数据
     */
    <T> T get(final String key);

    /**
         * 删除缓存数据
     */
    void clear();

}
