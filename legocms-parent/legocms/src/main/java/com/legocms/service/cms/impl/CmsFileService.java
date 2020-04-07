package com.legocms.service.cms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legocms.core.common.DateUtil;
import com.legocms.core.common.StringUtil;
import com.legocms.core.dto.Page;
import com.legocms.core.dto.cms.CmsFileInfo;
import com.legocms.core.vo.cms.CmsFileVo;
import com.legocms.data.assembler.cms.CmsFileAssembler;
import com.legocms.data.dao.cms.ICmsFileDao;
import com.legocms.data.entities.cms.CmsFile;
import com.legocms.data.entities.cms.simpletype.CmsFileType;
import com.legocms.service.BaseService;
import com.legocms.service.cms.ICmsFileService;

@Service
public class CmsFileService extends BaseService implements ICmsFileService {

    @Autowired
    private ICmsFileDao fileDao;

    @Autowired
    private CmsFileAssembler fileAssembler;

    @Override
    public Page<CmsFileInfo> findBy(String parentCode, String siteCode, int pageIndex, int pageSize) {
        Page<CmsFile> page = fileDao.findBy(parentCode, siteCode, pageIndex, pageSize);
        return fileAssembler.createInfoPage(page);
    }

    @Override
    public void save(CmsFileVo vo) {
        CmsFile file = fileDao.findByUnsureCode(vo.getCode());
        if (file == null) {
            file = new CmsFile(vo.getCode());
        }
        String path = "/";
        if (StringUtil.isNotBlank(vo.getParentCode())) {
            CmsFile parent = fileDao.findByCode(vo.getParentCode());
            file.setParent(parent);
        }
        file.setUpdateTime(DateUtil.getCurrentDate());
        file.setType(commonDao.findByCode(CmsFileType.class, vo.getType()));
    }

}
