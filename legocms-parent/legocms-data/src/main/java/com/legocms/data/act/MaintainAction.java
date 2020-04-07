package com.legocms.data.act;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.data.base.BaseEntity;
import com.legocms.data.base.ICommonDao;
import com.legocms.data.base.LegoBeanFactory;
import com.legocms.data.dao.sys.ISysOperationLogDao;
import com.legocms.data.dao.sys.ISysPermissionDao;
import com.legocms.data.dao.sys.ISysSiteDao;
import com.legocms.data.dao.sys.ISysUserDao;
import com.legocms.data.entities.sys.SysOperationLog;
import com.legocms.data.entities.sys.SysPermission;
import com.legocms.data.entities.sys.SysUser;
import com.legocms.data.entities.sys.simpletype.ActionType;

public abstract class MaintainAction<T extends BaseEntity> {

    protected T targetEntity;

    private SysUser operator;
    private SysPermission permission;
    private SysOperationLog log;

    protected ICommonDao commonDao = getDao(ICommonDao.class);
    protected ISysUserDao userDao = getDao(ISysUserDao.class);
    protected ISysSiteDao siteDao = getDao(ISysSiteDao.class);
    protected ISysPermissionDao permissionDao = getDao(ISysPermissionDao.class);
    protected ISysOperationLogDao operationLogDao = getDao(ISysOperationLogDao.class);

    protected MaintainAction(String permissionCode, String operatorCode) {
        this.operator = userDao.findByCode(operatorCode);
        this.permission = permissionDao.findByCode(permissionCode);
        this.log = new SysOperationLog(permission, operator);
    }

    public final String run() {
        preprocess();
        doRun();
        postprocess();
        ActionType actionType = getActionType();
        log.setActionType(actionType);
        log.setName(actionType.getName());
        operationLogDao.save(log);
        if (targetEntity != null) {
            return targetEntity.getCode();
        }
        return null;
    }

    protected abstract void doRun();

    protected void preprocess() { }

    protected void postprocess() { }

    protected void setLocation(String location) {
        log.setLocation(location);
    }

    protected abstract ActionType getActionType();

    protected void setDescription(String description) {
        log.setDescription(description);
    }

    public T getTargetEntity() {
        BusinessException.check(targetEntity != null, "targetObject is null");
        return targetEntity;
    }

    protected final void setTargetEntity(T targetObject) {
        BusinessException.check(targetObject != null, "targetObject is null");
        this.targetEntity = targetObject;
    }

    protected <D> D getDao(Class<D> clazz) {
        return LegoBeanFactory.getBean(clazz);
    }

    protected final String addSnapshot(Map<String, String> beforeSnapshot, BaseEntity domainObject) {
        Map<String, String> afterSnapshot = domainObject.buildReadableSnapshot();
        StringBuilder sb = new StringBuilder("新增记录 ->");
        List<String> beforeClone = new ArrayList<String>(beforeSnapshot.keySet());
        beforeClone.retainAll(afterSnapshot.keySet());
        for (String key : beforeClone) {
            String value = beforeSnapshot.get(key);
            sb.append(MessageFormat.format("{0}: \"{1}\" \r\n", key, value));
        }
        return sb.toString();
    }

    protected final String diffSnapshot(Map<String, String> beforeSnapshot, BaseEntity domainObject) {
        Map<String, String> afterSnapshot = domainObject.buildReadableSnapshot();

        StringBuilder sb = new StringBuilder();
        List<String> beforeClone = new ArrayList<String>(beforeSnapshot.keySet());
        beforeClone.retainAll(afterSnapshot.keySet());
        for (String key : beforeClone) {
            String beforeValue = beforeSnapshot.get(key);
            String afterValue = afterSnapshot.get(key);
            if (!StringUtil.equals(beforeValue, afterValue)) {
                sb.append(MessageFormat.format("{0}: 由\"{1}\"修改为\"{2}\"\r\n", key, beforeValue, afterValue));
            }
        }
        return sb.toString();
    }
}
