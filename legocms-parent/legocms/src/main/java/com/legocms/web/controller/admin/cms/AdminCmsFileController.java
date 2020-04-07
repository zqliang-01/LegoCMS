package com.legocms.web.controller.admin.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.common.StringUtil;
import com.legocms.core.vo.cms.CmsFileTypeCode;
import com.legocms.core.vo.cms.CmsFileVo;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.core.web.JsonResponse;
import com.legocms.core.web.ViewResponse;
import com.legocms.service.cms.ICmsFileService;
import com.legocms.web.controller.admin.AdminController;
import com.legocms.web.controller.admin.AdminView;

@RestController
@RequestMapping("/admin/file")
public class AdminCmsFileController extends AdminController {

    @Autowired
    private ICmsFileService fileService;

    @GetMapping("/init")
    @RequiresPermissions(SysPermissionCode.FILE)
    public ViewResponse init() {
        return ViewResponse.ok(AdminView.CMS_FILE_LIST);
    }

    @GetMapping("/edit/{code}")
    @RequiresPermissions(SysPermissionCode.FILE_EDIT)
    public ViewResponse edit(@PathVariable String code) {
        return ViewResponse.ok(AdminView.CMS_FILE_EDIT).put("code", code);
    }

    @PostMapping("/save")
    @RequiresPermissions(SysPermissionCode.FILE_EDIT)
    public JsonResponse save(CmsFileVo vo) {
        vo.setSiteCode(getSiteCode());
        ByteArrayInputStream ins = null;
        if (StringUtil.isNotBlank(vo.getContent())) {
            ins = new ByteArrayInputStream(vo.getContent().getBytes());
        }
        fileService.save(vo, ins);
        return JsonResponse.ok();
    }

    @PostMapping("/upload")
    @RequiresPermissions(SysPermissionCode.FILE_EDIT)
    public JsonResponse uploads(@RequestParam MultipartFile file, String parentCode, HttpServletRequest request) throws IOException {
        CmsFileVo vo = new CmsFileVo();
        vo.setName(file.getOriginalFilename());
        vo.setParentCode(parentCode);
        vo.setSiteCode(getSiteCode());
        vo.setType(CmsFileTypeCode.FILE);
        fileService.save(vo, file.getInputStream());
        return JsonResponse.ok();
    }

    @PostMapping("/delete")
    @RequiresPermissions(SysPermissionCode.FILE_DELETE)
    public JsonResponse delete(String code) {
        fileService.delete(code, getSiteCode());
        return JsonResponse.ok();
    }

    @PostMapping("/synchronize")
    @RequiresPermissions(SysPermissionCode.FILE_EDIT)
    public JsonResponse synchronize(String parentCode) {
        fileService.synchronizeDirectory(parentCode, getSiteCode());
        return JsonResponse.ok();
    }
}
