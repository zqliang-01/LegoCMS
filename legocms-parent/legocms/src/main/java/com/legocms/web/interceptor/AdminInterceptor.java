package com.legocms.web.interceptor;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import com.legocms.core.common.ConstantEnum;
import com.legocms.core.dto.sys.SysUserInfo;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.web.AbstractInterceptor;
import com.legocms.web.controller.admin.AdminView;

@Component
public class AdminInterceptor extends AbstractInterceptor {

    @Override
    public List<String> getExcludePathPatterns() {
        return Arrays.asList("/admin/login", "/admin/user/doLogin", "/admin/user/doLogout");
    }

    @Override
    public List<String> getPathPatterns() {
        return Arrays.asList("/admin/**", "/admin");
    }

    @Override
    protected boolean preprocess(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        BusinessException.check(getAttribute(AdminView.USER_SESSION_KEY) != null, ConstantEnum.SESSION_INVALID);
        return true;
    }

    @Override
    protected List<String> permissionCodes() {
        SysUserInfo user = getAttribute(AdminView.USER_SESSION_KEY);
        return user.getPermissions();
    }

}
