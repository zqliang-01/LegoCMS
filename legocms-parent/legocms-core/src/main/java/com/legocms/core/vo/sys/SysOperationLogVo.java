package com.legocms.core.vo.sys;

import java.util.Date;

import com.legocms.core.vo.Vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SysOperationLogVo extends Vo {

    private static final long serialVersionUID = -3536089172020172307L;

    private String userName;
    private Date createStart;
    private Date createEnd;
}
