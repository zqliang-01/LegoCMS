package com.legocms.web.directive.sys;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.dto.sys.SysPermissionInfo;
import com.legocms.core.dto.sys.SysUserInfo;
import com.legocms.data.handler.RenderHandler;
import com.legocms.service.sys.ISysPermissionService;
import com.legocms.web.AdminView;
import com.legocms.web.directive.ControllerTemplateDirective;

@Component
@RequiresPermissions(skip = false)
public class SysPermissionListDirective extends ControllerTemplateDirective {

    @Autowired
    private ISysPermissionService permissionService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String lang = getLang(handler);
        String code = handler.getString("code");
        boolean menu = handler.getBoolean("menu", true).booleanValue();
        SysUserInfo user = getAttribute(AdminView.USER_SESSION_KEY);
        List<SysPermissionInfo> permissions = permissionService.findBy(user.getCode(), code, lang, menu);
        handler.put("permissions", permissions).render();
    }
}