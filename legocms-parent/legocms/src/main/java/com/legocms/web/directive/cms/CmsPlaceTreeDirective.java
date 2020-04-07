package com.legocms.web.directive.cms;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.data.handler.RenderHandler;
import com.legocms.service.cms.ICmsPlaceService;
import com.legocms.web.directive.ControllerTemplateDirective;

@Component
@RequiresPermissions(skip = true)
public class CmsPlaceTreeDirective extends ControllerTemplateDirective {

    @Autowired
    private ICmsPlaceService placeService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        List<SimpleTreeInfo> tree = placeService.findSimpleTree();
        handler.put("simpleTree", tree).render();
    }

}
