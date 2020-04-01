package com.legocms.handler;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

public interface RenderHandler {

    /**
         * 渲染
     */
    public void render() throws Exception;

    /**
         * 打印
     */
    public void print(String value) throws IOException;

    /**
         * 获取Writer
     */
    public Writer getWriter() throws IOException;

    public RenderHandler put(String key, Object value);

    public String getString(String name, String defaultValue) throws Exception;

    public String getString(String name) throws Exception;

    public Character getCharacter(String name) throws Exception;

    public int getInteger(String name, int defaultValue) throws Exception;

    public Integer getInteger(String name) throws Exception;

    public Short getShort(String name) throws Exception;

    public Long getLong(String name) throws Exception;

    public Double getDouble(String name) throws Exception;

    public Integer[] getIntegerArray(String name) throws Exception;

    public Long[] getLongArray(String name) throws Exception;

    public Short[] getShortArray(String name) throws Exception;

    public String[] getStringArray(String name) throws Exception;

    public Boolean getBoolean(String name, boolean defaultValue) throws Exception;

    public Boolean getBoolean(String name) throws Exception;

    public Date getDate(String name) throws Exception;

    public Date getDate(String name, Date defaultValue) throws Exception;

    public Locale getLocale() throws Exception;

    public HttpServletRequest getRequest() throws IOException, Exception;

    public Object getAttribute(String name) throws IOException, Exception;

    public void setRenderd();
}
