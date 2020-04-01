package com.legocms.core.dto;

import java.util.HashMap;

import com.legocms.core.common.ConstantEnum;

/**
 * Json返回数据
 */
public class JsonResponse extends HashMap<String, Object> {

    public static final String KEY_CODE = "code";
    public static final String KEY_MESSAGE = "msg";
    public static final String KEY_RESULT = "result";

    public static final String SUCCESS = "0";

    private static final long serialVersionUID = 1L;

    public JsonResponse() {
        put(KEY_CODE, SUCCESS);
        put(KEY_MESSAGE, "success");
    }

    public static JsonResponse error(final String msg) {
        final JsonResponse r = new JsonResponse();
        r.put(KEY_CODE, ConstantEnum.UNKNOW_ERROR.getCode());
        r.put(KEY_MESSAGE, msg);
        return r;
    }

    public static JsonResponse error(final String code, final String msg) {
        final JsonResponse r = new JsonResponse();
        r.put(KEY_CODE, code);
        r.put(KEY_MESSAGE, msg);
        return r;
    }

    public static JsonResponse ok() {
        return new JsonResponse();
    }

    @Override
    public JsonResponse put(final String key, final Object value) {
        super.put(key, value);
        return this;
    }

    public boolean isOk() {
        return SUCCESS.equals(get(KEY_CODE));
    }

    public static JsonResponse ok(final Object result) {
        return JsonResponse.ok().put(KEY_RESULT, result);
    }

    @SuppressWarnings("unchecked")
    public <T> T getResult() {
        return (T) get(KEY_RESULT);
    }
}
