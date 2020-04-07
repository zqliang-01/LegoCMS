package com.legocms.web.directive.cms;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.dto.cms.CmsCategoryInfo;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.handler.RenderHandler;
import com.legocms.service.cms.ICmsCategoryService;
import com.legocms.web.directive.ControllerTemplateDirective;

@Component
@RequiresPermissions(SysPermissionCode.CATEGORY)
public class CmsCategoryDirective extends ControllerTemplateDirective {

    @Autowired
    private ICmsCategoryService categoryService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String code = handler.getString("code");
        CmsCategoryInfo info = categoryService.findInfoBy(code);
        handler.put("category", info).render();
    }
}