package com.legocms.web.directive.sys;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.dto.Page;
import com.legocms.core.dto.sys.SysSiteInfo;
import com.legocms.core.dto.sys.SysUserInfo;
import com.legocms.data.handler.RenderHandler;
import com.legocms.service.sys.ISysSiteService;
import com.legocms.web.controller.admin.AdminView;
import com.legocms.web.directive.ControllerTemplateDirective;

@Component
@RequiresPermissions(skip = true)
public class SysSiteListDirective extends ControllerTemplateDirective {

    @Autowired
    private ISysSiteService siteService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String code = handler.getString("code");
        String name = handler.getString("name");
        SysUserInfo user = getAttribute(AdminView.USER_SESSION_KEY);
        String organization = user.getOrganization().getCode();
        String manageSite = null;
        if (user.getSite() != null) {
            manageSite = user.getSite().getCode();
        }
        Page<SysSiteInfo> page = siteService.findBy(code, name, organization, manageSite, pageIndex, pageSize);
        handler.put(KEY_PAGE_NAME, page).render();
    }

}
