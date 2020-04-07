package com.legocms.service.sys;

import com.legocms.core.dto.Page;
import com.legocms.core.dto.sys.SysUserInfo;
import com.legocms.core.vo.sys.QuerySysUserVo;

public interface ISysUserService {

    SysUserInfo findBy(String code);

    Page<SysUserInfo> findBy(QuerySysUserVo vo, int pageIndex, int pageSize);

    void active(String code);

    void invalid(String code);
}