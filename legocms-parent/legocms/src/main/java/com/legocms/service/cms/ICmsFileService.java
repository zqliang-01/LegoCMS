package com.legocms.service.cms;

import com.legocms.core.dto.Page;
import com.legocms.core.dto.cms.CmsFileInfo;
import com.legocms.core.vo.cms.CmsFileVo;

public interface ICmsFileService {

    Page<CmsFileInfo> findBy(String parentCode, String siteCode, int pageIndex, int pageSize);

    void save(CmsFileVo vo);
}
