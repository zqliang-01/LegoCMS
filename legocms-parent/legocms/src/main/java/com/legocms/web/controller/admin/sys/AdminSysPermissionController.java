package com.legocms.web.controller.admin.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.core.vo.sys.SysPermissionLangCode;
import com.legocms.core.vo.sys.SysPermissionVo;
import com.legocms.core.web.JsonResponse;
import com.legocms.core.web.ViewResponse;
import com.legocms.service.sys.ISysPermissionService;
import com.legocms.web.controller.admin.AdminController;
import com.legocms.web.controller.admin.AdminView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin/permission")
public class AdminSysPermissionController extends AdminController {

    @Autowired
    private ISysPermissionService permissionService;

    @GetMapping("/init")
    @RequiresPermissions(SysPermissionCode.PERMISSION)
    public ViewResponse init() {
        return ViewResponse.ok(AdminView.SYS_PERMISSION_LIST).put("langTypes", SysPermissionLangCode.ALL_TYPE);
    }

    @PostMapping("/save")
    @RequiresPermissions(SysPermissionCode.PERMISSION_EDIT)
    public JsonResponse save(SysPermissionVo vo) {
        log.debug("save:{}", vo);
        permissionService.save(vo);
        return JsonResponse.ok();
    }

    @PostMapping("/delete")
    @RequiresPermissions(SysPermissionCode.PERMISSION_DELETE)
    public JsonResponse delete(String code) {
        permissionService.delete(code);
        return JsonResponse.ok();
    }

}