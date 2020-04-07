package com.legocms.core.dto.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.legocms.core.dto.TypeInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysPermissionDetailInfo extends SysPermissionInfo {

    private static final long serialVersionUID = -6394862700783489411L;

    private int sort;
    private boolean menu;
    private Date createDate;
    private TypeInfo parent = new TypeInfo();
    private List<TypeInfo> lang = new ArrayList<TypeInfo>();

    public void addLang(TypeInfo info) {
        lang.add(info);
    }
}
