package com.legocms.web.controller.admin.cms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.vo.cms.CmsPlaceVo;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.core.web.JsonResponse;
import com.legocms.core.web.ViewResponse;
import com.legocms.service.cms.ICmsPlaceService;
import com.legocms.web.controller.admin.AdminController;
import com.legocms.web.controller.admin.AdminView;

@RestController
@RequestMapping("/admin/place")
public class AdminCmsPlaceController extends AdminController {

    @Autowired
    private ICmsPlaceService templateService;

    @GetMapping("/init")
    @RequiresPermissions(SysPermissionCode.PLACE)
    public ViewResponse init() {
        return ViewResponse.ok(AdminView.CMS_PLACE_LIST);
    }

    @PostMapping(params = "action=add")
    @RequiresPermissions(SysPermissionCode.PLACE_EDIT)
    public JsonResponse add(CmsPlaceVo vo) {
        vo.setSiteCode(getSiteCode());
        return JsonResponse.ok(templateService.add(getUserCode(), vo));
    }

    @PostMapping(params = "action=modify")
    @RequiresPermissions(SysPermissionCode.PLACE_EDIT)
    public JsonResponse modify(CmsPlaceVo vo) {
        vo.setSiteCode(getSiteCode());
        return JsonResponse.ok(templateService.modify(getUserCode(), vo));
    }

    @PostMapping(params = "action=delete")
    @RequiresPermissions(SysPermissionCode.PLACE_DELETE)
    public JsonResponse delete(String code) {
        templateService.delete(getUserCode(), code);
        return JsonResponse.ok();
    }
}