package com.legocms.service.cms.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.legocms.core.common.FileUtil;
import com.legocms.core.dto.Page;
import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.cms.CmsFileInfo;
import com.legocms.core.vo.cms.CmsFileVo;
import com.legocms.data.act.cms.AddCmsFileAction;
import com.legocms.data.act.cms.DeleteCmsFileAction;
import com.legocms.data.act.cms.ModifyCmsFileAction;
import com.legocms.data.act.cms.SynchronizeCmsFileAction;
import com.legocms.data.assembler.cms.CmsFileAssembler;
import com.legocms.data.dao.cms.ICmsFileDao;
import com.legocms.data.dao.sys.ISysSiteDao;
import com.legocms.data.entities.cms.CmsFile;
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
        List<CmsFile> files = fileDao.findAll(Sort.by("type.code", "name"));
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
    public void add(String operator, CmsFileVo vo, InputStream ins) {
        new AddCmsFileAction(operator, vo, ins, fileHelper).run();
    }

    @Override
    public void modify(String operator, CmsFileVo vo, InputStream ins) {
        new ModifyCmsFileAction(operator, vo, ins, fileHelper).run();
    }

    @Override
    public void delete(String operator, String code, String siteCode) {
        new DeleteCmsFileAction(operator, code, siteCode, fileHelper).run();
    }

    @Override
    public void synchronizeDirectory(String operator, String parentCode, String siteCode) {
        new SynchronizeCmsFileAction(operator, parentCode, siteCode, fileHelper).run();
    }
}