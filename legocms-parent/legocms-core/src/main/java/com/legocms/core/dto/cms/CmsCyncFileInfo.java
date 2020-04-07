package com.legocms.core.dto.cms;

import java.util.Date;

import com.legocms.core.dto.Dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CmsCyncFileInfo extends Dto {

    private static final long serialVersionUID = 5340920125217024169L;

    private String name;
    private String path;
    private long size;
    private String type;
    private Date updateTime;
}
