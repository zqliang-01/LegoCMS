package com.legocms.core.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.legocms.core.exception.CoreException;

public class FtpHelper {
	private Logger logger = LoggerFactory.getLogger(FtpHelper.class);

	private FTPClient ftp;
	private String serverIP;
	private String username;
	private String password;

	public FtpHelper(String serverIP, String username, String password) {
		this.serverIP = serverIP;
		this.username = username;
		this.password = password;
	}

    public boolean open(String path) {
        try {
            ftp = new FTPClient();
            ftp.connect(serverIP);
            ftp.login(username, password);
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return false;
            }
            ftp.changeWorkingDirectory(path);
            return true;
        }
        catch (Exception e) {
            throw new CoreException(e);
        }
    }

    public boolean open() {
        try {
            ftp = new FTPClient();
            ftp.connect(serverIP);
            ftp.login(username, password);
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return false;
            }
            ftp.changeWorkingDirectory("/");
            return true;
        }
        catch (Exception e) {
            throw new CoreException(e);
        }
    }

    public Set<String> getAllFileName(String dirName) {
        FTPFile[] files = getFiles(dirName);
        Set<String> fileSet = new HashSet<String>();
        if (null != files && files.length > 0) {
            String dir = "";
            for (FTPFile file : files) {
                String name = file.getName();
                if (file.isDirectory()) {
                    if (!name.equals(dir)) {
                        dir = file.getName();
                        getAllFileName(dirName + "/" + name);
                    }
                }
                else {
                    fileSet.add(dirName + "/" + name);
                }
            }
        }
        return fileSet;
    }

    private FTPFile[] getFiles(String dirName) {
        try {
            ftp.changeWorkingDirectory(dirName);
            return ftp.listFiles(dirName);
        }
        catch (Exception e) {
            throw new CoreException(e);
        }
    }

    public void upload(File file, String path) throws Exception {
        ftp.makeDirectory(path);
        ftp.changeWorkingDirectory(path);
        if (file.isDirectory()) {
            String[] files = file.list();
            for (int i = 0; i < files.length; i++) {
                File file1 = new File(file.getPath() + File.separator + files[i]);
                if (file1.isDirectory()) {
                    upload(file1, path);
                    ftp.changeToParentDirectory();
                }
                else {
                    File file2 = new File(file.getPath() + File.separator + files[i]);
                    FileInputStream input = new FileInputStream(file2);
                    try {
                        ftp.storeFile(file2.getName(), input);
                    }
                    catch (Exception e) {
                        throw new CoreException(e);
                    }
                    finally {
                        input.close();
                    }
                    logger.info(file2.getName() + "上传成功！");
                }
            }
        }
        else {
            FileInputStream input = new FileInputStream(file);
            try {
                ftp.storeFile(file.getName(), input);
            }
            catch (Exception e) {
                throw new CoreException(e);
            }
            finally {
                input.close();
            }
            logger.info(file.getName() + "上传成功！");
        }
    }

    public void download(Set<String> fileNames, String localPath) throws Exception {
        File localFile = new File(localPath);
        if (!localFile.exists()) {
            localFile.mkdirs();
        }
        for (String fileName : fileNames) {
            String extension = FileUtil.getFileType(fileName);// 扩展名
            String mix = String.format("%s.%s", fileName, extension);// 文件名+扩展名
            File filePath = new File(localFile.getAbsolutePath() + File.separator + mix);
            if (!filePath.exists()) {
                filePath.createNewFile();
            }

            // 输出流
            OutputStream os = new FileOutputStream(filePath);
            // 下载文件
            try {
                ftp.retrieveFile(fileName, os);
            }
            catch (Exception e) {
                throw e;
            }
            finally {
                os.close();
            }
        }
    }

    public void delete(String pathname, boolean directory) throws Exception {
        boolean flag = false;
        if (directory) {
            flag = ftp.removeDirectory(pathname);// 删文件夹
        }
        else {
            flag = ftp.deleteFile(pathname);// 删文件
        }
        logger.info(pathname + "--->删除状态:" + flag);
    }

    public void delete(String filePath, Set<String> ignores) throws Exception {
        boolean flag = ftp.deleteFile(filePath);
        if (flag) {
            String dirName = filePath.substring(0, filePath.lastIndexOf("/"));// 这里只考虑了/的情况,\未考虑
            FTPFile[] files = ftp.listFiles(dirName);
            if (files.length == 0) {// 若文件夹为空,则文件夹也删了
                if (null != ignores && !ignores.contains(dirName)) {
                    ftp.removeDirectory(dirName);// 删文件夹
                }
            }
        }
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
}
