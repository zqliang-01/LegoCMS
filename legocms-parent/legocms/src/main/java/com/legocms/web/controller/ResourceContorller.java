package com.legocms.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UrlPathHelper;

import com.legocms.core.common.FileUtil;
import com.legocms.core.dto.sys.SysDomainInfo;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.web.session.SessionController;
import com.legocms.data.handler.FileHelper;
import com.legocms.service.sys.ISysDomainService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ResourceContorller extends SessionController {

    @Autowired
    private ISysDomainService domainService;

    @Autowired
    private FileHelper fileHelper;

    private UrlPathHelper urlPathHelper = new UrlPathHelper();

    @GetMapping(value = "/static/**")
    public void findResource(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestPath = urlPathHelper.getLookupPathForRequest(request);
        String resourcePath = requestPath.substring(7);
        log.debug("resourcePath:{}", resourcePath);
        SysDomainInfo domain = domainService.findByName(request.getServerName());
        BusinessException.check(domain != null, "域名未配置，资源获取失败！");
        String path = FileUtil.getAbsolutePath(resourcePath, domain.getSite().getCode());
        log.debug("path:{}", path);
        fileHelper.get(response.getOutputStream(), FileUtil.getPath(path), FileUtil.getFile(path));
    }
}