package com.legocms.core.common;

public enum ConstantEnum {
    UNKNOW_ERROR("9999", "系统未知异常"),
    UNPAID_ORDER_STATUS("2000", "未支付"),
    BUSINESS_INVALID("3000", "业务异常"),
    AUTHORIZATION_INVALID("5000", "访问功能未授权"),
    INTERFACE_NOTFOUND_INVALID("5001", "不存在的接口！"),
    RESOURCE_NOTFUND_INVALID("404", "资源不存在"),
    SESSION_INVALID("1000", "登录超时，请重新登录！");

    private String code;
    private String msg;

    private ConstantEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public static ConstantEnum codeOf(String value) {
        for (ConstantEnum item : values()) {
            if (item.getCode().equals(value)) {
                return item;
            }
        }
        return null;
    }
}
