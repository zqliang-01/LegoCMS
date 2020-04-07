package com.legocms.core.cache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component("cache")
public class HttpCacheImpl implements Cache {

    @Override
    public void setToken(String token) { }

    @Override
    public <T> void set(String key, T obj) {
        getSession().setAttribute(key, obj);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) getSession().getAttribute(key);
    }

    @Override
    public void clear() {
        getSession().invalidate();
    }

    public HttpSession getSession() {
        return getRequest().getSession();
    }

    public HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs.getRequest();
    }
}
