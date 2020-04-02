package com.legocms.core.web.session;

import java.util.List;

import org.springframework.web.servlet.HandlerInterceptor;

public interface ISessionInterceptor extends HandlerInterceptor {

    List<String> getExcludePathPatterns();

    List<String> getPathPatterns();
}
