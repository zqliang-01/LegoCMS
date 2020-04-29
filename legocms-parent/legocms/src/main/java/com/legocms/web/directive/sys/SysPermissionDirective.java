package com.legocms.web.directive.sys;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.dto.sys.SysPermissionDetailInfo;
import com.legocms.core.vo.sys.AdminPermission;
import com.legocms.data.handler.RenderHandler;
import com.legocms.service.sys.ISysPermissionService;
import com.legocms.web.directive.ControllerTemplateDirective;

@Component
@RequiresPermissions(AdminPermission.PERMISSION_EDIT)
public class SysPermissionDirective extends ControllerTemplateDirective {

    @Autowired
    private ISysPermissionService permissionService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        SysPermissionDetailInfo permissionInfo = permissionService.findDetailBy(handler.getString("code"));
        handler.put("permission", permissionInfo).render();
    }


}