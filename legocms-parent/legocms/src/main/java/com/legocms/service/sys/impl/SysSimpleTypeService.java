package com.legocms.service.sys.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legocms.core.dto.TypeInfo;
import com.legocms.data.assembler.TypeInfoAssembler;
import com.legocms.data.entities.sys.simpletype.SysOrganizationStatus;
import com.legocms.data.entities.sys.simpletype.SysUserStatus;
import com.legocms.service.BaseService;
import com.legocms.service.sys.ISysSimpleTypeService;

@Service
public class SysSimpleTypeService extends BaseService implements ISysSimpleTypeService {

    @Autowired
    private TypeInfoAssembler typeInfoAssembler;

    @Override
    public List<TypeInfo> findUserStatus() {
        List<SysUserStatus> userStatus = commonDao.findAll(SysUserStatus.class);
        return typeInfoAssembler.create(userStatus);
    }

    @Override
    public List<TypeInfo> findOrganizationStatus() {
        List<SysOrganizationStatus> userStatus = commonDao.findAll(SysOrganizationStatus.class);
        return typeInfoAssembler.create(userStatus);
    }

}
