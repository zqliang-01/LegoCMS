package com.legocms.data.handler;

import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.legocms.core.common.CollectionUtil;
import com.legocms.core.common.Constants;
import com.legocms.core.common.DateUtil;
import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.Vo;

public class HttpParameterHandler extends BaseFreemarkerHandler {

    private MediaType mediaType;
    private HttpMessageConverter<Object> httpMessageConverter;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public HttpParameterHandler(HttpMessageConverter<Object> httpMessageConverter, MediaType mediaType, HttpServletRequest request, HttpServletResponse response) {
        this.httpMessageConverter = httpMessageConverter;
        this.request = request;
        this.response = response;
        this.mediaType = mediaType;
        regristerParameters();
    }

    @Override
    public void render() throws HttpMessageNotWritableException, IOException {
        if (!renderd) {
            httpMessageConverter.write(map, mediaType, new ServletServerHttpResponse(response));
            renderd = true;
        }
    }

    @Override
    public void print(String value) throws IOException {
        response.getWriter().print(value);
    }

    @Override
    public Writer getWriter() throws IOException {
        return response.getWriter();
    }

    @Override
    protected String getStringWithoutRegister(String name) {
        return request.getParameter(name);
    }

    @Override
    protected Integer getIntegerWithoutRegister(String name) {
        String result = getStringWithoutRegister(name);
        if (StringUtil.isNotBlank(result)) {
            try {
                return Integer.valueOf(result);
            }
            catch (NumberFormatException e) {
                new BusinessException("name[" + name + "]获取Integer异常", e);
            }
        }
        return null;
    }

    @Override
    public Short getShort(String name) {
        regristerParameter(PARAMETER_TYPE_STRING, name);
        String result = getStringWithoutRegister(name);
        if (StringUtil.isNotBlank(result)) {
            try {
                return Short.valueOf(result);
            }
            catch (NumberFormatException e) {
                new BusinessException("name[" + name + "]转Short异常", e);
            }
        }
        return null;
    }

    @Override
    public Long getLong(String name) {
        regristerParameter(PARAMETER_TYPE_LONG, name);
        String result = getStringWithoutRegister(name);
        if (StringUtil.isNotBlank(result)) {
            try {
                return Long.valueOf(result);
            }
            catch (NumberFormatException e) {
                new BusinessException("name[" + name + "]转Long异常", e);
            }
        }
        return null;
    }

    @Override
    public Double getDouble(String name) {
        regristerParameter(PARAMETER_TYPE_DOUBLE, name);
        String result = getStringWithoutRegister(name);
        if (StringUtil.isNotBlank(result)) {
            try {
                return Double.valueOf(result);
            }
            catch (NumberFormatException e) {
                new BusinessException("name[" + name + "]转double异常", e);
            }
        }
        return null;
    }

    @Override
    protected String[] getStringArrayWithoutRegister(String name) {
        String[] values = request.getParameterValues(name);
        if (CollectionUtil.isNotNil(values) && 1 == values.length && 0 <= values[0].indexOf(Constants.COMMA_DELIMITED)) {
            return StringUtils.split(values[0], Constants.COMMA_DELIMITED);
        }
        return values;
    }

    @Override
    protected Boolean getBooleanWithoutRegister(String name) {
        String result = getStringWithoutRegister(name);
        if (StringUtil.isNotBlank(result)) {
            return Boolean.valueOf(result);
        }
        return null;
    }

    @Override
    public Date getDateWithoutRegister(String name) throws ParseException {
        String result = getStringWithoutRegister(name);
        if (StringUtil.isNotBlank(result)) {
            String temp = StringUtils.trimToEmpty(result);
            if (DateUtil.dateTimePatternLength == temp.length()) {
                return DateUtil.toDateTime(temp);
            }
            else if (DateUtil.datePatternLength == temp.length()) {
                return DateUtil.toDate(temp);
            }
            else {
                try {
                    return new Date(Long.parseLong(temp));
                }
                catch (NumberFormatException e) {
                    new BusinessException("temp[" + temp +"]格式化日期异常", e);
                }
            }
        }
        return null;
    }

    @Override
    public HttpServletRequest getRequest() throws IOException, Exception {
        return request;
    }

    @Override
    public Object getAttribute(String name) {
        return request.getAttribute(name);
    }

    @Override
    public Locale getLocale() {
        return RequestContextUtils.getLocale(request);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Vo> T getJsonVo(Class<T> clazz) throws IOException, Exception {
        return (T) httpMessageConverter.read(clazz, new ServletServerHttpRequest(request));
    }

}
