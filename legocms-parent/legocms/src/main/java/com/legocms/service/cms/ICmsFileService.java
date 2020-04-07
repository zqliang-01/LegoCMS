package com.legocms.service.cms;

import java.io.InputStream;

import com.legocms.core.dto.Page;
import com.legocms.core.dto.cms.CmsFileInfo;
import com.legocms.core.vo.cms.CmsFileVo;

public interface ICmsFileService {

    CmsFileInfo findBy(String code, String siteCode);

    Page<CmsFileInfo> findBy(String parentCode, String siteCode, int pageIndex, int pageSize);

    void save(CmsFileVo vo, InputStream ins);

    void delete(String code, String siteCode);
}
