package com.legocms.service.cms;

import java.util.List;

import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.cms.CmsTemplateInfo;
import com.legocms.core.vo.cms.CmsTemplateVo;

public interface ICmsTemplateService {

    List<SimpleTreeInfo> findSimpleTree();

    CmsTemplateInfo findBy(String code);

    String add(String operator, CmsTemplateVo vo);

    String modify(String operator, CmsTemplateVo vo);

    void delete(String operator, String code);
}
