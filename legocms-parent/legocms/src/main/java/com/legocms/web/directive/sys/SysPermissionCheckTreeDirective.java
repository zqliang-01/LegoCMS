package com.legocms.web.directive.sys;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.common.Constants;
import com.legocms.core.dto.SimpleCheckTreeInfo;
import com.legocms.data.handler.RenderHandler;
import com.legocms.service.sys.ISysPermissionService;
import com.legocms.web.directive.AbstractTemplateDirective;

@Component
@RequiresPermissions(skip = true)
public class SysPermissionCheckTreeDirective extends AbstractTemplateDirective {

    @Autowired
    private ISysPermissionService permissionService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String roleCode = handler.getString("roleCode");
        List<SimpleCheckTreeInfo> tree = permissionService.findSimpleCheckTree(roleCode, Constants.DEFAULT_LANG);
        handler.put("simpleCheckTree", tree);
    }

}
