package com.legocms.service.sys;

import java.util.List;

import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.sys.SysPermissionDetailInfo;
import com.legocms.core.dto.sys.SysPermissionInfo;
import com.legocms.core.vo.sys.SysPermissionVo;

public interface ISysPermissionService {

    List<SysPermissionInfo> findBy(String userCode, String parentCode, boolean menu);

    List<SimpleTreeInfo> findSimpleTree(String lang);

    SysPermissionDetailInfo findDetailBy(String code);

    void save(SysPermissionVo vo);

    void delete(String code);
}
