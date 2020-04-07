package com.legocms.data.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.exception.CoreException;

@Component
@ConditionalOnProperty(name = "spring.session.store-type", havingValue = "none")
public class LocalFileHandler implements FileHelper {

    @Override
    public void create(InputStream ins, String path, String name) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (StringUtil.isBlank(name)) {
            return;
        }
        try {
            int length;
            byte[] b = new byte[1024];
            file = new File(path, name);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            while((length = ins.read(b)) > 0){
                fos.write(b, 0 ,length);
            }
            fos.close();
            ins.close();
        }
        catch (Exception e) {
            throw new CoreException(e);
        }
    }

    @Override
    public void get(OutputStream os, String path, String name) {
        File file = new File(path, name);
        BusinessException.check(file.exists(), "文件[" + name + "]不存在");
        try {
            int len = -1;
            byte[] bs = new byte[1024];
            FileInputStream ins = new FileInputStream(file);
            while((len = ins.read(bs)) != -1){
                os.write(bs, 0, len);
            }
            ins.close();
        }
        catch (Exception e) {
            throw new CoreException(e);
        }
    }

    @Override
    public void delete(String path, boolean directory) {
        if (directory) {
            delete(path);
        }
        else {
            File file = new File(path);
            file.delete();
        }
    }

    public void delete(String path) {
        File parentFile = new File(path);
        File[] files = parentFile.listFiles();
        if (null != files && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    delete(path + "/" + file.getName());
                    // 切换到父目录，不然删不掉文件夹
                    file.delete();
                }
                else {
                    file.delete();
                }
            }
        }
        parentFile.delete();
    }
}
