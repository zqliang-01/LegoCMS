package com.legocms.component;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.legocms.core.exception.ServiceException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.Data;

@Data
@Component
public class PlaceComponent {
    private Configuration placeConfiguration;

    public void generateStringByWriter(Writer writer, String code, Map<String, Object> input) {
        try {
            Template template = getTemplate(code);
            template.process(input, writer);
        }
        catch (TemplateException | IOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private Template getTemplate(String code) {
        try {
            placeConfiguration.clearTemplateCache();
            return placeConfiguration.getTemplate(code);
        }
        catch (IOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
