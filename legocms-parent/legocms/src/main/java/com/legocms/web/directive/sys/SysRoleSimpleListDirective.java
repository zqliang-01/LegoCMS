package com.legocms.web.directive.sys;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.dto.TypeCheckInfo;
import com.legocms.data.handler.RenderHandler;
import com.legocms.service.sys.ISysRoleService;
import com.legocms.web.directive.AbstractTemplateDirective;

@Component
@RequiresPermissions(skip = true)
public class SysRoleSimpleListDirective extends AbstractTemplateDirective {

    @Autowired
    private ISysRoleService roleService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String userCode = handler.getString("userCode");
        List<TypeCheckInfo> roles = roleService.findSimple(userCode);
        handler.put("roles", roles).render();
    }
}