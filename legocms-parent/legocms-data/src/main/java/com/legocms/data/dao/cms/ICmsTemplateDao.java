package com.legocms.data.dao.cms;

import java.util.List;

import com.legocms.data.base.IGenericDao;
import com.legocms.data.entities.cms.CmsTemplate;

public interface ICmsTemplateDao extends IGenericDao<CmsTemplate> {

    List<CmsTemplate> findChildren(String code);
}
