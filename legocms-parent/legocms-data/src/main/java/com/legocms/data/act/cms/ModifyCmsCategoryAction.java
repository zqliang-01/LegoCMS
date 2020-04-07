package com.legocms.data.act.cms;

import java.util.ArrayList;
import java.util.List;

import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.cms.CmsCategoryVo;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.ModifyAction;
import com.legocms.data.dao.cms.ICmsCategoryDao;
import com.legocms.data.dao.cms.ICmsModelDao;
import com.legocms.data.dao.cms.ICmsTemplateDao;
import com.legocms.data.entities.cms.CmsCategory;
import com.legocms.data.entities.cms.CmsModel;
import com.legocms.data.entities.cms.CmsTemplate;
import com.legocms.data.entities.cms.simpletype.CmsCategoryStatus;
import com.legocms.data.entities.cms.simpletype.CmsCategoryType;
import com.legocms.data.entities.sys.SysSite;

public class ModifyCmsCategoryAction extends ModifyAction<CmsCategory> {

    private String siteCode;
    private CmsCategoryVo vo;

    private SysSite site;
    private CmsCategory parent;
    private CmsTemplate template;
    private CmsCategoryType type;
    private CmsCategoryStatus status;
    private List<CmsModel> models = new ArrayList<CmsModel>();

    private ICmsModelDao modelDao = getDao(ICmsModelDao.class);
    private ICmsCategoryDao categoryDao = getDao(ICmsCategoryDao.class);
    private ICmsTemplateDao templateDao = getDao(ICmsTemplateDao.class);

    public ModifyCmsCategoryAction(String operator, String siteCode, CmsCategoryVo vo) {
        super(SysPermissionCode.CATEGORY, operator);
        this.vo = vo;
        this.siteCode = siteCode;
    }

    @Override
    protected void preprocess() {
        BusinessException.check(StringUtil.isNotBlank(vo.getCode()), "分类编码不能为空，分类修改失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getName()), "分类名称不能为空，分类修改失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getPath()), "分类访问路径不能为空，分类修改失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getStatus().getCode()), "分类状态不能为空，分类修改失败！");
        BusinessException.check(StringUtil.isNotBlank(vo.getTemplate().getCode()), "分类模板不能为空，分类修改失败！");
        BusinessException.check(vo.getPageSize() != 0, "分类每页内容数量必须大于零，分类修改失败！");

        CmsCategory category = categoryDao.findByUnsureCode(vo.getCode());
        BusinessException.check(category != null, "不存在编码为[{0}]的分类，分类修改失败！", vo.getCode());

        site = siteDao.findByCode(siteCode);
        template = templateDao.findByCode(vo.getTemplate().getCode());
        status = commonDao.findByCode(CmsCategoryStatus.class, vo.getStatus().getCode());

        if (StringUtil.isNotBlank(vo.getParent().getCode())) {
            parent = categoryDao.findByCode(vo.getParent().getCode());
        }

        for (String modelCode : vo.getModelCodes()) {
            if (StringUtil.isNotBlank(modelCode)) {
                models.add(modelDao.findByCode(modelCode));
            }
        }

        type = commonDao.findByCode(CmsCategoryType.class, vo.getType().getCode());

        setTargetEntity(category);
    }

    @Override
    protected void doModify(CmsCategory category) {
        category.setName(vo.getName());
        category.setContentPath(vo.getContentPath());
        category.setPageSize(vo.getPageSize());
        category.setModels(models);
        category.setParent(parent);
        category.setSite(site);
        category.setPath(vo.getPath());
        category.setStatus(status);
        category.setType(type);
        category.setTemplate(template);
        category.setSort(vo.getSort());
        categoryDao.save(category);
    }
}
