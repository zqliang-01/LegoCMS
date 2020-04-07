package com.legocms.web.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.legocms.core.dto.sys.SysSiteInfo;
import com.legocms.core.dto.sys.SysUserInfo;
import com.legocms.core.exception.BusinessException;
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
        setSite(user.getSite());
    }

    protected void setSite(SysSiteInfo site) {
        setAttribute(AdminView.SITE_SESSION_KEY, site);
    }

    protected SysSiteInfo getSite() {
        return getAttribute(AdminView.SITE_SESSION_KEY);
    }

    protected String getSiteCode() {
        SysSiteInfo site = getSite();
        BusinessException.check(site != null, "当前无管理站点，请先选择管理站点！");
        return site.getCode();
    }

    protected List<String> getPermissionCodes() {
        return getUser().getPermissions();
    }

}
