package com.legocms.core.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SimpleCheckTreeInfo extends SimpleTreeInfo {

    private static final long serialVersionUID = -9056058558406880913L;

    private boolean checked;
}
