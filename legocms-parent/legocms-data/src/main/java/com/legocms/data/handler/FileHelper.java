package com.legocms.data.handler;

import java.io.InputStream;
import java.io.OutputStream;

public interface FileHelper {

    void create(InputStream ins, String path, String name);

    void get(OutputStream os, String path, String name);

    void delete(String path, boolean directory);
}
