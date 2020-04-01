package com.legocms.tools;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.legocms.core.common.Constants;
import com.legocms.core.common.DateUtil;
import com.legocms.core.common.StringUtil;

import freemarker.ext.beans.BeanModel;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateHashModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateScalarModel;
import freemarker.template.TemplateSequenceModel;

/**
 * 模板数据模型工具类
 */
public class TemplateModelUtils {

    public static Object converBean(TemplateModel model) throws TemplateModelException {
        if (null != model) {
            if (model instanceof TemplateSequenceModel) {
                converBean(((TemplateSequenceModel) model).get(0));
            }
            if (model instanceof BeanModel) {
                return ((BeanModel) model).getWrappedObject();
            }
        }
        return null;
    }

    public static String converString(TemplateModel model) throws TemplateModelException {
        if (null != model) {
            if (model instanceof TemplateSequenceModel) {
                converString(((TemplateSequenceModel) model).get(0));
            }
            if (model instanceof TemplateScalarModel) {
                return ((TemplateScalarModel) model).getAsString();
            }
            else if ((model instanceof TemplateNumberModel)) {
                return ((TemplateNumberModel) model).getAsNumber().toString();
            }
        }
        return null;
    }

    public static TemplateHashModelEx converMap(TemplateModel model) throws TemplateModelException {
        if (null != model) {
            if (model instanceof TemplateHashModelEx) {
                return (TemplateHashModelEx) model;
            }
        }
        return null;
    }

    public static Integer converInteger(TemplateModel model) throws TemplateModelException {
        if (null != model) {
            if (model instanceof TemplateSequenceModel) {
                converInteger(((TemplateSequenceModel) model).get(0));
            }
            if (model instanceof TemplateNumberModel) {
                return ((TemplateNumberModel) model).getAsNumber().intValue();
            }
            else if (model instanceof TemplateScalarModel) {
                String s = ((TemplateScalarModel) model).getAsString();
                if (StringUtil.isNotBlank(s)) {
                    try {
                        return Integer.parseInt(s);
                    }
                    catch (NumberFormatException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    public static Short converShort(TemplateModel model) throws TemplateModelException {
        if (null != model) {
            if (model instanceof TemplateSequenceModel) {
                model = ((TemplateSequenceModel) model).get(0);
            }
            if (model instanceof TemplateNumberModel) {
                return ((TemplateNumberModel) model).getAsNumber().shortValue();
            }
            else if (model instanceof TemplateScalarModel) {
                String s = ((TemplateScalarModel) model).getAsString();
                if (StringUtil.isNotBlank(s)) {
                    try {
                        return Short.parseShort(s);
                    }
                    catch (NumberFormatException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    public static Long converLong(TemplateModel model) throws TemplateModelException {
        if (null != model) {
            if (model instanceof TemplateSequenceModel) {
                model = ((TemplateSequenceModel) model).get(0);
            }
            if (model instanceof TemplateNumberModel) {
                return ((TemplateNumberModel) model).getAsNumber().longValue();
            }
            else if (model instanceof TemplateScalarModel) {
                String s = ((TemplateScalarModel) model).getAsString();
                if (StringUtil.isNotBlank(s)) {
                    try {
                        return Long.parseLong(s);
                    }
                    catch (NumberFormatException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    public static Double converDouble(TemplateModel model) throws TemplateModelException {
        if (null != model) {
            if (model instanceof TemplateSequenceModel) {
                converDouble(((TemplateSequenceModel) model).get(0));
            }
            if (model instanceof TemplateNumberModel) {
                return ((TemplateNumberModel) model).getAsNumber().doubleValue();
            }
            else if (model instanceof TemplateScalarModel) {
                String s = ((TemplateScalarModel) model).getAsString();
                if (StringUtil.isNotBlank(s)) {
                    try {
                        return Double.parseDouble(s);
                    }
                    catch (NumberFormatException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    public static String[] converStringArray(TemplateModel model) throws TemplateModelException {
        if (model instanceof TemplateSequenceModel) {
            TemplateSequenceModel smodel = (TemplateSequenceModel) model;
            String[] values = new String[smodel.size()];
            for (int i = 0; i < smodel.size(); i++) {
                values[i] = converString(smodel.get(i));
            }
            return values;
        }
        String str = converString(model);
        if (null != str) {
            if (0 <= str.indexOf(Constants.COMMA_DELIMITED)) {
                return StringUtils.split(str, Constants.COMMA_DELIMITED);
            }
            else {
                return StringUtils.split(str, Constants.BLANK_SPACE);
            }
        }
        return null;
    }

    public static Boolean converBoolean(TemplateModel model) throws TemplateModelException {
        if (null != model) {
            if (model instanceof TemplateSequenceModel) {
                model = ((TemplateSequenceModel) model).get(0);
            }
            if (model instanceof TemplateBooleanModel) {
                return ((TemplateBooleanModel) model).getAsBoolean();
            }
            else if (model instanceof TemplateNumberModel) {
                return !(0 == ((TemplateNumberModel) model).getAsNumber().intValue());
            }
            else if (model instanceof TemplateScalarModel) {
                String temp = ((TemplateScalarModel) model).getAsString();
                if (StringUtil.isNotBlank(temp)) {
                    return Boolean.valueOf(temp);
                }
            }
        }
        return null;
    }

    public static Date converDate(TemplateModel model) throws TemplateModelException, ParseException {
        if (null != model) {
            if (model instanceof TemplateSequenceModel) {
                converDate(((TemplateSequenceModel) model).get(0));
            }
            if (model instanceof TemplateDateModel) {
                return ((TemplateDateModel) model).getAsDate();
            }
            else if (model instanceof TemplateScalarModel) {
                String temp = StringUtils.trimToEmpty(((TemplateScalarModel) model).getAsString());
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
                        return null;
                    }
                }
            }
            else if (model instanceof TemplateNumberModel) {
                return new Date(((TemplateNumberModel) model).getAsNumber().longValue());
            }
        }
        return null;
    }
}
