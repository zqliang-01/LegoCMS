package com.legocms.web.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.vo.sys.AdminPermission;
import com.legocms.core.vo.sys.SysOrganizationVo;
import com.legocms.core.web.JsonResponse;
import com.legocms.core.web.ViewResponse;
import com.legocms.core.web.session.SessionController;
import com.legocms.service.sys.ISysOrganizationService;
import com.legocms.web.AdminView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin/organization")
public class AdminSysOrganizationController extends SessionController {

    @Autowired
    private ISysOrganizationService organizationService;

    @GetMapping("/init")
    @RequiresPermissions(AdminPermission.ORGANIZATION)
    public ViewResponse init() {
        return ViewResponse.ok(AdminView.CMS_ORGANIZATION_LIST);
    }

    @PostMapping("/save")
    @RequiresPermissions(AdminPermission.ORGANIZATION_EDIT)
    public JsonResponse save(SysOrganizationVo vo) {
        organizationService.save(vo);
        return JsonResponse.ok();
    }

    @PostMapping("/delete")
    @RequiresPermissions(AdminPermission.ORGANIZATION_EDIT)
    public JsonResponse delete(String code) {
        organizationService.delete(code);
        return JsonResponse.ok();
    }

}
