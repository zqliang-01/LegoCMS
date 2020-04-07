package com.legocms.web.directive.sys;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.dto.sys.SysRoleInfo;
import com.legocms.data.handler.RenderHandler;
import com.legocms.service.sys.ISysRoleService;
import com.legocms.web.directive.ControllerTemplateDirective;

@Component
@RequiresPermissions(skip = true)
public class SysRoleDirective extends ControllerTemplateDirective {

    @Autowired
    private ISysRoleService roleService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String code = handler.getString("code");
        SysRoleInfo role = roleService.findByCode(code);
        handler.put("role", role).render();
    }

}
