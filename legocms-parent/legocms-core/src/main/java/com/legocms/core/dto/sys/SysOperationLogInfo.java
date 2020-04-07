package com.legocms.core.dto.sys;

import java.util.Date;

import com.legocms.core.dto.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysOperationLogInfo extends Dto {

    private static final long serialVersionUID = 564926832756131644L;

    private String code;
    private String user;
    private String permission;
    private String actionType;
    private String summary;
    private String description;
    private Date createTime;
}
