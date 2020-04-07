package com.legocms.core.dto.sys;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysOrganizationDetailInfo extends SysOrganizationInfo {

    private static final long serialVersionUID = 4007886977231122527L;

    private Date createTime;
}
