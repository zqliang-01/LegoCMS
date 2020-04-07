package com.legocms.service.cms;

import java.util.List;

import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.cms.CmsPlaceInfo;
import com.legocms.core.vo.cms.CmsPlaceVo;

public interface ICmsPlaceService {

    List<SimpleTreeInfo> findSimpleTree();

    CmsPlaceInfo findBy(String code);

    String add(String operator, CmsPlaceVo vo);

    String modify(String operator, CmsPlaceVo vo);

    void delete(String operator, String code);
}
