package com.legocms.web.directive.cms;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.dto.cms.CmsTemplateInfo;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.handler.RenderHandler;
import com.legocms.service.cms.ICmsTemplateService;
import com.legocms.web.directive.ControllerTemplateDirective;

@Component
@RequiresPermissions(SysPermissionCode.TEMPLATE)
public class CmsTemplateDirective extends ControllerTemplateDirective {

    @Autowired
    private ICmsTemplateService templateService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        CmsTemplateInfo template = templateService.findBy(handler.getString("code"));
        handler.put("template", template).render();
    }

}
