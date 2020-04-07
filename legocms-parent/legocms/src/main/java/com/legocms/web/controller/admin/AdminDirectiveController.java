package com.legocms.web.controller.admin;

import java.util.List;

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
import com.legocms.core.web.session.SessionController;
import com.legocms.web.AdminView;
import com.legocms.web.directive.AbstractTemplateDirective;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin/directive")
public class AdminDirectiveController extends SessionController {

    @Autowired
    private DirectiveComponent directiveComponent;

    @Autowired
    private FastJsonHttpMessageConverter jsonMessageConverter;

    @SneakyThrows
    @RequestMapping("{action}")
    @RequiresPermissions(skip = true)
    public void action(@PathVariable String action, HttpServletRequest request, HttpServletResponse response) {
        log.debug("/admin/action:{}", action);
        AbstractTemplateDirective directive = directiveComponent.getTemplateDirectiveMap().get(action);
        RequiresPermissions permission = directive.getClass().getAnnotation(RequiresPermissions.class);
        BusinessException.check(directive != null && directive.httpEnabled(), ConstantEnum.INTERFACE_NOTFOUND_INVALID);
        BusinessException.check(permission != null, ConstantEnum.AUTHORIZATION_INVALID);

        if (!permission.skip()) {
            List<String> permissionCodes = getPermissionCodes(AdminView.USER_SESSION_KEY);
            BusinessException.check(permissionCodes.contains(permission.value()), ConstantEnum.AUTHORIZATION_INVALID);
        }

        directive.execute(jsonMessageConverter, Constants.JSON_MEDIA_TYPE, request, response);
    }
}
