package com.legocms.loader;

import java.io.Reader;
import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.data.dao.cms.ICmsTemplateDao;
import com.legocms.data.entities.cms.CmsTemplate;

import freemarker.cache.TemplateLoader;

@Component
public class DatabaseTemplateLoader implements TemplateLoader {

    @Autowired
    private ICmsTemplateDao templateDao;

    @Override
    public Object findTemplateSource(String code) {
        code = code.split("_")[0];
        CmsTemplate template = templateDao.findByCode(code);
        return new StringTemplateSource(code, template.getContent().replaceAll("\r|\n|\t", ""), template.getUpdateTime().getTime());
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
        private final String code;
        private final String source;
        private final long lastModified;

        StringTemplateSource(String code, String source, long lastModified) {
            if (code == null) {
                throw new IllegalArgumentException("code == null");
            }
            if (source == null) {
                throw new IllegalArgumentException("source == null");
            }
            if (lastModified < -1L) {
                throw new IllegalArgumentException("lastModified < -1L");
            }
            this.code = code;
            this.source = source;
            this.lastModified = lastModified;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof StringTemplateSource) {
                return code.equals(((StringTemplateSource) obj).code);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return code.hashCode();
        }
    }
}