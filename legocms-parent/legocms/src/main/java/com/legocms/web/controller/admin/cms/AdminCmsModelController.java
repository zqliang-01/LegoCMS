package com.legocms.web.controller.admin.cms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.vo.cms.CmsModelVo;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.core.web.JsonResponse;
import com.legocms.core.web.ViewResponse;
import com.legocms.service.cms.ICmsModelService;
import com.legocms.web.controller.admin.AdminController;
import com.legocms.web.controller.admin.AdminView;

@RestController
@RequestMapping("/admin/model")
public class AdminCmsModelController extends AdminController {

    @Autowired
    private ICmsModelService modelService;

    @GetMapping("/init")
    @RequiresPermissions(SysPermissionCode.MODEL)
    public ViewResponse init() {
        return ViewResponse.ok(AdminView.CMS_MODEL_LIST);
    }

    @RequestMapping(params = "action=add")
    @RequiresPermissions(SysPermissionCode.MODEL_EDIT)
    public JsonResponse add(CmsModelVo model) {
        String code = modelService.add(getUserCode(), getSiteCode(), model);
        return JsonResponse.ok(code);
    }

    @RequestMapping(params = "action=modify")
    @RequiresPermissions(SysPermissionCode.MODEL_EDIT)
    public JsonResponse modify(CmsModelVo model) {
        String code = modelService.modify(getUserCode(), getSiteCode(), model);
        return JsonResponse.ok(code);
    }

    @RequestMapping(params = "action=delete")
    @RequiresPermissions(SysPermissionCode.MODEL_DELETE)
    public JsonResponse delete(String code) {
        modelService.delete(getUserCode(), code);
        return JsonResponse.ok(code);
    }
}
