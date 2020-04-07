package com.legocms.core.common;

public class FileUtil {

    public static String getName(String typs) {
        return StringUtil.getUUID() + typs;
    }

    public static String getFileType(String name) {
        return name.substring(name.lastIndexOf(".")).toLowerCase();
    }

}
