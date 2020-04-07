package com.legocms.data.act.sys;

import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.DeleteAction;
import com.legocms.data.dao.sys.ISysDomainDao;
import com.legocms.data.entities.sys.SysDomain;

public class DeleteSysDomainAction extends DeleteAction<SysDomain> {

    private String code;

    private ISysDomainDao domainDao = getDao(ISysDomainDao.class);

    public DeleteSysDomainAction(String operator, String code) {
        super(SysPermissionCode.DOMAIN, operator);
        this.code = code;
    }

    @Override
    protected void preprocess() {
        BusinessException.check(StringUtil.isNotBlank(code), "域名编码不能为空，域名删除失败！");

        SysDomain domain = domainDao.findByUnsureCode(code);
        BusinessException.check(domain != null, "不存在编码为[{0}]的域名信息，域名删除失败！");

        setTargetEntity(domain);
    }

    @Override
    protected void destory(SysDomain entity) {
        domainDao.delete(entity);
    }

}
