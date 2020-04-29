package com.legocms.web.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.vo.sys.AdminPermission;
import com.legocms.core.vo.sys.SysPermissionLangCode;
import com.legocms.core.vo.sys.SysPermissionVo;
import com.legocms.core.web.JsonResponse;
import com.legocms.core.web.ViewResponse;
import com.legocms.core.web.session.SessionController;
import com.legocms.service.sys.ISysPermissionService;
import com.legocms.web.AdminView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin/permission")
public class AdminSysPermissionController extends SessionController {

    @Autowired
    private ISysPermissionService permissionService;

    @GetMapping("/init")
    @RequiresPermissions(AdminPermission.PERMISSION)
    public ViewResponse init() {
        return ViewResponse.ok(AdminView.CMS_PERMISSION_LIST).put("langTypes", SysPermissionLangCode.ALL);
    }

    @PostMapping("/save")
    @RequiresPermissions(AdminPermission.PERMISSION_EDIT)
    public JsonResponse save(SysPermissionVo vo) {
        log.debug("save:{}", vo);
        permissionService.save(vo);
        return JsonResponse.ok();
    }

    @PostMapping("/delete")
    @RequiresPermissions(AdminPermission.PERMISSION_EDIT)
    public JsonResponse delete(String code) {
        permissionService.delete(code);
        return JsonResponse.ok();
    }

}