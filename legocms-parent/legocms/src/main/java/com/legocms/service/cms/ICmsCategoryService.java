package com.legocms.service.cms;

import java.util.List;

import com.legocms.core.dto.Page;
import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.TypeInfo;
import com.legocms.core.dto.cms.CmsCategoryInfo;
import com.legocms.core.dto.cms.CmsCategorySimpleInfo;
import com.legocms.core.vo.cms.CmsCategoryVo;

public interface ICmsCategoryService {

    List<SimpleTreeInfo> findSimpleTree(String status);

    String add(String operator, String siteCode, CmsCategoryVo vo);

    String modify(String operator, String siteCode, CmsCategoryVo vo);

    Page<CmsCategorySimpleInfo> findBy(String code, String name, String status, String parentCode, int pageIndex, int pageSize);

    TypeInfo findByCode(String code);

    CmsCategoryInfo findInfoBy(String code);
}
