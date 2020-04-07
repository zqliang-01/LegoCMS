package com.legocms.data.act.cms;

import com.legocms.core.common.FileUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.cms.CmsFileTypeCode;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.DeleteAction;
import com.legocms.data.dao.cms.ICmsFileDao;
import com.legocms.data.entities.cms.CmsFile;
import com.legocms.data.handler.FileHelper;

public class DeleteCmsFileAction extends DeleteAction<CmsFile> {

    private String code;
    private String siteCode;
    private FileHelper fileHelper;

    private ICmsFileDao fileDao = getDao(ICmsFileDao.class);

    public DeleteCmsFileAction(String operator, String code, String siteCode, FileHelper fileHelper) {
        super(SysPermissionCode.FILE, operator);
        this.code = code;
        this.siteCode = siteCode;
        this.fileHelper = fileHelper;
    }

    @Override
    protected void preprocess() {
        CmsFile file = fileDao.findByUnsureCode(code);
        BusinessException.check(file != null, "不存在编码为[{0}]的文件信息，文件删除失败！", code);
    }

    @Override
    protected void destory(CmsFile file) {
        boolean isDir = CmsFileTypeCode.DIR.equals(file.getType().getCode());
        fileHelper.delete(FileUtil.getAbsolutePath(file.getPath(), siteCode), isDir);
        fileDao.deleteInBatch(file.getAllChildren());
    }

}
