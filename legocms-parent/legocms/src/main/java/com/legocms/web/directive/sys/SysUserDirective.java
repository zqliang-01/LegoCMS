package com.legocms.web.directive.sys;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.dto.sys.SysUserDetailInfo;
import com.legocms.data.handler.RenderHandler;
import com.legocms.service.sys.ISysUserService;
import com.legocms.web.directive.ControllerTemplateDirective;

@Component
@RequiresPermissions(skip = true)
public class SysUserDirective extends ControllerTemplateDirective {

    @Autowired
    private ISysUserService userService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String code = handler.getString("code");
        SysUserDetailInfo userInfo = userService.findDetail(code);
        handler.put("userInfo", userInfo).render();
    }

}
