package com.legocms.web.controller.admin;

public interface AdminView {

    String USER_SESSION_KEY = "admin_user";

    String ROOT = "admin";
    String ERROR_500 = "error/500";
    String ERROR_403 = "error/403";
    String LOGIN = "login";
    String INDEX = "index";

    String SYS_USER_LIST = "sys_user/list";
    String SYS_USER_EDIT = "sys_user/edit";

    String SYS_PERMISSION_LIST = "sys_permission/list";

    String SYS_ROLE_LIST = "sys_role/list";

    String SYS_ORGANIZATION_LIST = "sys_organization/list";

    String SYS_SITE_LIST = "sys_site/list";
    String SYS_SITE_EDIT = "sys_site/edit";

    String SYS_TEMPLATE_LIST = "sys_template/list";
}
