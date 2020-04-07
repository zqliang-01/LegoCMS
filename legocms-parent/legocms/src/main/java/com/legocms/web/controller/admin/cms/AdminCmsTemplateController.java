package com.legocms.web.controller.admin.cms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.common.StringUtil;
import com.legocms.core.vo.cms.CmsTemplateVo;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.core.web.JsonResponse;
import com.legocms.core.web.ViewResponse;
import com.legocms.service.cms.ICmsTemplateService;
import com.legocms.web.controller.admin.AdminController;
import com.legocms.web.controller.admin.AdminView;

@RestController
@RequestMapping("/admin/template")
public class AdminCmsTemplateController extends AdminController {

    @Autowired
    private ICmsTemplateService templateService;

    @GetMapping("/init")
    @RequiresPermissions(SysPermissionCode.TEMPLATE)
    public ViewResponse init() {
        return ViewResponse.ok(AdminView.CMS_TEMPLATE_LIST);
    }

    @PostMapping("/save")
    @RequiresPermissions(SysPermissionCode.TEMPLATE_EDIT)
    public JsonResponse save(CmsTemplateVo vo) {
        if (vo.getSite() == null || StringUtil.isBlank(vo.getSite().getCode())) {
            vo.setSite(getSite());
        }
        templateService.save(vo);
        return JsonResponse.ok();
    }

    @PostMapping("/delete")
    @RequiresPermissions(SysPermissionCode.TEMPLATE_DELETE)
    public JsonResponse delete(String code) {
        templateService.delete(code);
        return JsonResponse.ok();
    }

}
