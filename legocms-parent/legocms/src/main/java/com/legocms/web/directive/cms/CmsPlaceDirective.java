package com.legocms.web.directive.cms;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.dto.cms.CmsPlaceInfo;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.handler.RenderHandler;
import com.legocms.service.cms.ICmsPlaceService;
import com.legocms.web.directive.ControllerTemplateDirective;

@Component
@RequiresPermissions(SysPermissionCode.PLACE)
public class CmsPlaceDirective extends ControllerTemplateDirective {

    @Autowired
    private ICmsPlaceService placeService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        CmsPlaceInfo place = placeService.findBy(handler.getString("code"));
        handler.put("place", place).render();
    }

}
