package com.legocms.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.legocms.web.ViewResponse;
import com.legocms.web.WebView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping
public class InitController {

    @GetMapping("/login")
    public ViewResponse login() {
        log.debug("login");
        return ViewResponse.ok(WebView.LOGIN);
    }

    @GetMapping("/")
    public ViewResponse index() {
        log.debug("login");
        return ViewResponse.ok(WebView.INDEX);
    }
}
