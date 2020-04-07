package com.legocms.web.controller.admin.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.vo.sys.SysOrganizationVo;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.core.web.JsonResponse;
import com.legocms.core.web.ViewResponse;
import com.legocms.service.sys.ISysOrganizationService;
import com.legocms.web.controller.admin.AdminController;
import com.legocms.web.controller.admin.AdminView;

@RestController
@RequestMapping("/admin/organization")
public class AdminSysOrganizationController extends AdminController {

    @Autowired
    private ISysOrganizationService organizationService;

    @GetMapping("/init")
    @RequiresPermissions(SysPermissionCode.ORGANIZATION)
    public ViewResponse init() {
        return ViewResponse.ok(AdminView.SYS_ORGANIZATION_LIST);
    }

    @PostMapping(params = "action=add")
    @RequiresPermissions(SysPermissionCode.ORGANIZATION_EDIT)
    public JsonResponse add(SysOrganizationVo vo) {
        organizationService.add(getUserCode(), vo);
        refreshUser();
        return JsonResponse.ok();
    }

    @PostMapping(params = "action=modify")
    @RequiresPermissions(SysPermissionCode.ORGANIZATION_EDIT)
    public JsonResponse modify(SysOrganizationVo vo) {
        organizationService.modify(getUserCode(), vo);
        refreshUser();
        return JsonResponse.ok();
    }

    @PostMapping(params = "action=delete")
    @RequiresPermissions(SysPermissionCode.ORGANIZATION_DELETE)
    public JsonResponse delete(String code) {
        organizationService.delete(getUserCode(), code);
        return JsonResponse.ok();
    }

}
