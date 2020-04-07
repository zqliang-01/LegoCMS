package com.legocms.web.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UrlPathHelper;

import com.legocms.core.dto.sys.SysDomainInfo;
import com.legocms.core.web.session.SessionController;
import com.legocms.service.sys.ISysDomainService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ResourceContorller extends SessionController {

    @Autowired
    private ISysDomainService domainService;

    private UrlPathHelper urlPathHelper = new UrlPathHelper();

    @GetMapping(value = "/static/**")
    public void findResource(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestPath = urlPathHelper.getLookupPathForRequest(request);
        String resourcePath = requestPath.substring(7);
        SysDomainInfo info = domainService.findByName(request.getServerName());
        log.debug("domain:{}", info);
        resourcePath = File.separator + info.getSite().getCode() + resourcePath;
        log.debug("resourcePath:{}", resourcePath);
    }
}