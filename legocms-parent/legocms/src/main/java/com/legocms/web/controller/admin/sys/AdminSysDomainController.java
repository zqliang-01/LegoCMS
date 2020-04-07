package com.legocms.web.controller.admin.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
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

    @PostMapping("/save")
    @RequiresPermissions(SysPermissionCode.DOMAIN_EDIT)
    public JsonResponse save(SysDomainVo vo) {
        String siteCode = getSiteCode();
        if (StringUtil.isBlank(vo.getSiteCode()) && StringUtil.isNotBlank(siteCode)) {
            vo.setSiteCode(siteCode);
        }
        BusinessException.check(StringUtil.isNotBlank(vo.getSiteCode()), "当前无管理站点，保存失败！");
        domainService.save(vo);
        return JsonResponse.ok();
    }

    @PostMapping("/delete")
    @RequiresPermissions(SysPermissionCode.DOMAIN_DELETE)
    public JsonResponse delete(String code) {
        domainService.delete(code);
        return JsonResponse.ok();
    }

}
