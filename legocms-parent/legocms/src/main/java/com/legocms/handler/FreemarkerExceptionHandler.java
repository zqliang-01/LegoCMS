package com.legocms.handler;

import java.io.IOException;
import java.io.Writer;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FreemarkerExceptionHandler implements TemplateExceptionHandler {

    public void handleTemplateException(TemplateException templateexception, Environment environment, Writer writer) throws TemplateException {
        try {
            String code = templateexception.getFTLInstructionStack();
            if ((code != null) && (code.indexOf("Failed at: ") > 0) && (code.indexOf("  [") > 0)) {
                writer.write("error:" + code.substring(code.indexOf("Failed at: ") + 11, code.indexOf("  [")));
            }
            else writer.write("[some errors occurred!]");
        }
        catch (IOException e) {
            log.error(environment.getCurrentTemplate().getSourceName(), e);
        }
    }
}
