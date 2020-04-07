package com.legocms.data.act;

import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.sys.ActionTypeCode;
import com.legocms.data.base.BaseEntity;
import com.legocms.data.entities.sys.simpletype.ActionType;

public abstract class DeleteAction<T extends BaseEntity> extends MaintainAction<T> {

    public DeleteAction(String permissionCode, String operator) {
        super(permissionCode, operator);
    }

    @Override
    protected void doRun() {
        BusinessException.check(targetEntity != null, "targetEntity未初始化");
        setDescription("删除记录 -> " + targetEntity.buildReadableSnapshotString());
        destory(targetEntity);
    }

    @Override
    protected ActionType getActionType() {
        return commonDao.findByCode(ActionType.class, ActionTypeCode.DELETE);
    }

    protected abstract void destory(T entity);
}
