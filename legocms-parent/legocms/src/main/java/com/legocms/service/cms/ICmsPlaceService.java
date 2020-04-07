package com.legocms.service.cms;

import java.util.List;

import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.cms.CmsPlaceInfo;
import com.legocms.core.vo.cms.CmsPlaceVo;

public interface ICmsPlaceService {

    List<SimpleTreeInfo> findSimpleTree();

    CmsPlaceInfo findBy(String code);

    void save(CmsPlaceVo vo);

    void delete(String code);
}
