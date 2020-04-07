package com.legocms.core.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TypeCheckInfo extends TypeInfo {

    private static final long serialVersionUID = -816642692604591006L;

    private boolean checked;

    public TypeCheckInfo(TypeInfo info, boolean checked) {
        super(info.getCode(), info.getName());
        this.checked = checked;
    }

    public TypeCheckInfo(String code, String name, boolean checked) {
        super(code, name);
        this.checked = checked;
    }
}
