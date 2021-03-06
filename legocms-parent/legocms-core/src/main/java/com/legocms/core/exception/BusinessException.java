package com.legocms.core.exception;

import java.text.MessageFormat;

import com.legocms.core.common.ConstantEnum;

public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -4175159377328726294L;
    private String code;

    public BusinessException(String message) {
        super(message);
        this.code = ConstantEnum.BUSINESS_INVALID.getCode();
    }

    public BusinessException(ConstantEnum error) {
        super(error.getMsg());
        this.code = error.getCode();
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message, Throwable e) {
        super(message, e);
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static void check(boolean condition, String message, Object... arguments) {
        if (!condition) {
            throw new BusinessException(MessageFormat.format(message, arguments));
        }
    }

    public static void check(boolean condition, ConstantEnum error) {
        if (!condition) throw new BusinessException(error.getCode(), error.getMsg());
    }

    public static void error(ConstantEnum sessionInvalid) {
        throw new BusinessException(sessionInvalid.getCode(), sessionInvalid.getMsg());
    }
}
