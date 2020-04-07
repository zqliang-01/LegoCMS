package com.legocms.web.directive.sys;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.dto.Page;
import com.legocms.core.dto.sys.SysOperationLogInfo;
import com.legocms.data.handler.RenderHandler;
import com.legocms.service.sys.ISysOperationLogService;
import com.legocms.web.directive.ControllerTemplateDirective;

@Component
@RequiresPermissions(skip = true)
public class SysOperationLogListDirective extends ControllerTemplateDirective {

    @Autowired
    private ISysOperationLogService operationLogService;

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String userName = handler.getString("userName");
        Date start = handler.getDate("createStart");
        Date end = handler.getDate("createEnd");
        String lang = getLang(handler);
        Page<SysOperationLogInfo> page = operationLogService.findBy(userName, lang, start, end, pageIndex, pageSize);
        handler.put(KEY_PAGE_NAME, page).render();
    }

}
