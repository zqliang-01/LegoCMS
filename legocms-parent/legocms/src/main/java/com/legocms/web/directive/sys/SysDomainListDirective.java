package com.legocms.web.directive.sys;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.dto.Page;
import com.legocms.core.dto.sys.SysDomainInfo;
import com.legocms.data.handler.RenderHandler;
import com.legocms.service.sys.ISysDomainService;
import com.legocms.web.directive.ControllerTemplateDirective;

@Component
@RequiresPermissions(skip = true)
public class SysDomainListDirective extends ControllerTemplateDirective {

    @Autowired
    private ISysDomainService domainService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String code = handler.getString("code");
        String name = handler.getString("name");
        String siteCode = getSiteCode();
        Page<SysDomainInfo> page = domainService.findBy(code, name, siteCode, pageIndex, pageSize);
        handler.put(KEY_PAGE_NAME, page).render();
    }

}
