package com.legocms.data.assembler.sys;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.legocms.core.common.Constants;
import com.legocms.core.dto.Page;
import com.legocms.core.dto.sys.SysOperationLogInfo;
import com.legocms.data.assembler.AbstractAssembler;
import com.legocms.data.dao.sys.ISysPermissionLangDao;
import com.legocms.data.entities.sys.SysOperationLog;

import io.netty.util.internal.StringUtil;

@Component
public class SysOperationLogAssembler extends AbstractAssembler<SysOperationLogInfo, SysOperationLog> {

    @Autowired
    private ISysPermissionLangDao langDao;

    @Override
    public SysOperationLogInfo create(SysOperationLog entity) {
        return create(entity, Constants.DEFAULT_LANG);
    }

    public SysOperationLogInfo create(SysOperationLog entity, String lang) {
        SysOperationLogInfo info = new SysOperationLogInfo();
        info.setCode(entity.getCode());
        info.setUser(entity.getUser().getName());
        info.setActionType(entity.getActionType().getName());
        info.setPermission(langDao.findBy(entity.getPermission(), lang).getName());
        String description = entity.getDescription();
        if (StringUtil.length(description) > 30) {
            info.setSummary(description.substring(0, 30).trim() + "...");
        }
        else {
            info.setSummary(description);
        }
        info.setDescription(description);
        info.setCreateTime(entity.getCreateTime());
        return info;
    }

    public List<SysOperationLogInfo> create(List<SysOperationLog> entities, String lang) {
        List<SysOperationLogInfo> infos = new ArrayList<SysOperationLogInfo>();
        for (SysOperationLog entity : entities) {
            infos.add(create(entity, lang));
        }
        return infos;
    }

    public Page<SysOperationLogInfo> createInfoPage(Page<SysOperationLog> ePage, String lang) {
        return new Page<SysOperationLogInfo>(ePage.getParam(), create(ePage.getResult(), lang), ePage.getCurrent(), ePage.getPageSize(), ePage.getTotalCount());
    }
}
