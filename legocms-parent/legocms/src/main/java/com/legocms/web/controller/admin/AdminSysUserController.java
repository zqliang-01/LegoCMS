package com.legocms.web.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.legocms.core.annotation.RequiresPermissions;
import com.legocms.core.common.StringUtil;
import com.legocms.core.dto.sys.SysUserInfo;
import com.legocms.core.vo.sys.AdminPermission;
import com.legocms.core.vo.sys.SysUserVo;
import com.legocms.core.web.JsonResponse;
import com.legocms.core.web.ViewResponse;
import com.legocms.core.web.session.SessionController;
import com.legocms.service.sys.ISysUserService;
import com.legocms.web.AdminView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin/user")
public class AdminSysUserController extends SessionController {

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

        setAttribute(AdminView.USER_SESSION_KEY, user);
        return ViewResponse.redirect(AdminView.ROOT);
    }

    @GetMapping("/doLogout")
    public ViewResponse doLogout() {
        log.debug("doLogout");
        clearAttribute();
        return ViewResponse.ok(AdminView.LOGIN);
    }

    @GetMapping("/init")
    @RequiresPermissions(AdminPermission.USER)
    public ViewResponse init() {
        return ViewResponse.ok(AdminView.CMS_USER_LIST);
    }

    @GetMapping("/edit/{code}")
    @RequiresPermissions(AdminPermission.USER_EDIT)
    public ViewResponse edit(@PathVariable String code) {
        return ViewResponse.ok(AdminView.CMS_USER_EDIT).put("code", code);
    }

    @PostMapping("/active")
    @RequiresPermissions(AdminPermission.USER_EDIT)
    public JsonResponse active(String code) {
        userService.active(code);
        return JsonResponse.ok();
    }

    @PostMapping("/invalid")
    @RequiresPermissions(AdminPermission.USER_EDIT)
    public JsonResponse invalid(String code) {
        userService.invalid(code);
        return JsonResponse.ok();
    }

    @PostMapping("/save")
    @RequiresPermissions(AdminPermission.USER_EDIT)
    public JsonResponse save(SysUserVo vo) {
        log.debug("保存客户信息：{}", vo);
        userService.save(vo);
        return JsonResponse.ok();
    }
}
