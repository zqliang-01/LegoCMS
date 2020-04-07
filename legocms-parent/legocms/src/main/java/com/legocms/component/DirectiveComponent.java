package com.legocms.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.common.Constants;
import com.legocms.core.common.StringUtil;
import com.legocms.web.directive.AbstractTemplateDirective;

import freemarker.template.Configuration;
import lombok.Data;

@Data
@Component
public class DirectiveComponent {
    private Map<String, AbstractTemplateDirective> templateDirectiveMap = new HashMap<String, AbstractTemplateDirective>();

    @Autowired
    private void init(Configuration freeMarkerConfigurer, List<AbstractTemplateDirective> templateDirectiveList) {
        for (AbstractTemplateDirective templateDirective : templateDirectiveList) {
            if (templateDirective.getName() == null) {
                String simpleName = templateDirective.getClass().getSimpleName();
                String name = StringUtil.uncapitalize(simpleName.replaceAll(Constants.DIRECTIVE_REMOVE_REGEX, Constants.BLANK));
                templateDirective.setName(name);
            }
            templateDirectiveMap.put(templateDirective.getName(), templateDirective);
            freeMarkerConfigurer.setSharedVariable(templateDirective.getName(), templateDirective);
        }
    }

}
