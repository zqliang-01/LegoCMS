package com.legocms.data.act.sys;

import java.util.List;

import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.sys.SysPermissionCode;
import com.legocms.data.act.DeleteAction;
import com.legocms.data.dao.sys.ISysSiteDao;
import com.legocms.data.entities.sys.SysSite;
import com.legocms.data.entities.sys.SysUser;

public class DeleteSysSiteAction extends DeleteAction<SysSite> {

    private String code;

    private ISysSiteDao siteDao = getDao(ISysSiteDao.class);

    public DeleteSysSiteAction(String operator, String code) {
        super(SysPermissionCode.SITE, operator);
        this.code = code;
    }

    @Override
    protected void preprocess() {
        BusinessException.check(StringUtil.isNotBlank(code), "站点编码不能为空，站点删除失败！");

        SysSite site = siteDao.findByUnsureCode(code);
        BusinessException.check(site != null, "不存在编码为[{0}]的站点信息，站点删除失败！", code);
        setTargetEntity(site);
    }

    @Override
    protected void destory(SysSite entity) {
        List<SysUser> users = userDao.findBySite(code);
        for (SysUser user : users) {
            user.setSite(null);
        }
        userDao.saveAll(users);
        siteDao.delete(entity);
    }

}
