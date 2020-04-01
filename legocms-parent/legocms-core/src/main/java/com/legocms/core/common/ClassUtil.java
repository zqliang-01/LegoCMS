package com.legocms.core.common;

import static java.lang.ClassLoader.getSystemResources;
import static java.lang.Thread.currentThread;
import static java.text.MessageFormat.format;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.SecureClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.ClassUtils;

import com.legocms.core.exception.CoreException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClassUtil extends ClassUtils {

    private ClassUtil() { }

    @SuppressWarnings("unchecked")
    public static List<String> getClassNames(String packageName) {

        String packagePathName = packageName;
        packagePathName = packagePathName.replace('.', '/');
        if (!packagePathName.endsWith("/")) {
            packagePathName += "/";
        }
        CoreException.check(packagePathName.length() > 0, "不规范的包名：" + packagePathName);

        List<String> classNames = new ArrayList<String>();

        try {
            Enumeration<URL> urls = null;
            ClassLoader currentClassLoader = ClassUtil.class.getClassLoader();
            log.debug(format("当前 ClassLoader 为:{0}", currentClassLoader.getClass().getName()));

            if (currentClassLoader instanceof SecureClassLoader) {
                // websphere和weblogic9.1
                log.debug("使用 SecureClassLoader.findResources() 来分析...");
                SecureClassLoader secureClassLoader = (SecureClassLoader) currentClassLoader;
                Class<?> clazz = secureClassLoader.getClass();
                Method fMethod = null;
                if ("weblogic.utils.classloaders.ChangeAwareClassLoader".equals(secureClassLoader.getClass().getName())) {
                    log.debug("weblogic中，使用 weblogic.utils.classloaders.ChangeAwareClassLoader的父类中保护方法findResources() 来分析...");
                    clazz = clazz.getSuperclass();
                    fMethod = clazz.getDeclaredMethod("findResources", String.class);
                    fMethod.setAccessible(true);
                }
                else {
                    fMethod = clazz.getMethod("findResources", String.class);
                }
                urls = (Enumeration<URL>) fMethod.invoke(secureClassLoader, packagePathName);
            }
            else {
                log.debug("使用 ClassLoader.getSystemResources() 来分析...");
                urls = getSystemResources(packagePathName);
            }

            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                log.debug(format("正在处理URL文件 : {0}", url.getPath()));
                log.debug(format("URL.PROTOCAL   : {0}", url.getProtocol()));

                if ("file".equals(url.getProtocol())) {
                    classNames.addAll(getClassNames(restoreSpace(url.getFile()), packagePathName));
                }
                else if ("zip".equals(url.getProtocol()) || "jar".equals(url.getProtocol()) || "wsjar".equals(url.getProtocol())) {
                    String jarFileName = restoreSpace(parseJarFileName(url, packagePathName));
                    classNames.addAll(getClassNames(new JarFile(jarFileName), packagePathName));
                }
                else {
                    throw new CoreException(format("无法处理的url协议:{0}", url.getProtocol()));
                }
            }
        }
        catch (Throwable ex) {
            throw new CoreException(ex);
        }

        return classNames;
    }

    private static final String URL_FILE_PATH_HEAD = "file:/";

    private static String parseJarFileName(URL urlOfJar, String packageName) {
        String fileName = urlOfJar.getFile();

        if (fileName.startsWith(URL_FILE_PATH_HEAD)) {
            // 截去前缀
            fileName = fileName.substring(URL_FILE_PATH_HEAD.length());
        }
        CoreException.check(fileName.endsWith("!/" + packageName), "url文件名格式错误");

        // 截去.jar后面的部分
        fileName = fileName.substring(0, fileName.length() - packageName.length() - 2);

        if (isWindowsDir(fileName)) {
        } else {
            fileName = "/" + fileName;
        }
        log.debug("fileName: " + fileName);

        if (isWindowsDir(fileName)) {
        } else {
            fileName = "/" + fileName;
        }

        return fileName;
    }

    private static boolean isWindowsDir(String fileName) {
        String flag = fileName.substring(1, 3);
        return ":/".equals(flag) || ":\\".equals(flag);
    }

    // 把空格编码替换为真正的空格
    private static String restoreSpace(String fileName) {
        return fileName.replaceAll("%20", " ");
    }

    private static final String DOT_CLASS = ".class";

    /**
     * 从jar文件中提取指定包路径下面所有的类名(全名)
     */
    public static List<String> getClassNames(JarFile jarFile, String innerPath) {
        List<String> typeNames = new ArrayList<String>();
        try {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry element = entries.nextElement();

                if (element.getName().startsWith(innerPath) && element.getName().endsWith(DOT_CLASS)) {
                    typeNames.add(toTrueClassName(element.getName()));
                }
            }
        }
        catch (Throwable ex) {
            throw new CoreException(ex);
        }

        return typeNames;
    }

    private static String toTrueClassName(String name) {
        String trueName = name;
        trueName = trueName.replace('/', '.');
        if (trueName.endsWith(DOT_CLASS)) {
            trueName = trueName.substring(0, trueName.length() - DOT_CLASS.length());
        }

        return trueName;
    }

    /**
     * 从指定路径提取指定的类(全名)
     *
     * @param pathName
     * @param packageName
     * @return
     */
    public static List<String> getClassNames(final String pathName, final String packageName) {

        CoreException.check(!packageName.startsWith("/"), "提供的包名格式错误");
        CoreException.check(packageName.endsWith("/"), "提供的包名格式错误");

        List<String> typeNames = new ArrayList<String>();

        File folder = new File(pathName);
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                String subPackageName = packageName + file.getName() + "/";
                typeNames.addAll(getClassNames(file.getAbsolutePath(), subPackageName));
            }
            else {
                String typeName = file.getName();
                if (typeName.endsWith(DOT_CLASS)) {
                    typeName = typeName.substring(0, typeName.length() - DOT_CLASS.length());
                    typeName = packageName + typeName;
                    typeNames.add(toTrueClassName(typeName));
                }
            }
        }
        return typeNames;
    }

    public static List<Class<?>> getClasses(Collection<String> packageNames, Class<? extends Annotation> filter) {
        List<Class<?>> types = new ArrayList<Class<?>>();
        for (String packageName : packageNames) {
            types.addAll(getClasses(packageName));
        }

        List<Class<?>> filterTypes = new ArrayList<Class<?>>();
        for (Class<?> clazz : types) {
            Annotation annotation = clazz.getAnnotation(filter);
            if (annotation != null) {
                filterTypes.add(clazz);
            }
        }
        return filterTypes;
    }

    public static List<Class<?>> getClasses(String packageName) {
        List<Class<?>> types = new ArrayList<Class<?>>();

        List<String> classNames = getClassNames(packageName);
        for (String className : classNames) {
            try {
                Class<?> clazz = Class.forName(className, false, currentThread().getContextClassLoader());
                if (clazz.isInterface()) {
                } else if (clazz.isMemberClass()) {
                } else if (clazz.isAnonymousClass()) {
                } else if (clazz.isEnum()) {
                } else if (clazz.isAnnotation()) {
                } else {
                    types.add(clazz);
                }
            }
            catch (Throwable ex) {
                if (ex instanceof NoClassDefFoundError) {
                } else {
                    throw new CoreException(ex);
                }
            }
        }
        log.debug(format("在\"{0}\"包内共找到{1}个类", packageName, types.size()));
        return types;
    }

    public static String classNamesToString(Class<?>[] classes) {
        return classNamesToString(Arrays.asList(classes));
    }

    public static String classNamesToString(Collection<Class<?>> classes) {
        if (CollectionUtil.isNil(classes)) {
            return "[]";
        }
        StringBuffer sb = new StringBuffer("[");
        Iterator<Class<?>> iterator = classes.iterator();
        while (iterator.hasNext()) {
            Class<?> clazz = iterator.next();
            sb.append(clazz.getName());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static Class<?> getClass(String className) {
        try {
            return getClass(className, true);
        }
        catch (ClassNotFoundException e) {
            throw new CoreException("加载类[" + className + "]出错", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getClassInstance(Class<T> interfaceClazz, Class<?> implClazz) {
        try {
            if (isAssignable(implClazz, interfaceClazz)) {
                return (T) implClazz.newInstance();
            }
        }
        catch (Exception e) {
            throw new CoreException("加载类[" + implClazz + "]出错", e);
        }
        throw new CoreException("加载类[" + implClazz + "]非继承于[" + interfaceClazz + "]");
    }

    public static <T> T getClassInstance(Class<T> interfaceClazz, String className) {
        return getClassInstance(interfaceClazz, getClass(className));
    }
}
