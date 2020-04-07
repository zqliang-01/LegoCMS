package com.legocms.web.controller.admin.cms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.dto.TypeInfo;
import com.legocms.core.vo.cms.CmsCategoryVo;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.core.web.JsonResponse;
import com.legocms.core.web.ViewResponse;
import com.legocms.service.cms.ICmsCategoryService;
import com.legocms.web.controller.admin.AdminController;
import com.legocms.web.controller.admin.AdminView;

@RestController
@RequestMapping("/admin/category")
public class AdminCmsCategoryController extends AdminController {

    @Autowired
    private ICmsCategoryService categroyService;

    @GetMapping("/init")
    @RequiresPermissions(SysPermissionCode.CATEGORY)
    public ViewResponse init() {
        return ViewResponse.ok(AdminView.CMS_CATEGORY_LIST);
    }

    @GetMapping("/edit/{code}/{parentCode}")
    @RequiresPermissions(SysPermissionCode.CATEGORY_EDIT)
    public ViewResponse viewEdit(@PathVariable String code, @PathVariable String parentCode) {
        TypeInfo parent = categroyService.findByCode(parentCode);
        return ViewResponse.ok(AdminView.CMS_CATEGORY_EDIT).put("parent", parent).put("code", code);
    }

    @PostMapping(params = "action=add")
    @RequiresPermissions(SysPermissionCode.CATEGORY_EDIT)
    public JsonResponse add(CmsCategoryVo vo) {
        return JsonResponse.ok(categroyService.add(getUserCode(), getSiteCode(), vo));
    }

    @PostMapping(params = "action=modify")
    @RequiresPermissions(SysPermissionCode.CATEGORY_EDIT)
    public JsonResponse modify(CmsCategoryVo vo) {
        return JsonResponse.ok(categroyService.modify(getUserCode(), getSiteCode(), vo));
    }
}
