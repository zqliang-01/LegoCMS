package com.legocms.web.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.legocms.component.DirectiveComponent;
import com.legocms.core.common.ConstantEnum;
import com.legocms.core.common.Constants;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.web.session.SessionController;
import com.legocms.web.directive.ControllerTemplateDirective;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/directive")
public class DirectiveController extends SessionController {

    @Autowired
    private DirectiveComponent directiveComponent;

    @Autowired
    protected MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    @SneakyThrows
    @RequestMapping("{action}")
    public void api(@PathVariable String action, HttpServletRequest request, HttpServletResponse response) {
        log.debug("api:{}", action);
        ControllerTemplateDirective directive = directiveComponent.getTemplateDirectiveMap().get(action);
        BusinessException.check(directive != null && directive.httpEnabled(), ConstantEnum.INTERFACE_NOTFOUND_INVALID);
        directive.execute(mappingJackson2HttpMessageConverter, Constants.JSON_MEDIA_TYPE, request, response);
    }
}
