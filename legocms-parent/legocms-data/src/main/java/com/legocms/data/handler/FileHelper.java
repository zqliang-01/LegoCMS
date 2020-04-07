package com.legocms.data.handler;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.legocms.core.dto.cms.CmsCyncFileInfo;

public interface FileHelper {

    void create(InputStream ins, String path, String name);

    void get(OutputStream os, String path, String name);

    InputStream get(String path, String name);

    void delete(String path, boolean directory);

    List<CmsCyncFileInfo> list(String path);
}
