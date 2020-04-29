package com.legocms.data.handler;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.legocms.core.vo.Vo;

public interface RenderHandler {

    void render() throws Exception;

    void print(String value) throws IOException;

    Writer getWriter() throws IOException;

    RenderHandler put(String key, Object value);

    String getString(String name, String defaultValue) throws Exception;

    String getString(String name) throws Exception;

    Character getCharacter(String name) throws Exception;

    int getInteger(String name, int defaultValue) throws Exception;

    Integer getInteger(String name) throws Exception;

    Short getShort(String name) throws Exception;

    Long getLong(String name) throws Exception;

    Double getDouble(String name) throws Exception;

    Integer[] getIntegerArray(String name) throws Exception;

    Long[] getLongArray(String name) throws Exception;

    Short[] getShortArray(String name) throws Exception;

    String[] getStringArray(String name) throws Exception;

    Boolean getBoolean(String name, boolean defaultValue) throws Exception;

    Boolean getBoolean(String name) throws Exception;

    Date getDate(String name) throws Exception;

    Date getDate(String name, Date defaultValue) throws Exception;

    Locale getLocale() throws Exception;

    HttpServletRequest getRequest() throws IOException, Exception;

    Object getAttribute(String name) throws IOException, Exception;

    <T extends Vo> T getJsonVo(Class<T> clazz) throws IOException, Exception;

    public void setRenderd();
}
