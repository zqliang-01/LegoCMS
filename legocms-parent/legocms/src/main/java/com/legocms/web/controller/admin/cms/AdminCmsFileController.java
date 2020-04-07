package com.legocms.web.controller.admin.cms;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.common.FileUtil;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.core.web.JsonResponse;
import com.legocms.core.web.ViewResponse;
import com.legocms.core.web.session.SessionController;
import com.legocms.service.cms.ICmsFileService;
import com.legocms.web.controller.admin.AdminView;

@RestController
@RequestMapping("/admin/file")
public class AdminCmsFileController extends SessionController {

    @Autowired
    private ICmsFileService fileService;

    @GetMapping("/init")
    @RequiresPermissions(SysPermissionCode.FILE)
    public ViewResponse init() {
        return ViewResponse.ok(AdminView.CMS_FILE_LIST);
    }

    @GetMapping("/save")
    @RequiresPermissions(SysPermissionCode.FILE)
    public JsonResponse save() {
        return JsonResponse.ok();
    }

    @RequestMapping("/upload")
    @RequiresPermissions(SysPermissionCode.FILE_EDIT)
    public JsonResponse uploads(@RequestParam MultipartFile[] files, HttpServletRequest request) throws IOException {
        String dir = request.getServletContext().getRealPath("/upload");
        for (MultipartFile file : files) {
            String fileType = FileUtil.getFileType(file.getOriginalFilename());
            String fileName = FileUtil.getName(fileType);
            file.transferTo(new File(dir + "/" + fileName));
        }
        return JsonResponse.ok();
    }
}
