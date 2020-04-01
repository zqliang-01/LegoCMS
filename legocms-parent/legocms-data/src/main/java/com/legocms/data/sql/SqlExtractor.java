package com.legocms.data.sql;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UnknownFormatConversionException;

import org.hibernate.type.BigDecimalType;
import org.hibernate.type.BooleanType;
import org.hibernate.type.CalendarType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.FloatType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.ShortType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;

/**
 * sql列提取器
 */
public final class SqlExtractor {

    public static final String COLUMN_SPLIT_SYMBOL = "#";
    private static final String[] HAVE_TO_REPLACED_CHARS = {"\n", "\t", "\""}; // 需要替换掉的特殊符号，防止干扰取值
    private static final Map<Class<?>, Type> JAVA_TO_HIBERNATE_TYPE = new HashMap<Class<?>, Type>();

    static {
        JAVA_TO_HIBERNATE_TYPE.put(boolean.class, BooleanType.INSTANCE);
        JAVA_TO_HIBERNATE_TYPE.put(double.class, DoubleType.INSTANCE);
        JAVA_TO_HIBERNATE_TYPE.put(float.class, FloatType.INSTANCE);
        JAVA_TO_HIBERNATE_TYPE.put(int.class, IntegerType.INSTANCE);
        JAVA_TO_HIBERNATE_TYPE.put(long.class, LongType.INSTANCE);
        JAVA_TO_HIBERNATE_TYPE.put(short.class, ShortType.INSTANCE);
        JAVA_TO_HIBERNATE_TYPE.put(BigDecimal.class, BigDecimalType.INSTANCE);
        JAVA_TO_HIBERNATE_TYPE.put(Boolean.class, BooleanType.INSTANCE);
        JAVA_TO_HIBERNATE_TYPE.put(Calendar.class, CalendarType.INSTANCE);
        JAVA_TO_HIBERNATE_TYPE.put(Double.class, DoubleType.INSTANCE);
        JAVA_TO_HIBERNATE_TYPE.put(Float.class, FloatType.INSTANCE);
        JAVA_TO_HIBERNATE_TYPE.put(Integer.class, IntegerType.INSTANCE);
        JAVA_TO_HIBERNATE_TYPE.put(Long.class, LongType.INSTANCE);
        JAVA_TO_HIBERNATE_TYPE.put(Short.class, ShortType.INSTANCE);
        JAVA_TO_HIBERNATE_TYPE.put(String.class, StringType.INSTANCE);
    }

    /**
         * 从sql中提取出列名，并根据要输出的bean，找出对应的Hibernate类型
     */
    public static Map<String, Type> extract(Class<?> aliasBean, String sql) {
        String sqlString = sql.trim();

        if (!sqlString.toUpperCase().startsWith("SELECT")) {
            throw new UnsupportedOperationException("不符合条件的sql，目前仅支持对以select开头的sql进行解析");
        }

        Map<String, Type> map = new LinkedHashMap<String, Type>();
        List<String> columnNames = null;
        try {
            columnNames = parseColumnNames(sqlString);
        }
        catch (Exception e) {
            throw new UnsupportedOperationException("SQL解析失败", e);
        }
        for (String columnName : columnNames) {
            map.put(columnName, getColumnHibernateType(aliasBean, columnName));
        }

        return map;
    }

    private static List<String> parseColumnNames(String sql) throws JSQLParserException {
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Select select = (Select) parserManager.parse(new StringReader(sql));
        PlainSelect plain = (PlainSelect) select.getSelectBody();
        List<SelectItem> selectitems = plain.getSelectItems();
        List<String> str_items = new ArrayList<String>();
        if (selectitems != null) {
            for (int i = 0; i < selectitems.size(); i++) {
                String item = selectitems.get(i).toString();
                String[] columns = item.split(" AS ");
                if (columns.length == 2) {
                    str_items.add(columns[1].trim());
                }
                else {
                    String column = item.substring(item.lastIndexOf(" ") + 1);
                    str_items.add(column.trim());
                }
            }
        }
        return str_items;
    }

    static String countSql(String sql) {
        for (String c : HAVE_TO_REPLACED_CHARS) {
            sql = sql.replace(c, " ");
        }
        int fromIndex = sql.toUpperCase().indexOf(" FROM ");
        if (fromIndex == -1) {
            throw new UnsupportedOperationException("解析sql失败，未找到FROM关键字");
        }
        return "SELECT count(1) " + sql.substring(fromIndex);
    }

    private static Type getColumnHibernateType(Class<?> aliasBean, String columnName) {
        Class<?> fieldType;
        if (columnName.contains(COLUMN_SPLIT_SYMBOL)) {
            String[] fieldNames = columnName.split(COLUMN_SPLIT_SYMBOL);
            Field f = getDeclaredField(aliasBean, fieldNames[0]);
            fieldType = getDeclaredField(f.getType(), fieldNames[1]).getType();
        }
        else {
            Field field = getDeclaredField(aliasBean, columnName);
            fieldType = field.getType();
        }

        Type type = JAVA_TO_HIBERNATE_TYPE.get(fieldType);
        if (type == null) {
            throw new UnsupportedOperationException("不支持的类型映射，请检查：" + fieldType);
        }
        return type;
    }

    static Field getDeclaredField(Class<?> clazz, String fieldName) {
        Class<?> tempClazz = clazz;
        while (tempClazz != null) {
            try {
                return tempClazz.getDeclaredField(fieldName);
            }
            catch (NoSuchFieldException e) {
                tempClazz = tempClazz.getSuperclass();
                continue;
            }
        }
        throw new UnknownFormatConversionException(MessageFormat.format("sql字段映射到bean错误:在bean[{0}]中未找到属性[{1}]", clazz, fieldName));
    }
}
