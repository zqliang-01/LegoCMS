package com.legocms.service.sys;

import com.legocms.core.dto.Page;
import com.legocms.core.dto.sys.SysSiteInfo;
import com.legocms.core.vo.sys.SysSiteVo;

public interface ISysSiteService {

    SysSiteInfo findBy(String code);

    Page<SysSiteInfo> findBy(String code, String name, String organization, String manageSite, int pageIndex, int pageSize);

    void add(String operator, SysSiteVo vo);

    void modify(String operator, SysSiteVo vo);

    void manage(String operator, String userCode, String code);

    void delete(String operator, String code);
}