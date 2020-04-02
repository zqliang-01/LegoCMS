package com.legocms.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;

import com.legocms.core.web.AbstractExceptionHandler;
import com.legocms.web.AdminView;

@ControllerAdvice("com.legocms.web.controller.admin")
public class AdminExceptionHandler extends AbstractExceptionHandler {

    @Override
    protected String getPage500() {
        return AdminView.ERROR_500;
    }

    @Override
    protected String getPageLogin() {
        return AdminView.LOGIN;
    }

    @Override
    protected String getPage403() {
        return AdminView.ERROR_403;
    }

}
