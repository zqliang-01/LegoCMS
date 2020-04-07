package com.legocms.web.controller.admin.sys;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.core.web.ViewResponse;
import com.legocms.web.controller.admin.AdminController;
import com.legocms.web.controller.admin.AdminView;

@RestController
@RequestMapping("/admin/log")
public class AdminSysOperationLogController extends AdminController {

    @GetMapping("/init")
    @RequiresPermissions(SysPermissionCode.LOG)
    public ViewResponse init() {
        return ViewResponse.ok(AdminView.SYS_LOG_LIST);
    }

}
