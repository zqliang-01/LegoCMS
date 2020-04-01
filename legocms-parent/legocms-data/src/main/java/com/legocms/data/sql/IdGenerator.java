package com.legocms.data.sql;

import com.legocms.data.base.BaseEntity;

public abstract class IdGenerator {
    private static IdGenerator current;

    public abstract Long nextId(BaseEntity baseEntity);

    public static IdGenerator getCurrent() {
        return current;
    }

    public static void setIdGenerator(IdGenerator current) {
        IdGenerator.current = current;
    }
}
