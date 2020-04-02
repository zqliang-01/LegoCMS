package com.legocms.web.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.legocms.core.annotation.LoginCheckAvoided;
import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.web.ViewResponse;
import com.legocms.core.web.session.SessionController;
import com.legocms.web.AdminView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin")
public class InitController extends SessionController {

    @GetMapping
    @RequiresPermissions("permission:admin")
    public ViewResponse index() {
        log.debug("page index");
        return ViewResponse.ok(AdminView.INDEX);
    }

    @LoginCheckAvoided
    @GetMapping("/login")
    public ViewResponse login() {
        log.debug("page login");
        return ViewResponse.ok(AdminView.LOGIN);
    }
}
