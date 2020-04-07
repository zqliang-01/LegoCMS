package com.legocms.service.sys;

import java.util.List;

import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.sys.SysOrganizationDetailInfo;
import com.legocms.core.vo.sys.SysOrganizationVo;

public interface ISysOrganizationService {

    List<SimpleTreeInfo> findSimpleTree();

    SysOrganizationDetailInfo findDetailBy(String code);

    void save(SysOrganizationVo vo);

    void delete(String code);
}
