package com.legocms.core.common;

import java.nio.charset.Charset;

import org.springframework.http.MediaType;

import com.legocms.core.vo.sys.SysPermissionLangCode;

public interface Constants {
    String DEFAULT_LANG = SysPermissionLangCode.ZH;

    String DIRECTIVE_REMOVE_REGEX = "Directive";

    String DEFAULT_CHARSET_NAME = "UTF-8";
    Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    String DEFAULT_PAGE = "index.html";

    String MEDIA_TYPE = "application/json;charset=UTF-8";
    MediaType JSON_MEDIA_TYPE = new MediaType("application", "json", DEFAULT_CHARSET);

    String SEPARATOR = "/";
    String BLANK = "";
    String DOT = ".";
    String UNDERLINE = "_";
    String BLANK_SPACE = " ";
    String COMMA_DELIMITED = ",";

    String ROOT_PATH = "/legocms";
}
