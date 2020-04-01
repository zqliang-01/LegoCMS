package com.legocms.web;

import org.springframework.web.servlet.ModelAndView;

public class ViewResponse extends ModelAndView {

    public ViewResponse(String page) {
        super(page);
    }

    public static ViewResponse ok(String page) {
        return new ViewResponse(page);
    }

    public static ViewResponse error(String code, String msg, String page) {
        ViewResponse view = new ViewResponse(page);
        view.put("code", code);
        view.put("msg", msg);
        return view;
    }

    public ViewResponse put(String key, Object value) {
        super.getModelMap().put(key, value);
        return this;
    }
}
