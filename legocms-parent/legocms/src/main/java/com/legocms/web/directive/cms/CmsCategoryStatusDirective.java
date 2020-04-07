package com.legocms.web.directive.cms;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.handler.RenderHandler;
import com.legocms.service.cms.ICmsSimpleTypeService;
import com.legocms.web.directive.ControllerTemplateDirective;

@Component
@RequiresPermissions(SysPermissionCode.CATEGORY)
public class CmsCategoryStatusDirective extends ControllerTemplateDirective {

    @Autowired
    private ICmsSimpleTypeService simpleTypeService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        handler.put("status", simpleTypeService.findCategoryStatus()).render();
    }

}
