package com.legocms.loader;

import java.io.Reader;
import java.io.StringReader;

import org.springframework.stereotype.Component;

import freemarker.cache.TemplateLoader;

@Component("databaseTemplateLoader")
public class DatabaseTemplateLoader implements TemplateLoader {

    @Override
    public Object findTemplateSource(String name) {
        try {
            Integer templateId = Integer.valueOf(name);
            //通过id查询数据库中配置的模板信息
            //数据库表必须有一个最后更新字段用来刷新缓存,数据库中的模板保存字段为query,这里通过model.getQuery获取
            return new StringTemplateSource(name, null, 0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public long getLastModified(Object templateSource) {
        return ((StringTemplateSource) templateSource).lastModified;
    }

    @Override
    public Reader getReader(Object templateSource, String encoding) {
        return new StringReader(((StringTemplateSource) templateSource).source);
    }

    @Override
    public void closeTemplateSource(Object templateSource) { }

    private static class StringTemplateSource {
        private final String name;
        private final String source;
        private final long lastModified;

        StringTemplateSource(String name, String source, long lastModified) {
            if (name == null) {
                throw new IllegalArgumentException("name == null");
            }
            if (source == null) {
                throw new IllegalArgumentException("source == null");
            }
            if (lastModified < -1L) {
                throw new IllegalArgumentException("lastModified < -1L");
            }
            this.name = name;
            this.source = source;
            this.lastModified = lastModified;
        }

        public boolean equals(Object obj) {
            if (obj instanceof StringTemplateSource) {
                return name.equals(((StringTemplateSource) obj).name);
            }
            return false;
        }

        public int hashCode() {
            return name.hashCode();
        }
    }
}