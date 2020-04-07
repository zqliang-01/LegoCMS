package com.legocms.service.sys;

import com.legocms.core.dto.Page;
import com.legocms.core.dto.sys.SysDomainInfo;
import com.legocms.core.vo.sys.SysDomainVo;

public interface ISysDomainService {

    SysDomainInfo findBy(String code);

    SysDomainInfo findByName(String name);

    Page<SysDomainInfo> findBy(String code, String name, String siteCode, int pageIndex, int pageSize);

    void save(SysDomainVo vo);

    void delete(String code);
}
