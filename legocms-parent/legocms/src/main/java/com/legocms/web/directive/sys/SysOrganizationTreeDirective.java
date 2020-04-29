package com.legocms.web.directive.sys;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.data.handler.RenderHandler;
import com.legocms.service.sys.ISysOrganizationService;
import com.legocms.web.directive.AbstractTemplateDirective;

@Component
@RequiresPermissions(skip = true)
public class SysOrganizationTreeDirective extends AbstractTemplateDirective {

    @Autowired
    private ISysOrganizationService organizationService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        List<SimpleTreeInfo> simpleTree = organizationService.findSimpleTree();
        handler.put("simpleTree", simpleTree).render();
    }

}