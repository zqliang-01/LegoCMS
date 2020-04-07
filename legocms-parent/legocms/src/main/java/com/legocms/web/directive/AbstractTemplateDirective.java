package com.legocms.web.directive;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import com.legocms.core.cache.Cache;
import com.legocms.data.handler.HttpParameterHandler;

public abstract class AbstractTemplateDirective extends BaseTemplateDirective {

    @Autowired
    private Cache cache;

    private int pageIndex;
    private int pageSize;

    public void execute(HttpMessageConverter<Object> httpMessageConverter, MediaType mediaType, HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        HttpParameterHandler handler = new HttpParameterHandler(httpMessageConverter, mediaType, request, response);
        this.pageIndex = handler.getInteger("current", 1);
        this.pageSize = handler.getInteger("pageSize", 10);
        execute(handler);
        handler.render();
    }

    @SuppressWarnings("unchecked")
    protected <T> T getAttribute(String key) {
        return (T) cache.get(key);
    }

    protected void setAttribute(String key, Object value) {
        this.cache.set(key, value);
    }

    protected int getPageIndex() {
        return pageIndex;
    }

    protected int getPageSize() {
        return pageSize;
    }

    public boolean httpEnabled() {
        return true;
    }
}
