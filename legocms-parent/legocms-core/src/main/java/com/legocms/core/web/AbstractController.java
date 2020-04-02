package com.legocms.core.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.legocms.core.common.StringUtil;

/**
 * Controller公共组件
 */
public abstract class AbstractController {

    protected static final String KEY_PAGE_NAME = "page";
    protected static final String ERROR_PATH = "/error";

    protected HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs.getRequest();
    }

    protected boolean isOptions(HttpServletRequest request) {
        return StringUtil.equals("OPTIONS", request.getMethod());
    }

    public String getClassName() {
        return this.getClass().getName();
    }

    protected boolean isOtherError(String requestURI, Object handler) {
        if (isOptions(getRequest())) {
            return true;
        }

        if (ERROR_PATH.equals(requestURI)) {
            return true;
        }

        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }
        return false;
    }
}
