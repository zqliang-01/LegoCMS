package com.legocms.web.directive.sys;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.dto.sys.SysPermissionInfo;
import com.legocms.handler.RenderHandler;
import com.legocms.service.sys.ISysPermissionService;
import com.legocms.web.directive.AbstractTemplateDirective;

@Component
public class SysModuleListDirective extends AbstractTemplateDirective {

    @Autowired
    private ISysPermissionService moduleService;

    public void execute(RenderHandler handler) throws IOException, Exception {
        String code = handler.getString("code");
        boolean menu = handler.getBoolean("menu", true).booleanValue();
        List<SysPermissionInfo> modules = moduleService.findByParent(code, menu);
        handler.put("modules", modules).render();
    }
}
