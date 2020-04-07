package com.legocms.web.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.legocms.core.dto.TypeInfo;
import com.legocms.core.dto.sys.SysUserInfo;
import com.legocms.core.web.session.SessionController;
import com.legocms.service.sys.ISysUserService;

public class AdminController extends SessionController {

    @Autowired
    private ISysUserService userService;

    protected SysUserInfo getUser() {
        return getAttribute(AdminView.USER_SESSION_KEY);
    }

    protected void setUser(SysUserInfo user) {
        setAttribute(AdminView.USER_SESSION_KEY, user);
    }

    protected String getUserCode() {
        return getUser().getCode();
    }

    protected void refreshUser() {
        SysUserInfo user = userService.findBy(getUserCode());
        setUser(user);
    }

    protected TypeInfo getSite() {
        return getUser().getSite();
    }

    protected String getSiteCode() {
        return getSite().getCode();
    }

    protected List<String> getPermissionCodes() {
        return getUser().getPermissions();
    }

}
