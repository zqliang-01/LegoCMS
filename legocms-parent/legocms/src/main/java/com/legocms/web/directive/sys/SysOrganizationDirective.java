package com.legocms.web.directive.sys;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.dto.sys.SysOrganizationDetailInfo;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.handler.RenderHandler;
import com.legocms.service.sys.ISysOrganizationService;
import com.legocms.web.directive.ControllerTemplateDirective;

@Component
@RequiresPermissions(SysPermissionCode.ORGANIZATION)
public class SysOrganizationDirective extends ControllerTemplateDirective {

    @Autowired
    private ISysOrganizationService organizationService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String code = handler.getString("code");
        SysOrganizationDetailInfo organization = organizationService.findDetailBy(code);
        handler.put("organization", organization).render();
    }

}
