package com.legocms.web.controller.admin.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.vo.sys.SysDomainVo;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.core.web.JsonResponse;
import com.legocms.core.web.ViewResponse;
import com.legocms.service.sys.impl.SysDomainService;
import com.legocms.web.controller.admin.AdminController;
import com.legocms.web.controller.admin.AdminView;

@RestController
@RequestMapping("/admin/domain")
public class AdminSysDomainController extends AdminController {

    @Autowired
    private SysDomainService domainService;

    @GetMapping("/init")
    @RequiresPermissions(SysPermissionCode.DOMAIN)
    public ViewResponse init() {
        return ViewResponse.ok(AdminView.SYS_DOMAIN_LIST);
    }

    @GetMapping("/edit/{code}")
    @RequiresPermissions(SysPermissionCode.DOMAIN_EDIT)
    public ViewResponse edit(@PathVariable String code) {
        return ViewResponse.ok(AdminView.SYS_DOMAIN_EDIT).put("code", code);
    }

    @PostMapping(params = "action=add")
    @RequiresPermissions(SysPermissionCode.DOMAIN_EDIT)
    public JsonResponse add(SysDomainVo vo) {
        vo.setSiteCode(getSiteCode());
        domainService.add(getUserCode(), vo);
        return JsonResponse.ok();
    }

    @PostMapping(params = "action=modify")
    @RequiresPermissions(SysPermissionCode.DOMAIN_EDIT)
    public JsonResponse modify(SysDomainVo vo) {
        vo.setSiteCode(getSiteCode());
        domainService.modify(getUserCode(), vo);
        return JsonResponse.ok();
    }

    @PostMapping(params = "action=delete")
    @RequiresPermissions(SysPermissionCode.DOMAIN_DELETE)
    public JsonResponse delete(String code) {
        domainService.delete(getUserCode(), code);
        return JsonResponse.ok();
    }

}
