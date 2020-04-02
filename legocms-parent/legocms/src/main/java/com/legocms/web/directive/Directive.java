package com.legocms.web.directive;

import java.io.IOException;

import com.legocms.data.handler.RenderHandler;

public interface Directive {

    public String getName();

    public void execute(RenderHandler handler) throws IOException, Exception;
}
