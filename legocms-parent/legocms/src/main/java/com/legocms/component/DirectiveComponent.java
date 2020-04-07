package com.legocms.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.common.Constants;
import com.legocms.core.common.StringUtil;
import com.legocms.loader.DatabasePlaceLoader;
import com.legocms.loader.DatabaseTemplateLoader;
import com.legocms.web.directive.ControllerTemplateDirective;

import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateModelException;
import lombok.Data;

@Data
@Component
public class DirectiveComponent {
    private Map<String, ControllerTemplateDirective> templateDirectiveMap = new HashMap<String, ControllerTemplateDirective>();

    @Autowired
    private TemplateComponent templateComponent;

    @Autowired
    private PlaceComponent placeComponent;

    @Autowired
    private DatabaseTemplateLoader templateLoader;

    @Autowired
    private DatabasePlaceLoader placeLoader;

    @Autowired
    private void init(Configuration freeMarkerConfigurer, List<ControllerTemplateDirective> templateDirectiveList) throws TemplateModelException {
        for (ControllerTemplateDirective templateDirective : templateDirectiveList) {
            if (templateDirective.getName() == null) {
                String simpleName = templateDirective.getClass().getSimpleName();
                String name = StringUtil.uncapitalize(simpleName.replaceAll(Constants.DIRECTIVE_REMOVE_REGEX, Constants.BLANK));
                templateDirective.setName(name);
            }
            templateDirectiveMap.put(templateDirective.getName(), templateDirective);
        }
        freeMarkerConfigurer.setAllSharedVariables(new SimpleHash(templateDirectiveMap, freeMarkerConfigurer.getObjectWrapper()));

        Configuration templaceConfiguration = new Configuration(Configuration.getVersion());
        templaceConfiguration.setTemplateLoader(new MultiTemplateLoader(new TemplateLoader[]{templateLoader}));
        copyConfig(freeMarkerConfigurer, templaceConfiguration);
        templaceConfiguration.setAllSharedVariables(new SimpleHash(templateDirectiveMap, templaceConfiguration.getObjectWrapper()));
        templateComponent.setTemplaceConfiguration(templaceConfiguration);

        Configuration placeConfiguration = new Configuration(Configuration.getVersion());
        placeConfiguration.setTemplateLoader(new MultiTemplateLoader(new TemplateLoader[]{placeLoader}));
        copyConfig(freeMarkerConfigurer, placeConfiguration);
        placeConfiguration.setAllSharedVariables(new SimpleHash(templateDirectiveMap, placeConfiguration.getObjectWrapper()));
        placeComponent.setPlaceConfiguration(placeConfiguration);
    }

    private void copyConfig(Configuration source, Configuration target) {
        target.setNewBuiltinClassResolver(source.getNewBuiltinClassResolver());
        target.setTemplateUpdateDelayMilliseconds(source.getTemplateUpdateDelayMilliseconds());
        target.setDefaultEncoding(source.getDefaultEncoding());
        target.setLocale(source.getLocale());
        target.setBooleanFormat(source.getBooleanFormat());
        target.setDateTimeFormat(source.getDateTimeFormat());
        target.setDateFormat(source.getDateFormat());
        target.setTimeFormat(source.getTimeFormat());
        target.setNumberFormat(source.getNumberFormat());
        target.setOutputFormat(source.getOutputFormat());
        target.setURLEscapingCharset(source.getURLEscapingCharset());
        target.setLazyAutoImports(source.getLazyAutoImports());
        target.setTemplateExceptionHandler(source.getTemplateExceptionHandler());
    }

}
