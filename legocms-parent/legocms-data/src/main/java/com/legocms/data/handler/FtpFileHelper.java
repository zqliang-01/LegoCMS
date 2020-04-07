package com.legocms.data.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.legocms.core.common.Constants;
import com.legocms.core.common.StringUtil;
import com.legocms.core.dto.cms.CmsCyncFileInfo;
import com.legocms.core.exception.CoreException;
import com.legocms.core.vo.cms.CmsFileTypeCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnProperty(name = "spring.session.store-type", havingValue = "redis")
public class FtpFileHelper implements FileHelper {
	private Logger logger = LoggerFactory.getLogger(FtpFileHelper.class);

	private FTPClient ftp;

	@Value("${ftp.ip}")
	private String serverIP;

    @Value("${ftp.username}")
	private String username;

    @Value("${ftp.password}")
	private String password;

    public boolean open() {
        try {
            ftp = new FTPClient();
            ftp.connect(serverIP);
            ftp.login(username, password);
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.setControlEncoding(Constants.DEFAULT_CHARSET_NAME);
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return false;
            }
            ftp.changeWorkingDirectory("/");
            log.debug("创建FTP连接！");
            return true;
        }
        catch (Exception e) {
            throw new CoreException(e);
        }
    }

    @Override
    public void create(InputStream ins, String path, String name) {
        try {
            open();
            ftp.makeDirectory(path);
            ftp.changeWorkingDirectory(path);
            if (StringUtil.isNotBlank(name) && ins != null) {
                ftp.storeFile(name, ins);
            }
        }
        catch(Exception e) {
            throw new CoreException(e);
        }
        finally {
            close();
        }
    }

    @Override
    public void get(OutputStream os, String path, String name) {
        try {
            open();
            ftp.changeWorkingDirectory(path);
            ftp.retrieveFile(name, os);
        }
        catch (Exception e) {
            throw new CoreException(e);
        }
        finally {
            close();
        }
    }

    @Override
    public InputStream get(String path, String name) {
        try {
            open();
            ftp.changeWorkingDirectory(path);
            return ftp.retrieveFileStream(path + Constants.SEPARATOR + name);
        }
        catch (Exception e) {
            throw new CoreException(e);
        }
        finally {
            close();
        }
    }

    @Override
    public void delete(String path, boolean directory) {
        try {
            open();
            if (directory) {
                delete(path);
            }
            else {
                ftp.deleteFile(path);
            }
        }
        catch (Exception e) {
            throw new CoreException(e);
        }
        finally {
            close();
        }
    }

    public void delete(String path) throws Exception {
        FTPFile[] files = ftp.listFiles(path);
        if (null != files && files.length > 0) {
            for (FTPFile file : files) {
                if (file.isDirectory()) {
                    delete(path + "/" + file.getName());
                    // 切换到父目录，不然删不掉文件夹
                    ftp.changeWorkingDirectory(path.substring(0, path.lastIndexOf("/")));
                    ftp.removeDirectory(path);
                }
                else {
                    ftp.deleteFile(path + "/" + file.getName());
                }
            }
        }
        // 切换到父目录，不然删不掉文件夹
        ftp.changeWorkingDirectory(path.substring(0, path.lastIndexOf("/")));
        ftp.removeDirectory(path);
    }

    public void close() {
        if (null != ftp) {
            if (ftp.isConnected()) {
                try {
                    ftp.logout();
                    ftp.disconnect();
                }
                catch (Throwable e) {
                    logger.error("连接断开失败，错误原因", e);
                }
            }
        }
    }

    @Override
    public List<CmsCyncFileInfo> list(String path) {
        List<CmsCyncFileInfo> infos = new ArrayList<CmsCyncFileInfo>();
        try {
            open();
            FTPFile[] files = ftp.listFiles(path);
            for (FTPFile file : files) {
                CmsCyncFileInfo info = new CmsCyncFileInfo();
                info.setName(file.getName());
                info.setPath(path + "/" + file.getName());
                info.setUpdateTime(file.getTimestamp().getTime());
                if (file.isDirectory()) {
                    info.setType(CmsFileTypeCode.DIR);
                }
                else {
                    info.setSize(file.getSize());
                    info.setType(CmsFileTypeCode.FILE);
                }
                infos.add(info);
            }
        }
        catch (IOException e) {
            throw new CoreException(e);
        }
        finally {
            close();
        }
        return infos;
    }
}