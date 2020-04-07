package com.legocms.core.common;

public class FileUtil {

    public static String getName(String typs) {
        return StringUtil.getUUID() + typs;
    }

    public static String getFileType(String name) {
        if (name.lastIndexOf(".") < 0) {
            return null;
        }
        return name.substring(name.lastIndexOf(".")).toLowerCase();
    }

    public static String getAbsolutePath(String path, String siteCode) {
        return Constants.ROOT_PATH + Constants.SEPARATOR + siteCode + path;
    }

    public static String getPath(String filePath) {
        return filePath.substring(0, filePath.lastIndexOf(Constants.SEPARATOR));
    }

    public static String getFile(String filePath) {
        return filePath.substring(filePath.lastIndexOf(Constants.SEPARATOR) + 1);
    }
}
