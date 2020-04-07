package com.legocms.service.cms.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.legocms.core.common.Constants;
import com.legocms.core.common.DateUtil;
import com.legocms.core.common.FileUtil;
import com.legocms.core.common.StringUtil;
import com.legocms.core.dto.Page;
import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.cms.CmsCyncFileInfo;
import com.legocms.core.dto.cms.CmsFileInfo;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.exception.ServiceException;
import com.legocms.core.vo.cms.CmsFileTypeCode;
import com.legocms.core.vo.cms.CmsFileVo;
import com.legocms.data.assembler.cms.CmsFileAssembler;
import com.legocms.data.dao.cms.ICmsFileDao;
import com.legocms.data.dao.sys.ISysSiteDao;
import com.legocms.data.entities.cms.CmsFile;
import com.legocms.data.entities.cms.simpletype.CmsFileType;
import com.legocms.data.handler.FileHelper;
import com.legocms.service.BaseService;
import com.legocms.service.cms.ICmsFileService;

@Service
public class CmsFileService extends BaseService implements ICmsFileService {

    @Autowired
    private ICmsFileDao fileDao;

    @Autowired
    private ISysSiteDao siteDao;

    @Autowired
    private CmsFileAssembler fileAssembler;

    @Autowired
    private FileHelper fileHelper;

    @Override
    public CmsFileInfo findBy(String code, String siteCode) {
        CmsFileInfo file = fileAssembler.create(fileDao.findByCode(code));
        String path = FileUtil.getAbsolutePath(file.getPath(), siteCode);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        fileHelper.get(baos, FileUtil.getPath(path), FileUtil.getFile(path));
        file.setContent(baos.toString());
        return file;
    }

    @Override
    public CmsFileInfo findByPath(String path, String siteCode) {
        CmsFile file = fileDao.findByPath(path, siteCode);
        if (file == null) {
            return null;
        }
        return fileAssembler.create(file);
    }

    @Override
    public List<SimpleTreeInfo> findSimpleTree() {
        List<CmsFile> files = fileDao.findAll(new Sort(Sort.Direction.ASC, "type.code", "name"));
        return fileAssembler.createSimpleTree(files);
    }

    @Override
    public Page<CmsFileInfo> findBy(String parentCode, String siteCode, int pageIndex, int pageSize) {
        CmsFile parent = fileDao.findByUnsureCode(parentCode);
        Page<CmsFile> page = fileDao.findBy(parent, siteCode, pageIndex, pageSize);
        page.put("parentCode", "-");
        if (parent == null) {
            page.put("path", "/");
        }
        else {
            if (parent.getParent() != null) {
                page.put("parentCode", parent.getParent().getCode());
            }
            else {
                page.put("parentCode", "");
            }
            page.put("path", parent.getPath());
        }
        return fileAssembler.createInfoPage(page);
    }

    @Override
    public void save(CmsFileVo vo, InputStream ins) {
        CmsFile file = fileDao.findByUnsureCode(vo.getCode());
        if (file == null) {
            file = new CmsFile(vo.getCode());
        }
        String parentPath = "/";
        String path = parentPath + vo.getName();
        if (StringUtil.isNotBlank(vo.getParentCode())) {
            CmsFile parent = fileDao.findByCode(vo.getParentCode());
            file.setParent(parent);
            parentPath = parent.getPath();
            path = parent.getPath() + "/" + vo.getName();
        }
        if (ins != null) {
            try {
                file.setSize(ins.available());
            }
            catch (IOException e) {
                throw new ServiceException("读取文件大小失败！", e);
            }
        }
        file.setPath(path);
        file.setName(vo.getName());
        file.setUpdateTime(DateUtil.getCurrentDate());
        file.setType(commonDao.findByCode(CmsFileType.class, vo.getType()));
        file.setSite(siteDao.findByCode(vo.getSiteCode()));
        if (CmsFileTypeCode.DIR.equals(vo.getType())) {
            fileHelper.create(ins, FileUtil.getAbsolutePath(path, vo.getSiteCode()), null);
        }
        else {
            BusinessException.check(ins != null, "文件内容不能为空！");
            fileHelper.create(ins, FileUtil.getAbsolutePath(parentPath, vo.getSiteCode()), vo.getName());
        }
        fileDao.save(file);
    }

    @Override
    public void delete(String code, String siteCode) {
        CmsFile file = fileDao.findByCode(code);
        boolean isDir = CmsFileTypeCode.DIR.equals(file.getType().getCode());
        fileHelper.delete(FileUtil.getAbsolutePath(file.getPath(), siteCode), isDir);
        fileDao.deleteInBatch(file.getAllChildren());
    }

    @Override
    public void synchronizeDirectory(String parentCode, String siteCode) {
        CmsFile file = fileDao.findByUnsureCode(parentCode);
        String parentPath = "";
        if (file != null) {
            parentPath = file.getPath();
        }
        String path = FileUtil.getAbsolutePath(parentPath, siteCode);
        List<CmsCyncFileInfo> fileInfos = fileHelper.list(path);
        for (CmsCyncFileInfo fileInfo : fileInfos) {
            path = parentPath + Constants.SEPARATOR + fileInfo.getName();
            CmsFile dbFile = fileDao.findBy(parentCode, siteCode, path);
            if (dbFile == null) {
                dbFile = new CmsFile(null);
                if (file != null) {
                    dbFile.setParent(file);
                }
                dbFile.setPath(path);
                dbFile.setName(fileInfo.getName());
                dbFile.setSite(siteDao.findByCode(siteCode));
                dbFile.setType(commonDao.findByCode(CmsFileType.class, fileInfo.getType()));
            }
            dbFile.setSize(fileInfo.getSize());
            dbFile.setUpdateTime(fileInfo.getUpdateTime());
            fileDao.save(dbFile);
        }
        for (CmsFile dbFile : fileDao.findBy(parentCode, siteCode)) {
            boolean exist = false;
            for (CmsCyncFileInfo fileInfo : fileInfos) {
                if (fileInfo.getName().equals(dbFile.getName())) {
                    exist = true;
                    break;
                }
            }
            if (CmsFileTypeCode.DIR.equals(dbFile.getType().getCode())) {
                if (exist) {
                    synchronizeDirectory(dbFile.getCode(), siteCode);
                }
                else {
                    fileDao.deleteInBatch(dbFile.getAllChildren());
                }
            }
            else if (!exist) {
                fileDao.delete(dbFile);
            }
        }
    }
}