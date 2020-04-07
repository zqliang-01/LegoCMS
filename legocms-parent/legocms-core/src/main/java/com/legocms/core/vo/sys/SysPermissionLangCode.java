package com.legocms.core.vo.sys;

import java.util.Arrays;
import java.util.List;

import com.legocms.core.dto.TypeInfo;

public interface SysPermissionLangCode {

    String ZH = "zh";

    String EN = "en";

    List<String> ALL = Arrays.asList(ZH, EN);

    List<TypeInfo> ALL_TYPE = Arrays.asList(new TypeInfo("zh", "中文"), new TypeInfo("en", "English"));
}
