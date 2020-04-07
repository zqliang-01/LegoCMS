package com.legocms.core.web.session;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.legocms.core.cache.Cache;
import com.legocms.core.dto.sys.SysUserInfo;
import com.legocms.core.web.AbstractController;

public class SessionController extends AbstractController {

    @Autowired
    private Cache cache;

    public void initToken(String token) {
        this.cache.setToken(token);
    }

    protected String createToken() {
        String token = uuid();
        initToken(token);
        return token;
    }

    @SuppressWarnings("unchecked")
    protected <T> T getAttribute(String key) {
        return (T) cache.get(key);
    }

    protected List<String> getPermissionCodes(String sessionKey) {
        SysUserInfo user = getAttribute(sessionKey);
        return user.getPermissions();
    }

    protected void setAttribute(String key, Object value) {
        this.cache.set(key, value);
    }

    protected void clearAttribute() {
        this.cache.clear();
    }

    protected Cache getCache() {
        return cache;
    }

    public String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
