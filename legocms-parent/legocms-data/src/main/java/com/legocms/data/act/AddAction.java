package com.legocms.data.act;

import java.util.Map;

import com.legocms.core.vo.sys.ActionTypeCode;
import com.legocms.data.base.BaseEntity;
import com.legocms.data.entities.sys.simpletype.ActionType;

public abstract class AddAction<T extends BaseEntity> extends MaintainAction<T> {

    public AddAction(String permissionCode, String operator) {
        super(permissionCode, operator);
    }

    @Override
    protected void doRun() {
        setTargetEntity(createTargetEntity());
        Map<String, String> snapshot = targetEntity.buildReadableSnapshot();
        setDescription(addSnapshot(snapshot, targetEntity));
    }

    @Override
    protected ActionType getActionType() {
        return commonDao.findByCode(ActionType.class, ActionTypeCode.ADD);
    }

    protected abstract T createTargetEntity();
}
