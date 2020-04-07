package com.legocms.core.web;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.legocms.core.common.ConstantEnum;
import com.legocms.core.common.Constants;
import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.exception.ServiceException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractExceptionHandler {

    protected abstract String getPage500();

    protected abstract String getPageLogin();

    protected abstract String getPage403();

    @ExceptionHandler(value = Throwable.class)
    public ModelAndView defaulErrorHandler(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, Throwable e) throws  Exception {
        Throwable rootException = getRootCause(e);
        log.error("全局未知异常", rootException);

        String errorCode = ConstantEnum.UNKNOW_ERROR.getCode();
        String errorMsg = rootException.getMessage();
        if (StringUtil.isBlank(errorMsg)) {
            errorMsg = rootException.getClass().getSimpleName();
        }
        return response(response, handlerMethod, errorCode, errorMsg);
    }

    @ExceptionHandler(value = ServiceException.class)
    public ModelAndView serviceErrorHandler(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, ServiceException e) throws  Exception {
        String errorCode = e.getCode();
        String errorMsg = e.getMessage();
        log.error("服务异常", e);
        return response(response, handlerMethod, errorCode, errorMsg);
    }

    @ExceptionHandler(value = BusinessException.class)
    public ModelAndView businessErrorHandler(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, BusinessException e) throws  Exception {
        String errorCode = e.getCode();
        String errorMsg = e.getMessage();
        return response(response, handlerMethod, errorCode, errorMsg);
    }

    protected ModelAndView response(HttpServletResponse response, HandlerMethod handlerMethod, String errorCode, String errorMsg) {

        Class<?> returnType = handlerMethod.getMethod().getReturnType();
        if (returnType == JsonResponse.class || returnType == void.class) {
            return jsonResponse(response, errorMsg, errorCode);
        }

        if (ConstantEnum.SESSION_INVALID.getCode().equals(errorCode)) {
            return ViewResponse.ok(getPageLogin());
        }
        if (ConstantEnum.AUTHORIZATION_INVALID.getCode().equals(errorCode)) {
            return ViewResponse.ok(getPage403());
        }
        ViewResponse errorView = ViewResponse.ok(getPage500());
        errorView.put(JsonResponse.KEY_MESSAGE, errorMsg);
        errorView.put(JsonResponse.KEY_CODE, errorCode);
        return errorView;
    }

    private ModelAndView jsonResponse(HttpServletResponse response, String errorMsg, String errorCode) {
        response.setContentType(Constants.MEDIA_TYPE);
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.OK.value());
        PrintWriter writer = null;
        try {
            JsonResponse result = JsonResponse.error(errorCode, errorMsg);

            writer = response.getWriter();
            writer.write(JSON.toJSONString(result));
        }
        catch (Throwable ex) {
            log.error("输出异常返回结果时发生错误:", ex);
        }
        finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
        return new ModelAndView();
    }

    public Throwable getRootCause(Throwable throwable) {
        Throwable root = throwable;
        while (root.getCause() != null) {
            root = root.getCause();
        }
        return root;
    }
}
