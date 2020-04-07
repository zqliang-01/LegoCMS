package com.legocms.web.directive.cms;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.dto.Page;
import com.legocms.core.dto.cms.CmsFileInfo;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.handler.RenderHandler;
import com.legocms.service.cms.ICmsFileService;
import com.legocms.web.directive.ControllerTemplateDirective;

@Component
@RequiresPermissions(SysPermissionCode.FILE)
public class CmsFileListDirective extends ControllerTemplateDirective {

    @Autowired
    private ICmsFileService fileService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String siteCode = getSiteCode();
        String parentCode = handler.getString("parentCode");
        Page<CmsFileInfo> page = fileService.findBy(parentCode, siteCode, pageIndex, pageSize);
        handler.put(KEY_PAGE_NAME, page).render();
    }

}
