package com.legocms.data.act.cms;

import java.io.IOException;
import java.io.InputStream;

import com.legocms.core.common.DateUtil;
import com.legocms.core.common.FileUtil;
import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.exception.CoreException;
import com.legocms.core.vo.cms.CmsFileTypeCode;
import com.legocms.core.vo.cms.CmsFileVo;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.ModifyAction;
import com.legocms.data.dao.cms.ICmsFileDao;
import com.legocms.data.dao.sys.ISysSiteDao;
import com.legocms.data.entities.cms.CmsFile;
import com.legocms.data.entities.cms.simpletype.CmsFileType;
import com.legocms.data.entities.sys.SysSite;
import com.legocms.data.handler.FileHelper;

public class ModifyCmsFileAction extends ModifyAction<CmsFile> {

    private long size;
    private String path;
    private String parentPath;
    private InputStream inputStream;

    private CmsFileVo vo;
    private SysSite site;
    private CmsFile parent;
    private CmsFileType type;
    private FileHelper fileHelper;

    private ISysSiteDao siteDao = getDao(ISysSiteDao.class);
    private ICmsFileDao fileDao = getDao(ICmsFileDao.class);

    public ModifyCmsFileAction(String operator, CmsFileVo vo, InputStream inputStream, FileHelper fileHelper) {
        super(SysPermissionCode.FILE, operator);
        this.vo = vo;
        this.inputStream = inputStream;
        this.fileHelper = fileHelper;
    }

    @Override
    protected void preprocess() {
        site = siteDao.findByUnsureCode(vo.getSiteCode());
        BusinessException.check(site != null, "无在用站点，文件修改失败！");

        CmsFile file = fileDao.findByUnsureCode(vo.getCode());
        BusinessException.check(file != null, "不存在编码为[{0}]的文件信息，文件修改失败！", vo.getCode());

        type = commonDao.findByUnsureCode(CmsFileType.class, vo.getType());
        BusinessException.check(type != null, "不存在编码为[{0}]的文件类型，文件修改失败！", vo.getType());

        parentPath = "/";
        path = parentPath + vo.getName();

        if (StringUtil.isNotBlank(vo.getParentCode())) {
            parent = fileDao.findByCode(vo.getParentCode());
            parentPath = parent.getPath();
            path = parent.getPath() + "/" + vo.getName();
        }
        if (CmsFileTypeCode.FILE.equals(vo.getType())) {
            try {
                BusinessException.check(inputStream != null, "文件内容不能为空！");
                size = inputStream.available();
            }
            catch (IOException e) {
                throw new CoreException("读取文件内容出错，文件修改失败！", e);
            }
        }

        setTargetEntity(file);
    }

    @Override
    protected void doModify(CmsFile file) {
        file.setPath(path);
        file.setSize(size);
        file.setName(vo.getName());
        file.setUpdateTime(DateUtil.getCurrentDate());
        file.setType(type);
        file.setSite(site);
        file.setParent(parent);
        if (CmsFileTypeCode.DIR.equals(vo.getType())) {
            fileHelper.create(inputStream, FileUtil.getAbsolutePath(path, vo.getSiteCode()), null);
        }
        else {
            fileHelper.create(inputStream, FileUtil.getAbsolutePath(parentPath, vo.getSiteCode()), vo.getName());
        }
        fileDao.save(file);
    }

}
