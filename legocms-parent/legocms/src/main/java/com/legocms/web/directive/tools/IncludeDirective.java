package com.legocms.web.directive.tools;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.component.TemplateComponent;
import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.data.handler.RenderHandler;
import com.legocms.handler.TemplateDirectiveHandler;
import com.legocms.web.directive.ControllerTemplateDirective;

@Component
@RequiresPermissions(skip = true)
public class IncludeDirective extends ControllerTemplateDirective {

    @Autowired
    private TemplateComponent templateComponent;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String code = handler.getString("code");
        if (handler instanceof TemplateDirectiveHandler) {
            TemplateDirectiveHandler templateHandler = (TemplateDirectiveHandler) handler;
            templateHandler.getEnvironment().include(templateComponent.getTemplate(code));
        }
    }

}
