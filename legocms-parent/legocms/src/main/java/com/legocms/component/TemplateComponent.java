package com.legocms.component;

import org.springframework.stereotype.Component;

import freemarker.template.Configuration;
import lombok.Data;

@Data
@Component
public class TemplateComponent {

    private Configuration adminConfiguration;
    private Configuration webConfiguration;
}
