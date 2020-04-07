package com.legocms.web.directive.cms;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.dto.TypeInfo;
import com.legocms.data.handler.RenderHandler;
import com.legocms.service.cms.ICmsSimpleTypeService;
import com.legocms.web.directive.ControllerTemplateDirective;

@Component
@RequiresPermissions(skip = true)
public class CmsTemplateTypeDirective extends ControllerTemplateDirective {

    @Autowired
    private ICmsSimpleTypeService simpleTypeService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        List<TypeInfo> types = simpleTypeService.findTemplateType();
        handler.put("types", types).render();
    }

}
