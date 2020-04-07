package com.legocms.core.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.web.method.HandlerMethod;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.common.ConstantEnum;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.web.session.ISessionInterceptor;
import com.legocms.core.web.session.SessionController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractInterceptor extends SessionController implements ISessionInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.debug("URI: {}, SessionId:{}", requestURI, request.getSession().getId());
        if (isOtherError(requestURI, handler)) {
            return true;
        }

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Object bean = handlerMethod.getBean();
            if (bean instanceof BasicErrorController) {
                return true;
            }
            AbstractController controller = (AbstractController) bean;
            Map<?, ?> params = request.getParameterMap();
            log.debug("Controller: {}", controller.getClassName());
            log.debug("method: {}", handlerMethod.getMethod().getName());
            log.debug("Param: {}", params);
            if (preprocess(request, response, handlerMethod)) {
                checkPermission(handlerMethod);
                return true;
            }
        }
        return true;
    }

    private void checkPermission(HandlerMethod handlerMethod) {
        if (handlerMethod.hasMethodAnnotation(RequiresPermissions.class)) {
            RequiresPermissions permission = handlerMethod.getMethodAnnotation(RequiresPermissions.class);
            if (!permissionCodes().contains(permission.value())) {
                throw new BusinessException(ConstantEnum.AUTHORIZATION_INVALID);
            }
        }
    }

    protected abstract List<String> permissionCodes();

    protected abstract boolean preprocess(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler);

    @Override
    public List<String> getExcludePathPatterns() {
        return new ArrayList<String>();
    }
}
