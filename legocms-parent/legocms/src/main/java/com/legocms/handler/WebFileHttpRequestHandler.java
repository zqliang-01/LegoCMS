package com.legocms.handler;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.ResourceRegionHttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.accept.PathExtensionContentNegotiationStrategy;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.util.UrlPathHelper;

import com.legocms.core.common.Constants;
import com.legocms.core.common.FileUtil;
import com.legocms.core.common.StringUtil;
import com.legocms.core.dto.cms.CmsFileInfo;
import com.legocms.core.dto.sys.SysDomainInfo;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.web.StaticFileResource;
import com.legocms.data.handler.FileHelper;
import com.legocms.service.cms.ICmsFileService;
import com.legocms.service.sys.ISysDomainService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("webfileServlet")
public class WebFileHttpRequestHandler extends ResourceHttpRequestHandler {

    @Nullable
    private PathExtensionContentNegotiationStrategy contentNegotiationStrategy;

    @Autowired
    private ISysDomainService domainService;

    @Autowired
    private ICmsFileService fileService;

    @Autowired
    private FileHelper fileHelper;

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.handleRequest(request, response);
    }

    @Override
    protected Resource getResource(HttpServletRequest request) throws IOException {
        String resourcePath = getUrlPathHelper().getLookupPathForRequest(request);
        log.debug("resourcePath:{}", resourcePath);
        SysDomainInfo domain = getDomain(request.getServerName());
        BusinessException.check(domain != null, "域名未配置，资源获取失败！");
        String path = FileUtil.getAbsolutePath(domain.getPath() + resourcePath, domain.getSite().getCode());
        CmsFileInfo file = fileService.findByPath(resourcePath, domain.getSite().getCode());
        if (file == null) {
            return null;
        }
        InputStream inputStream = fileHelper.get(FileUtil.getPath(path), FileUtil.getFile(path));
        Resource resource = new StaticFileResource(inputStream, file);
        if (resource.exists()) {
            if (resource.isReadable()) {
                return resource;
            }
        }
        return null;
    }

    private SysDomainInfo getDomain(String serverName) {
        SysDomainInfo domain = domainService.findByName(serverName);
        if (null == domain) {
            int index;
            if (StringUtil.isNotBlank(serverName)
                    && (index = serverName.indexOf(Constants.DOT)) > 0
                    && index != serverName.lastIndexOf(Constants.DOT)) {
                domain = getDomain(serverName.substring(index + 1));
            }
        }
        return domain;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setUrlPathHelper(new UrlPathHelper());
        if (null == getResourceHttpMessageConverter()) {
            setResourceHttpMessageConverter(new ResourceHttpMessageConverter());
        }
        if (null == getResourceRegionHttpMessageConverter()) {
            setResourceRegionHttpMessageConverter(new ResourceRegionHttpMessageConverter());
        }
        this.contentNegotiationStrategy = initContentNegotiationStrategy();
    }
}