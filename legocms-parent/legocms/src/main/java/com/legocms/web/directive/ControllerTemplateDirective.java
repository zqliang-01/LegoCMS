package com.legocms.web.directive;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import com.legocms.core.cache.Cache;
import com.legocms.core.common.Constants;
import com.legocms.core.common.StringUtil;
import com.legocms.core.dto.sys.SysSiteInfo;
import com.legocms.core.dto.sys.SysUserInfo;
import com.legocms.core.exception.BusinessException;
import com.legocms.data.handler.HttpParameterHandler;
import com.legocms.data.handler.RenderHandler;
import com.legocms.web.controller.admin.AdminView;

public abstract class ControllerTemplateDirective extends BaseTemplateDirective {

    @Autowired
    private Cache cache;

    protected int pageIndex;
    protected int pageSize;

    @Override
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

    protected SysUserInfo getUser() {
        return getAttribute(AdminView.USER_SESSION_KEY);
    }

    protected SysSiteInfo getSite() {
        return getAttribute(AdminView.SITE_SESSION_KEY);
    }

    protected String getSiteCode() {
        SysSiteInfo site = getSite();
        BusinessException.check(site != null, "当前无管理站点，请先选择管理站点！");
        return site.getCode();
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

    protected String getLang(RenderHandler handler) {
        String lang = handler.getLocale().getLanguage();
        if (StringUtil.isBlank(lang)) {
            return Constants.DEFAULT_LANG;
        }
        return lang;
    }
}
