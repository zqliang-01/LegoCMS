package com.legocms.data.act;

import java.util.Map;

import com.legocms.core.common.StringUtil;
import com.legocms.core.exception.BusinessException;
import com.legocms.core.vo.sys.ActionTypeCode;
import com.legocms.data.base.BaseEntity;
import com.legocms.data.entities.sys.simpletype.ActionType;

public abstract class ModifyAction<T extends BaseEntity> extends MaintainAction<T> {

    public ModifyAction(String permissionCode, String operator) {
        super(permissionCode, operator);
    }

    @Override
    protected void doRun() {
        BusinessException.check(targetEntity != null, "targetEntity未设置");
        Map<String, String> beforeSnapshot = targetEntity.buildReadableSnapshot();
        doModify(targetEntity);
        String diffSnapshot = diffSnapshot(beforeSnapshot, targetEntity);
        BusinessException.check(StringUtil.isNotBlank(diffSnapshot), "内容无变化！");
        setDescription(diffSnapshot);
    }

    @Override
    protected ActionType getActionType() {
        return commonDao.findByCode(ActionType.class, ActionTypeCode.MODIFY);
    }

    protected abstract void doModify(T entity);
}
