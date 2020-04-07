package com.legocms.web.controller.admin;

public interface AdminView {

    String USER_SESSION_KEY = "admin_user";

    String ROOT = "admin";
    String UI_TYPE = "dashboard-ui/";//"unicorn-ui/";
    String ERROR_500 = "error/500";
    String ERROR_403 = "error/403";
    String LOGIN = UI_TYPE + "login";
    String INDEX = UI_TYPE + "index";

    String SYS_USER_LIST = UI_TYPE + "sys_user/list";
    String SYS_USER_EDIT = UI_TYPE + "sys_user/edit";

    String SYS_PERMISSION_LIST = UI_TYPE + "sys_permission/list";

    String SYS_ROLE_LIST = UI_TYPE + "sys_role/list";

    String SYS_ORGANIZATION_LIST = UI_TYPE + "sys_organization/list";

    String SYS_SITE_LIST = UI_TYPE + "sys_site/list";
    String SYS_SITE_EDIT = UI_TYPE + "sys_site/edit";

    String CMS_TEMPLATE_LIST = UI_TYPE + "cms_template/list";
}
