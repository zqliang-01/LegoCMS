package com.legocms.core.web.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RedisSession implements Serializable {

    private static final long serialVersionUID = -9042213481080543182L;

    private Map<String, Object> map = new HashMap<String, Object>();

    public RedisSession put(String key, Object value) {
        map.put(key, value);
        return this;
    }

    public Object get(Object key) {
        return map.get(key);
    }
}
