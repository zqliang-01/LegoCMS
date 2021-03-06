package com.legocms.web.controller.admin.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.core.web.JsonResponse;
import com.legocms.core.web.ViewResponse;
import com.legocms.service.sys.ISysRoleService;
import com.legocms.web.controller.admin.AdminController;
import com.legocms.web.controller.admin.AdminView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin/role")
public class AdminSysRoleController extends AdminController {

    @Autowired
    private ISysRoleService roleService;

    @GetMapping("/init")
    @RequiresPermissions(SysPermissionCode.ROLE)
    public ViewResponse init() {
        log.debug("init");
        return ViewResponse.ok(AdminView.SYS_ROLE_LIST);
    }

    @PostMapping(params = "action=authorize")
    @RequiresPermissions(SysPermissionCode.ROLE_AUTHORIZE)
    public JsonResponse authorize(@RequestParam(value = "permisisons[]", required = false) List<String> permisisons, String roleCode) {
        roleService.authorize(getUserCode(), roleCode, permisisons);
        refreshUser();
        return JsonResponse.ok();
    }

    @PostMapping(params = "action=add")
    @RequiresPermissions(SysPermissionCode.ROLE_EDIT)
    public JsonResponse add(String code, String name) {
        roleService.add(getUserCode(), code, name);
        return JsonResponse.ok();
    }

    @PostMapping(params = "action=modify")
    @RequiresPermissions(SysPermissionCode.ROLE_EDIT)
    public JsonResponse modify(String code, String name) {
        roleService.modify(getUserCode(), code, name);
        return JsonResponse.ok();
    }

    @PostMapping(params = "action=delete")
    @RequiresPermissions(SysPermissionCode.ROLE_DELETE)
    public JsonResponse delete(String code) {
        roleService.delete(getUserCode(), code);
        refreshUser();
        return JsonResponse.ok();
    }
}
