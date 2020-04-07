package com.legocms.web.controller.admin.cms;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.legocms.component.TemplateComponent;
import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.common.StringUtil;
import com.legocms.core.dto.sys.SysSiteInfo;
import com.legocms.core.exception.BusinessException;
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

    @Autowired
    private TemplateComponent templateComponent;

    @GetMapping("/init")
    @RequiresPermissions(SysPermissionCode.TEMPLATE)
    public ViewResponse init() {
        return ViewResponse.ok(AdminView.CMS_TEMPLATE_LIST);
    }

    @PostMapping("/save")
    @RequiresPermissions(SysPermissionCode.TEMPLATE_EDIT)
    public JsonResponse save(CmsTemplateVo vo) {
        SysSiteInfo site = getAttribute(AdminView.SITE_SESSION_KEY);
        if (site != null) {
            vo.setSiteCode(site.getCode());
        }
        BusinessException.check(StringUtil.isNotBlank(vo.getSiteCode()), "当前无管理站点，保存失败！");
        templateService.save(vo);
        return JsonResponse.ok();
    }

    @PostMapping("/delete")
    @RequiresPermissions(SysPermissionCode.TEMPLATE_DELETE)
    public JsonResponse delete(String code) {
        templateService.delete(code);
        return JsonResponse.ok();
    }

    @ResponseBody
    @GetMapping(value = "/find/{code}", produces = MediaType.TEXT_HTML_VALUE)
    @RequiresPermissions(SysPermissionCode.TEMPLATE_EDIT)
    public String findTemplate(@PathVariable String code) {
        HashMap<String, Object> input = new HashMap<String, Object>();
        input.put(AdminView.USER_SESSION_KEY, getUser());
        input.put(AdminView.SITE_SESSION_KEY, getSite());
        String output = templateComponent.generateStringByTemplate(code, input);
        return output;
    }
}
