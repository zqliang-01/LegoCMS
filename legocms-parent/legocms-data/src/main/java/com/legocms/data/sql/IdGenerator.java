package com.legocms.data.sql;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.legocms.core.exception.CoreException;
import com.legocms.data.base.BaseEntity;

public abstract class IdGenerator {
    private static IdGenerator current;

    public abstract Long nextId(BaseEntity baseEntity);

    public abstract Long nextId();

    public static IdGenerator getCurrent() {
        return current;
    }

    public static void setIdGenerator(IdGenerator current) {
        IdGenerator.current = current;
    }

    protected static String parserSubSystemPrefix(String name) {
        String subSystemPrefix = "";
        Pattern p = Pattern.compile("com.legocms.data.entities.([^.]+).");
        Matcher m = p.matcher(name);
        if (m.find()) {
            subSystemPrefix = m.group(1);
        }
        else {
            throw new CoreException(MessageFormat.format("ID生成时根据类名称{0}，无法确定子系统", name));
        }
        return subSystemPrefix;
    }
}
