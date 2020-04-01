package com.legocms.core.vo;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/** VO的基类 */
public abstract class Vo implements Serializable {

    private static final long serialVersionUID = 5946293315696611712L;

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static String toJson(Object info) {
        return JSON.toJSONString(info);
    }

    @Override
    public String toString() {
        return this.toJson();
    }

    @SuppressWarnings("unchecked")
    public <T extends Vo> T buildAsJson(String json) {
        return (T) JSON.parseObject(json, this.getClass());
    }
}
