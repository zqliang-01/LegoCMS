package com.legocms.web.controller.admin.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.core.vo.sys.SysSiteVo;
import com.legocms.core.web.JsonResponse;
import com.legocms.core.web.ViewResponse;
import com.legocms.service.sys.ISysSiteService;
import com.legocms.web.controller.admin.AdminController;
import com.legocms.web.controller.admin.AdminView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin/site")
public class AdminSysSiteController extends AdminController {

    @Autowired
    private ISysSiteService siteService;

    @GetMapping("/init")
    @RequiresPermissions(SysPermissionCode.SITE)
    public ViewResponse init() {
        log.debug("init");
        return ViewResponse.ok(AdminView.SYS_SITE_LIST);
    }

    @GetMapping("/edit/{code}")
    @RequiresPermissions(SysPermissionCode.SITE_EDIT)
    public ViewResponse edit(@PathVariable String code) {
        return ViewResponse.ok(AdminView.SYS_SITE_EDIT).put("code", code);
    }

    @PostMapping("/manage")
    @RequiresPermissions(SysPermissionCode.SITE_EDIT)
    public JsonResponse manage(String code) {
        siteService.manage(getUserCode(), code);
        refreshUser();
        return JsonResponse.ok();
    }

    @PostMapping("/save")
    @RequiresPermissions(SysPermissionCode.SITE_EDIT)
    public JsonResponse save(SysSiteVo vo) {
        siteService.save(vo);
        refreshUser();
        return JsonResponse.ok();
    }

    @PostMapping("/delete")
    @RequiresPermissions(SysPermissionCode.SITE_DELETE)
    public JsonResponse delete(String code) {
        siteService.delete(getUserCode(), code);
        refreshUser();
        return JsonResponse.ok();
    }
}