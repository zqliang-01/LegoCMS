package com.legocms.web.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.dto.TypeCheckInfo;
import com.legocms.core.dto.TypeInfo;
import com.legocms.core.vo.sys.SysPermissionLangCode;
import com.legocms.core.web.JsonResponse;
import com.legocms.core.web.ViewResponse;
import com.legocms.core.web.session.SessionController;
import com.legocms.web.AdminView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminInitController extends SessionController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    @RequiresPermissions("admin")
    public ViewResponse index(HttpServletRequest request) {
        log.debug("page index");
        List<TypeCheckInfo> langs = new ArrayList<TypeCheckInfo>();
        Locale locale = RequestContextUtils.getLocale(request);
        for (TypeInfo lang : SysPermissionLangCode.ALL_TYPE) {
            if (locale != null && lang.getCode().equals(locale.getLanguage())) {
                langs.add(new TypeCheckInfo(lang, true));
            }
            else {
                langs.add(new TypeCheckInfo(lang, false));
            }
        }
        return ViewResponse.ok(AdminView.INDEX).put("langTypes", langs);
    }

    @GetMapping("/login")
    public ViewResponse login() {
        log.debug("page login");
        return ViewResponse.ok(AdminView.LOGIN);
    }

    @RequiresPermissions(skip = true)
    @GetMapping("/changeLocale/{lang}")
    public ViewResponse changeLocale(@PathVariable String lang, HttpServletRequest request, HttpServletResponse response) {
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        if (null != localeResolver) {
            localeResolver.setLocale(request, response, StringUtils.parseLocaleString(lang));
        }
        return ViewResponse.redirect(AdminView.ROOT);
    }

    @RequiresPermissions(skip = true)
    @GetMapping("/message")
    public JsonResponse message(String code, HttpServletRequest request) {
        Locale locale = RequestContextUtils.getLocale(request);
        String message = messageSource.getMessage(code, null, locale);
        return JsonResponse.ok(message);
    }
}
