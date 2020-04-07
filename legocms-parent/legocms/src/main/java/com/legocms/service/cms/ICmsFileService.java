package com.legocms.service.cms;

import java.io.InputStream;
import java.util.List;

import com.legocms.core.dto.Page;
import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.cms.CmsFileInfo;
import com.legocms.core.vo.cms.CmsFileVo;

public interface ICmsFileService {

    CmsFileInfo findBy(String code, String siteCode);

    CmsFileInfo findByPath(String path, String siteCode);

    List<SimpleTreeInfo> findSimpleTree();

    Page<CmsFileInfo> findBy(String parentCode, String siteCode, int pageIndex, int pageSize);

    void add(String operator, CmsFileVo vo, InputStream ins);

    void modify(String operator, CmsFileVo vo, InputStream ins);

    void delete(String operator, String code, String siteCode);

    void synchronizeDirectory(String operator, String parentCode, String siteCode);
}
