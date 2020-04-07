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
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.core.vo.sys.SysUserStatus;
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
        if (SysUserStatus.Terminated.equals(user.getStatus().getCode())) {
            view.put("msg", "员工已停用！");
            return view;
        }

        setUser(user);
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

    @PostMapping("/active")
    @RequiresPermissions(SysPermissionCode.USER_EDIT)
    public JsonResponse active(String code) {
        userService.active(code);
        return JsonResponse.ok();
    }

    @PostMapping("/invalid")
    @RequiresPermissions(SysPermissionCode.USER_EDIT)
    public JsonResponse invalid(String code) {
        userService.invalid(code);
        return JsonResponse.ok();
    }

    @PostMapping("/save")
    @RequiresPermissions(SysPermissionCode.USER_EDIT)
    public JsonResponse save(SysUserVo vo) {
        log.debug("保存客户信息：{}", vo);
        userService.save(vo);
        refreshUser();
        return JsonResponse.ok();
    }

    @PostMapping("/changePassword")
    @RequiresPermissions(skip = true)
    public JsonResponse changePassword(String originalPassword, String newPassword, String repeatPassword) {
        BusinessException.check(repeatPassword.equals(newPassword), "重复密码不一致！");
        SysUserInfo user = userService.findBy(getUserCode());
        BusinessException.check(user.getPassword().equals(originalPassword), "原密码错误！");
        userService.changePassword(user.getCode(), newPassword);
        return JsonResponse.ok();
    }
}
