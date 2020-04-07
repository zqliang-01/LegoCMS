package com.legocms.service.cms;

import java.util.List;

import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.cms.CmsModelInfo;
import com.legocms.core.vo.cms.CmsModelVo;

public interface ICmsModelService {

    List<SimpleTreeInfo> findSimpleTree();

    CmsModelInfo findBy(String code);

    String add(String operator, String siteCode, CmsModelVo vo);

    String modify(String operator, String siteCode, CmsModelVo vo);

    void delete(String operator, String code);

    List<CmsModelInfo> findByParent(String parentCode);
}
