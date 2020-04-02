package com.legocms.web.directive;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legocms.handler.TemplateDirectiveHandler;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public abstract class BaseTemplateDirective implements TemplateDirectiveModel, Directive, HttpDirective {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private String name;

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void execute(Environment environment, Map parameters, TemplateModel[] loopVars, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        try {
            execute(new TemplateDirectiveHandler(parameters, loopVars, environment, templateDirectiveBody));
        }
        catch (IOException e) {
            throw e;
        }
        catch (Exception e) {
            throw new TemplateException(e, environment);
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
