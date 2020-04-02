package com.legocms.web.interceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import com.legocms.core.web.AbstractInterceptor;

@Component
public class WebInterceptor extends AbstractInterceptor {

    @Override
    public List<String> getExcludePathPatterns() {
        return Arrays.asList("/web/", "/web/login");
    }

    @Override
    public List<String> getPathPatterns() {
        return Arrays.asList("/web/**");
    }

    @Override
    protected List<String> permissionCodes() {
        return new ArrayList<String>();
    }

    @Override
    protected boolean preprocess(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        return true;
    }
}
