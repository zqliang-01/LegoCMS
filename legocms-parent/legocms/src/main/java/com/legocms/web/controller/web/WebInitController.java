package com.legocms.web.controller.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.web.session.SessionController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/web")
public class WebInitController extends SessionController {

    @GetMapping
    @ResponseBody
    @RequiresPermissions(skip = true)
    public String index() {
        log.debug("page index");
        return "<html><body style='background: aliceblue;'>hello world! gigi</body></html>";
    }

}
