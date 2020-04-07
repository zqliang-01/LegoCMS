package com.legocms.web.directive.cms;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.dto.Page;
import com.legocms.core.dto.cms.CmsCategorySimpleInfo;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.handler.RenderHandler;
import com.legocms.service.cms.ICmsCategoryService;
import com.legocms.web.directive.ControllerTemplateDirective;

@Component
@RequiresPermissions(SysPermissionCode.CATEGORY)
public class CmsCategoryListDirective extends ControllerTemplateDirective {

    @Autowired
    private ICmsCategoryService categoryService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String name = handler.getString("name");
        String code = handler.getString("code");
        String status = handler.getString("status");
        String parentCode = handler.getString("parentCode");

        Page<CmsCategorySimpleInfo> page = categoryService.findBy(code, name, status, parentCode, pageIndex, pageSize);
        handler.put(KEY_PAGE_NAME, page).render();
    }

}
