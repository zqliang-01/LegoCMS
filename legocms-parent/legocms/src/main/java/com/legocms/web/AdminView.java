package com.legocms.web;

public interface AdminView {

    String USER_SESSION_KEY = "admin_user";

    String ROOT = "admin";
    String ERROR_500 = "error/500";
    String ERROR_403 = "error/403";
    String LOGIN = "login";
    String INDEX = "index";

    String CMS_USER_LIST = "cms_user/list";
    String CMS_USER_EDIT = "cms_user/edit";

    String CMS_PERMISSION_LIST = "cms_permission/list";

    String CMS_ROLE_LIST = "cms_role/list";
}
