package com.legocms.data.entities.sys;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.legocms.data.base.BaseEntity;
import com.legocms.data.entities.sys.simpletype.ActionType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "sys_operation_log")
@EqualsAndHashCode(callSuper = true)
public class SysOperationLog extends BaseEntity {

    private String description;
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private SysUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", nullable = false)
    private SysPermission permission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_type", referencedColumnName = "id")
    private ActionType actionType;

    protected SysOperationLog() { }

    public SysOperationLog(SysPermission permission, SysUser user) {
        super(null);
        this.user = user;
        this.permission = permission;
    }
}
