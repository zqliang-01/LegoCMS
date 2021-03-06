package com.legocms.web.directive.sys;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.dto.TypeInfo;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.handler.RenderHandler;
import com.legocms.service.sys.ISysSimpleTypeService;
import com.legocms.web.directive.ControllerTemplateDirective;

@Component
@RequiresPermissions(SysPermissionCode.ORGANIZATION)
public class SysOrganizationStatusDirective extends ControllerTemplateDirective {

    @Autowired
    private ISysSimpleTypeService simpleTypeService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        List<TypeInfo> status = simpleTypeService.findOrganizationStatus();
        handler.put("status", status).render();
    }

}
