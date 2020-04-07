package com.legocms.web.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.legocms.component.DirectiveComponent;
import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.common.ConstantEnum;
import com.legocms.core.common.Constants;
import com.legocms.core.exception.BusinessException;
import com.legocms.web.directive.ControllerTemplateDirective;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin/directive")
public class AdminDirectiveController extends AdminController {

    @Autowired
    private DirectiveComponent directiveComponent;

    @Autowired
    private FastJsonHttpMessageConverter jsonMessageConverter;

    @SneakyThrows
    @RequestMapping("{action}")
    @RequiresPermissions(skip = true)
    public void action(@PathVariable String action, HttpServletRequest request, HttpServletResponse response) {
        log.debug("/admin/action:{}", action);
        ControllerTemplateDirective directive = directiveComponent.getTemplateDirectiveMap().get(action);

        BusinessException.check(directive != null && directive.httpEnabled(), ConstantEnum.INTERFACE_NOTFOUND_INVALID);
        RequiresPermissions permission = directive.getClass().getAnnotation(RequiresPermissions.class);
        BusinessException.check(permission != null, ConstantEnum.AUTHORIZATION_INVALID);
        BusinessException.check(permission.skip() || getPermissionCodes().contains(permission.value()), ConstantEnum.AUTHORIZATION_INVALID);

        directive.execute(jsonMessageConverter, Constants.JSON_MEDIA_TYPE, request, response);
    }
}
