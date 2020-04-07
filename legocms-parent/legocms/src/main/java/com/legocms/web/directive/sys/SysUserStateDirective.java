package com.legocms.web.directive.sys;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.dto.TypeInfo;
import com.legocms.data.handler.RenderHandler;
import com.legocms.service.sys.ISysSimpleTypeService;
import com.legocms.web.directive.AbstractTemplateDirective;

@Component
@RequiresPermissions(skip = true)
public class SysUserStateDirective extends AbstractTemplateDirective {

    @Autowired
    private ISysSimpleTypeService simpleTypeService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        List<TypeInfo> userStatus = simpleTypeService.findUserStatus();
        handler.put("userStatus", userStatus).render();
    }

}
