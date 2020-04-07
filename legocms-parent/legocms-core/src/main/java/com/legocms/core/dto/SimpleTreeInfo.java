package com.legocms.core.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SimpleTreeInfo extends TypeInfo {
    private static final long serialVersionUID = 3368714279474759201L;

    private String parentCode;
    private boolean open;
}
