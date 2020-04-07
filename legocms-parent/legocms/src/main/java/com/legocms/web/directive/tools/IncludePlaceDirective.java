package com.legocms.web.directive.tools;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.component.PlaceComponent;
import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.data.handler.RenderHandler;
import com.legocms.web.controller.admin.AdminView;
import com.legocms.web.directive.ControllerTemplateDirective;

@Component
@RequiresPermissions(skip = true)
public class IncludePlaceDirective extends ControllerTemplateDirective {

    @Autowired
    private PlaceComponent placeComponent;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String code = handler.getString("code");
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put(AdminView.SITE_SESSION_KEY, getSite());
        param.put(AdminView.USER_SESSION_KEY, getUser());
        placeComponent.generateStringByWriter(handler.getWriter(), code, param);
    }
}
