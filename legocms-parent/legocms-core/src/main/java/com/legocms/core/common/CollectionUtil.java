package com.legocms.core.common;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.legocms.core.exception.CoreException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("all")
public class CollectionUtil {

    /**
         * 空集合检查。如果集合为null或者集合没有元素返回true。
     */
    public static boolean isNil(Collection<? extends Object> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotNil(Collection<? extends Object> collection) {
        return !isNil(collection);
    }

    public static boolean isNotNil(Object[] array) {
        return array != null && array.length > 0;
    }

    public static <T extends Object> boolean isNil(T... objs) {
        if (objs == null) {
            return true;
        }
        return isNil(Arrays.asList(objs));
    }

    /**
     * 计算list可按指定元素个数分成的组数目
     *
     * @param list
     * @param gSize
     * @return
     */
    public static int getGroupNum(List<? extends Object> list, int gSize) {
        CoreException.check(gSize > 1, "groupSize 必须大于 1");
        if (list.size() % gSize == 0) {
            return list.size() / gSize;
        }
        return 1 + (list.size() / gSize);
    }

    /**
     * 活动指定组的List
     *
     * @param list
     * @param gSize
     * @param gIndex
     * @return
     */
    public static <E> List<E> getGroupList(List<E> list, int gSize, int gIndex) {
        CoreException.check(gSize > 1, "must groupSize > 1");
        CoreException.check(gIndex >= 0, "must groupIndex >= 0 ");

        int startIndex = gIndex * gSize;
        int endIndex = ((gIndex + 1) * gSize) - 1;

        CoreException.check(startIndex < list.size(), "must startIndex < list.size()");

        if (endIndex >= list.size()) {
            endIndex = list.size() - 1;
        }
        return list.subList(startIndex, endIndex + 1);
    }

    public static <E> List<List<E>> groupList(List<E> list, int gSize) {
        List<List<E>> groups = new ArrayList<List<E>>();
        if (list == null) {
            return groups;
        }
        for (int i = 0; i < getGroupNum(list, gSize); i++) {
            groups.add(getGroupList(list, gSize, i));
        }
        return groups;
    }

    public static <T> Set<T> createSet(T... objects) {
        Set<T> set = new HashSet<T>();
        for (T obj : objects) {
            set.add(obj);
        }
        return set;
    }

    public static <T> List<T> createList(T... objects) {
        List<T> list = new ArrayList<T>();
        if (isNil(objects)) {
            return list;
        }
        for (T obj : objects) {
            list.add(obj);
        }
        return list;
    }

    public static <T> List<T> addAllNotRepeat(List<T> list, List<T> addList) {
        if (list == null) {
            list = new ArrayList<T>();
        }
        if (isNil(addList)) {
            return list;
        }

        for (T t : addList) {
            if (!list.contains(t)) {
                list.add(t);
            }
        }
        return list;
    }

    public static List subList(List list, int fromIndex, int toIndex) {
        if (list == null) {
            return list;
        }

        List newList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (i >= fromIndex && i < toIndex) {
                newList.add(list.get(i));
            }
        }

        return newList;
    }

    public static <T> String toElementString(Collection<T> c) {
        String string = c.toString();
        return c.isEmpty() ? "" : string.substring(1, string.length() - 1);
    }

    public static List diff(List list1, List list2) {
        if (isNil(list1)) {
            return null;
        }
        List list = new ArrayList(Arrays.asList(new Object[list1.size()]));
        Collections.copy(list, list1);
        list.removeAll(list2);
        return list;
    }

    public static <T> List<T> array2List(T[] arr) {
        List<T> list = new ArrayList<T>();
        for (T t : arr) {
            list.add(t);
        }
        return list;
    }

    public static <T> List<String> getCodes(Collection<T> c) throws Exception {
        List<String> codes = new ArrayList<String>();
        if (isNil(c)) {
            return codes;
        }
        for (T t : c) {
            Class ownerClass = t.getClass();
            Method m = ownerClass.getDeclaredMethod("getCode");
            String property = (String) m.invoke(t);
            codes.add(property);
        }
        return codes;
    }

    public static <T> Set<String> getUniqueCodes(Collection<T> c) throws Exception {
        Set<String> codes = new HashSet<String>();
        if (isNil(c)) {
            return codes;
        }
        for (T t : c) {
            Class ownerClass = t.getClass();
            Method m = ownerClass.getDeclaredMethod("getCode");
            String property = (String) m.invoke(t);
            codes.add(property);
        }
        return codes;
    }

    public static List[] splitList(List list, int pageSize) {
        int total = list.size();
        int pageCount = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        List[] result = new List[pageCount];
        for (int i = 0; i < pageCount; i++) {
            int start = i * pageSize;
            int end = start + pageSize > total ? total : start + pageSize;
            List subList = list.subList(start, end);
            result[i] = subList;
        }
        return result;
    }
}
