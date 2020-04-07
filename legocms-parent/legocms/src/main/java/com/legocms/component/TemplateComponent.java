package com.legocms.component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.legocms.core.exception.ServiceException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.Data;

@Data
@Component
public class TemplateComponent {

    private Configuration templaceConfiguration;

    public String generateStringByTemplate(String code, Map<String, Object> input) {
        Template template = this.getTemplate(code);
        StringWriter stringWriter = new StringWriter();
        if (Objects.isNull(template)) {
            throw new ServiceException("不存在模板信息：" + code);
        }
        try {
            template.process(input, stringWriter);
        }
        catch (TemplateException | IOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return stringWriter.toString();
    }

    public String generateStringByString(String templateContent, Map<String, Object> input) {
        StringWriter stringWriter = new StringWriter();
        try {
            Template template = new Template(null, templateContent, templaceConfiguration);
            template.process(input, stringWriter);
        }
        catch (TemplateException | IOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return stringWriter.toString();
    }

    public Template getTemplate(String code) {
        try {
            templaceConfiguration.clearTemplateCache();
            return templaceConfiguration.getTemplate(code);
        }
        catch (IOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
