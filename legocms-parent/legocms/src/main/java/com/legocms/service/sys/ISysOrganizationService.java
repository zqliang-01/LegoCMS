package com.legocms.service.sys;

import java.util.List;

import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.sys.SysOrganizationDetailInfo;
import com.legocms.core.vo.sys.SysOrganizationVo;

public interface ISysOrganizationService {

    List<SimpleTreeInfo> findSimpleTree();

    SysOrganizationDetailInfo findDetailBy(String code);

    void add(String operator, SysOrganizationVo vo);

    void modify(String operator, SysOrganizationVo vo);

    void delete(String operator, String code);
}
