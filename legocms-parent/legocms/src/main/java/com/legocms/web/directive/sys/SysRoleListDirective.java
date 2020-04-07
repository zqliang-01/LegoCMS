package com.legocms.web.directive.sys;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.dto.Page;
import com.legocms.core.dto.sys.SysRoleInfo;
import com.legocms.data.handler.RenderHandler;
import com.legocms.service.sys.ISysRoleService;
import com.legocms.web.directive.ControllerTemplateDirective;

@Component
@RequiresPermissions(skip = true)
public class SysRoleListDirective extends ControllerTemplateDirective {

    @Autowired
    private ISysRoleService roleService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String code = handler.getString("code");
        String name = handler.getString("name");
        Page<SysRoleInfo> page = roleService.findBy(code, name, getPageIndex(), getPageSize());
        handler.put(KEY_PAGE_NAME, page).render();
    }

}
