package com.legocms.web.controller.admin.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.common.StringUtil;
import com.legocms.core.dto.sys.SysUserInfo;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.core.vo.sys.SysUserStatusCode;
import com.legocms.core.vo.sys.SysUserVo;
import com.legocms.core.web.JsonResponse;
import com.legocms.core.web.ViewResponse;
import com.legocms.service.sys.ISysUserService;
import com.legocms.web.controller.admin.AdminController;
import com.legocms.web.controller.admin.AdminView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin/user")
public class AdminSysUserController extends AdminController {

    @Autowired
    private ISysUserService userService;

    @PostMapping("/doLogin")
    public ViewResponse doLogin(String username, String password) {
        log.debug("doLogin");

        ViewResponse view = ViewResponse.ok(AdminView.LOGIN);
        if (StringUtil.isBlank(username)) {
            view.put("msg", "账号不能为空！");
            return view;
        }
        if (StringUtil.isBlank(password)) {
            view.put("msg", "密码不能为空！");
            return view;
        }

        SysUserInfo user = userService.findBy(username);
        if (user == null) {
            view.put("msg", "账号不存在！");
            return view;
        }
        if (!user.getPassword().equals(password)) {
            view.put("msg", "密码错误，登录失败！");
            return view;
        }
        if (SysUserStatusCode.Terminated.equals(user.getStatus().getCode())) {
            view.put("msg", "员工已停用！");
            return view;
        }

        setUser(user);
        setSite(user.getSite());
        return ViewResponse.redirect(AdminView.ROOT);
    }

    @GetMapping("/doLogout")
    public ViewResponse doLogout() {
        log.debug("doLogout");
        clearAttribute();
        return ViewResponse.ok(AdminView.LOGIN);
    }

    @GetMapping("/init")
    @RequiresPermissions(SysPermissionCode.USER)
    public ViewResponse init() {
        return ViewResponse.ok(AdminView.SYS_USER_LIST);
    }

    @GetMapping("/edit/{code}")
    @RequiresPermissions(SysPermissionCode.USER_EDIT)
    public ViewResponse edit(@PathVariable String code) {
        return ViewResponse.ok(AdminView.SYS_USER_EDIT).put("code", code);
    }

    @PostMapping(params = "action=active")
    @RequiresPermissions(SysPermissionCode.USER_EDIT)
    public JsonResponse active(String code) {
        userService.active(getUserCode(), code);
        return JsonResponse.ok();
    }

    @PostMapping(params = "action=invalid")
    @RequiresPermissions(SysPermissionCode.USER_EDIT)
    public JsonResponse invalid(String code) {
        userService.invalid(getUserCode(), code);
        return JsonResponse.ok();
    }

    @PostMapping(params = "action=add")
    @RequiresPermissions(SysPermissionCode.USER_EDIT)
    public JsonResponse add(SysUserVo vo) {
        vo.setSiteCode(getSiteCode());
        userService.add(getUserCode(), vo);
        refreshUser();
        return JsonResponse.ok();
    }

    @PostMapping(params = "action=modify")
    @RequiresPermissions(SysPermissionCode.USER_EDIT)
    public JsonResponse modify(SysUserVo vo) {
        vo.setSiteCode(getSiteCode());
        userService.modify(getUserCode(), vo);
        refreshUser();
        return JsonResponse.ok();
    }

    @PostMapping("/changePassword")
    @RequiresPermissions(skip = true)
    public JsonResponse changePassword(String originalPassword, String newPassword) {
        userService.changePassword(getUserCode(), getUserCode(), originalPassword, newPassword);
        return JsonResponse.ok();
    }
}
