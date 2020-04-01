package com.legocms.core.exception;

import com.legocms.core.common.ConstantEnum;

public class CoreException extends RuntimeException {
    private static final long serialVersionUID = 3357231670678262776L;
    private String code;

    public CoreException(String message) {
        super(message);
        this.code = ConstantEnum.BUSINESS_INVALID.getCode();
    }

    public CoreException(Throwable e) {
        super(e);
    }

    public CoreException(String code, String message) {
        super(message);
        this.code = code;
    }

    public CoreException(String message, Throwable e) {
        super(message, e);
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static void check(boolean condition, String message) {
        if (!condition) throw new CoreException(message);
    }

    public static void check(boolean condition, ConstantEnum error) {
        if (!condition) throw new CoreException(error.getCode(), error.getMsg());
    }
}
