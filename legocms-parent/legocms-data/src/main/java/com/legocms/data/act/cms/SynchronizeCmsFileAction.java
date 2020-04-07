package com.legocms.data.act.cms;

import java.util.List;

import com.legocms.core.common.Constants;
import com.legocms.core.common.FileUtil;
import com.legocms.core.dto.cms.CmsCyncFileInfo;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.cms.CmsFileTypeCode;
import com.legocms.core.vo.sys.ActionTypeCode;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.MaintainAction;
import com.legocms.data.dao.cms.ICmsFileDao;
import com.legocms.data.dao.sys.ISysSiteDao;
import com.legocms.data.entities.cms.CmsFile;
import com.legocms.data.entities.cms.simpletype.CmsFileType;
import com.legocms.data.entities.sys.SysSite;
import com.legocms.data.entities.sys.simpletype.ActionType;
import com.legocms.data.handler.FileHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SynchronizeCmsFileAction extends MaintainAction<CmsFile> {

    private String siteCode;
    private String parentPath;
    private String parentCode;

    private SysSite site;
    private FileHelper fileHelper;

    private ISysSiteDao siteDao = getDao(ISysSiteDao.class);
    private ICmsFileDao fileDao = getDao(ICmsFileDao.class);

    public SynchronizeCmsFileAction(String operator, String parentCode, String siteCode, FileHelper fileHelper) {
        super(SysPermissionCode.FILE, operator);
        this.siteCode = siteCode;
        this.parentCode = parentCode;
        this.fileHelper = fileHelper;
    }

    @Override
    protected void preprocess() {
        site = siteDao.findByUnsureCode(siteCode);
        BusinessException.check(site != null, "无在用站点，刷新目录失败！");

        CmsFile parent = fileDao.findByUnsureCode(parentCode);
        if (parent == null) {
            parentPath = Constants.SEPARATOR;
        }
        else {
            parentPath = parent.getPath();
        }
    }

    @Override
    protected void doRun() {
        try {
            long beginTime = System.currentTimeMillis();
            synchronizeDirectory(parentCode);
            long runTime = System.currentTimeMillis() - beginTime;
            setDescription("目录[" + parentPath + "]刷新成功，共耗时：" + runTime + "ms");
        }
        catch (Exception e) {
            setDescription("目录[" + parentPath + "]刷新失败，" + e.getMessage());
            log.error(e.getMessage(), e);
        }
    }

    @Override
    protected ActionType getActionType() {
        return commonDao.findByCode(ActionType.class, ActionTypeCode.SYNCHRONIZE);
    }

    public void synchronizeDirectory(String parentCode) {
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
                dbFile.setSite(site);
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
                    synchronizeDirectory(dbFile.getCode());
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
