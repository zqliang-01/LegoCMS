package com.legocms.core.dto.sys;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysOrganizationDetailInfo extends SysOrganizationInfo {

    private static final long serialVersionUID = 4007886977231122527L;

    private Date createDate;
}
