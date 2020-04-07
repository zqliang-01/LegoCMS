package com.legocms.service.sys;

import com.legocms.core.dto.Page;
import com.legocms.core.dto.sys.SysUserDetailInfo;
import com.legocms.core.dto.sys.SysUserInfo;
import com.legocms.core.vo.sys.QuerySysUserVo;
import com.legocms.core.vo.sys.SysUserVo;

public interface ISysUserService {

    SysUserInfo findBy(String code);

    SysUserDetailInfo findDetail(String code);

    Page<SysUserInfo> findBy(QuerySysUserVo vo, int pageIndex, int pageSize);

    void active(String code);

    void invalid(String code);

    void save(SysUserVo vo);
}