package com.legocms.core.vo.cms;

import com.legocms.core.vo.Vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CmsFileVo extends Vo {

    private static final long serialVersionUID = -530154675827361599L;

    private String code;
    private String parentCode;
    private String name;
    private String type;
    private String content;

}
