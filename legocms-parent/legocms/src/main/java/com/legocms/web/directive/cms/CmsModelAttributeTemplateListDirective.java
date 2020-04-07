package com.legocms.web.directive.cms;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.vo.cms.CmsModelAttributeCode;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.handler.RenderHandler;
import com.legocms.web.directive.ControllerTemplateDirective;

@Component
@RequiresPermissions(SysPermissionCode.MODEL)
public class CmsModelAttributeTemplateListDirective extends ControllerTemplateDirective {

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String lang = getLang(handler);
        handler.put("attributeTemplates", CmsModelAttributeCode.ALL).render();
    }

}
