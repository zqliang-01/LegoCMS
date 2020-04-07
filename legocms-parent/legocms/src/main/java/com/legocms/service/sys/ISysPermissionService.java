package com.legocms.service.sys;

import java.util.List;

import com.legocms.core.dto.SimpleCheckTreeInfo;
import com.legocms.core.dto.SimpleTreeInfo;
import com.legocms.core.dto.sys.SysPermissionDetailInfo;
import com.legocms.core.dto.sys.SysPermissionInfo;
import com.legocms.core.vo.sys.SysPermissionVo;

public interface ISysPermissionService {

    List<SysPermissionInfo> findBy(String userCode, String parentCode, String lang, boolean menu);

    List<SimpleTreeInfo> findSimpleTree(String lang);

    List<SimpleCheckTreeInfo> findSimpleCheckTree(String roleCode, String lang);

    SysPermissionDetailInfo findDetailBy(String code);

    void add(String operator, SysPermissionVo vo);

    void modify(String operator, SysPermissionVo vo);

    void delete(String operator, String code);

}
