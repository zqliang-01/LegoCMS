package com.legocms.web.directive.sys;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.dto.sys.SysPermissionInfo;
import com.legocms.core.dto.sys.SysUserInfo;
import com.legocms.data.handler.RenderHandler;
import com.legocms.service.sys.ISysPermissionService;
import com.legocms.web.AdminView;
import com.legocms.web.directive.AbstractTemplateDirective;

@Component
public class SysPermissionListDirective extends AbstractTemplateDirective {

    @Autowired
    private ISysPermissionService moduleService;

    public void execute(RenderHandler handler) throws IOException, Exception {
        String code = handler.getString("code");
        boolean menu = handler.getBoolean("menu", true).booleanValue();
        SysUserInfo user = getAttribute(AdminView.USER_SESSION_KEY);
        List<SysPermissionInfo> permissions = moduleService.findBy(user.getCode(), code, menu);
        handler.put("permissions", permissions).render();
    }
}