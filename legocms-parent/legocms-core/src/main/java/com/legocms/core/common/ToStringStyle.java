package com.legocms.core.common;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.apache.commons.lang3.time.DateFormatUtils;

public class ToStringStyle extends StandardToStringStyle {

    public static ToStringStyle INSTANCE = new ToStringStyle();

    private static final long serialVersionUID = -4121870203762635872L;

    private ToStringStyle() {
        super();
        this.setContentStart("[");
        this.setFieldSeparator(System.getProperty("line.separator") + "  ");
        this.setFieldSeparatorAtStart(true);
        this.setContentEnd(System.getProperty("line.separator") + "]");
    }

    @Override
    public void append(StringBuffer buffer, String fieldName, Object value, Boolean fullDetail) {
        Object newValue = value;
        if (value instanceof Calendar) {
            newValue = DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.format((Calendar) value);
        }
        if (value instanceof Date) {
            newValue = DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.format((Date) value);
        }
        super.append(buffer, fieldName, newValue, fullDetail);
    }
}
