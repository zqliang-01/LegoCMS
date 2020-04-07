package com.legocms.web.directive.sys;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.dto.sys.SysSiteInfo;
import com.legocms.data.handler.RenderHandler;
import com.legocms.service.sys.ISysSiteService;
import com.legocms.web.directive.ControllerTemplateDirective;

@Component
@RequiresPermissions(skip = true)
public class SysSiteDirective extends ControllerTemplateDirective {

    @Autowired
    private ISysSiteService siteService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String code = handler.getString("code");
        SysSiteInfo site = siteService.findBy(code);
        handler.put("siteInfo", site).render();
    }

}
