package com.legocms.core.vo.cms;

import java.util.Arrays;
import java.util.List;

import com.legocms.core.dto.TypeInfo;

public interface CmsModelAttributeCode {

    TypeInfo TITLE = new TypeInfo("title", "标题");

    TypeInfo COPIED = new TypeInfo("copied", "转载");

    TypeInfo TAG = new TypeInfo("tag", "标签");

    TypeInfo AUTHOR = new TypeInfo("author", "作者");

    TypeInfo EDITOR = new TypeInfo("editor", "编辑");

    TypeInfo DESCRIPTION = new TypeInfo("description", "描述");

    TypeInfo COVER = new TypeInfo("cover", "封面图");

    TypeInfo SOURCE = new TypeInfo("source", "来源");

    TypeInfo SOURCE_URL = new TypeInfo("sourceUrl", "来源网址");

    TypeInfo TEXT = new TypeInfo("text", "正文");

    List<TypeInfo> ALL = Arrays.asList(TITLE, COPIED, TAG, AUTHOR, EDITOR, DESCRIPTION, COVER, SOURCE, SOURCE_URL, TEXT);
}
