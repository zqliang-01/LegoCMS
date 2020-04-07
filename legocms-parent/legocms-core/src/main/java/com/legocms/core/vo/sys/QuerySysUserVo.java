package com.legocms.core.vo.sys;

import java.util.Date;

import com.legocms.core.vo.Vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class QuerySysUserVo extends Vo {

    private static final long serialVersionUID = 1070444377374111579L;

    private String code;
    private String name;
    private Date createStart;
    private Date createEnd;
}
