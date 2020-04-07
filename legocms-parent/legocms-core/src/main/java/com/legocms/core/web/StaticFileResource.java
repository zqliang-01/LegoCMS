package com.legocms.core.web;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.InputStreamResource;

import com.legocms.core.dto.cms.CmsFileInfo;

public class StaticFileResource extends InputStreamResource {

    private CmsFileInfo file;

    public StaticFileResource(InputStream inputStream, CmsFileInfo file) {
        super(inputStream);
        this.file = file;
    }

    @Override
    public boolean exists() {
        return file != null;
    }

    @Override
    public String getFilename() {
        return file.getName();
    }

    @Override
    public long lastModified() throws IOException {
        return file.getUpdateTime().getTime();
    }

    @Override
    public long contentLength() throws IOException {
        return file.getSize();
    }
}
