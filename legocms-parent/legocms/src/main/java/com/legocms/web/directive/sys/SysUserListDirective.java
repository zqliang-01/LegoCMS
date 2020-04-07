package com.legocms.web.directive.sys;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.dto.Page;
import com.legocms.core.dto.sys.SysUserInfo;
import com.legocms.data.handler.RenderHandler;
import com.legocms.service.sys.ISysUserService;
import com.legocms.web.directive.AbstractTemplateDirective;

@Component
public class SysUserListDirective extends AbstractTemplateDirective {

    @Autowired
    private ISysUserService userService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String name = handler.getString("name");
        String code = handler.getString("code");
        Page<SysUserInfo> infos = userService.findBy(code, name, getPageIndex(), getPageSize());
        handler.put(KEY_PAGE_NAME, infos).render();
    }

}
