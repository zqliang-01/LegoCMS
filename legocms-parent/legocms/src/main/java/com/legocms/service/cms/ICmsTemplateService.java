package com.legocms.service.cms;

import java.util.List;

import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.cms.CmsTemplateInfo;
import com.legocms.core.vo.cms.CmsTemplateVo;

public interface ICmsTemplateService {

    List<SimpleTreeInfo> findSimpleTree();

    CmsTemplateInfo findBy(String code);

    void save(CmsTemplateVo vo);

    void delete(String code);
}
