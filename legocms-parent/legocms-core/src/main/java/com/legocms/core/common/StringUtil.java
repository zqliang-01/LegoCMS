package com.legocms.core.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import com.legocms.core.exception.CoreException;

public class StringUtil extends StringUtils {
    private static final String charset = "UTF-8";

    public static boolean isBlank(Collection<String> strings) {
        return strings == null || strings.size() == 0;
    }

    public static boolean isNotBlank(Collection<String> strings) {
        return strings != null;
    }

    public static String objToStr(Object obj) {
        return obj == null ? "" : String.valueOf(obj);
    }

    public static String encodeUrl(String value) {
        try {
            if (StringUtil.isBlank(value)) {
                return value;
            }
            return URLEncoder.encode(value, charset);
        }
        catch (UnsupportedEncodingException e) {
            throw new CoreException("Url字符编码出错", e);
        }
    }

    public static String decodeUrl(String value) {
        try {
            if (StringUtil.isBlank(value)) {
                return value;
            }
            return URLDecoder.decode(value, charset);
        }
        catch (UnsupportedEncodingException e) {
            throw new CoreException("Url字符解码出错", e);
        }
    }

    public static String encodeBase64(String value) {
        return new String(Base64.encodeBase64(value.getBytes()));
    }

    public static String decodeBase64(String value) {
        return new String(Base64.decodeBase64(value.getBytes()));
    }

    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static String getMD5(String value) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(value.getBytes("UTF-8"));
        }
        catch (Exception e) {
            throw new CoreException("MD5编码出错", e);
        }
        byte[] b = md.digest();
        int i;
        StringBuffer buf = new StringBuffer("");
        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0) {
                i += 256;
            }
            if (i < 16) {
                buf.append("0");
            }
            buf.append(Integer.toHexString(i));
        }
        return buf.toString().toUpperCase();
    }

    /**
     * Clob转为String
     */
    public static String clobToString(Clob clob) {
        String reString = "";
        Reader is = null;
        try {
            is = clob.getCharacterStream();
        }
        catch (SQLException e) {
            throw new CoreException("Clob转为String出错", e);
        }
        BufferedReader br = new BufferedReader(is);
        String s = null;
        try {
            s = br.readLine();
        }
        catch (IOException e) {
            throw new CoreException("Clob转为String出错", e);
        }
        StringBuffer sb = new StringBuffer();
        while (s != null) {
            sb.append(s);
            try {
                s = br.readLine();
            }
            catch (IOException e) {
                throw new CoreException("Clob转为String出错", e);
            }
        }
        reString = sb.toString();
        return reString;
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String replaceSubString(String param, int start, int end, String replaceStr) {
        param = param.trim();
        if (isBlank(param)) {
            return null;
        }
        StringBuffer str = new StringBuffer();
        char[] arr = param.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            if (start <= i && i < end) {
                str.append(replaceStr);
            }
            else {
                str.append(arr[i]);
            }
        }
        return str.toString();
    }

    public static String trimStart(String str, String replaceStr) {
        if (str != null && str.startsWith(replaceStr)) {
            return str.substring(str.indexOf(replaceStr) + replaceStr.length());
        }
        return str;
    }

    public static String trimEnd(String str, String replaceStr) {
        if (str != null && str.endsWith(replaceStr)) {
            return str.substring(0, str.lastIndexOf(replaceStr));
        }
        return str;
    }
}
