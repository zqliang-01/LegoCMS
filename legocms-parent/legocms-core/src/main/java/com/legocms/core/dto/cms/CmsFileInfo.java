package com.legocms.core.dto.cms;

import java.util.Date;

import com.legocms.core.dto.Dto;
import com.legocms.core.dto.TypeInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CmsFileInfo extends Dto {

    private static final long serialVersionUID = 5340920125217024169L;

    private String code;
    private String name;
    private String path;
    private long size;
    private String content;
    private boolean editable;
    private TypeInfo type;
    private TypeInfo parent;
    private Date createTime;
    private Date updateTime;
}
