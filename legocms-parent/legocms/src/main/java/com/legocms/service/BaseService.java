package com.legocms.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.legocms.data.base.ICommonDao;

@Transactional
public class BaseService {

    @Autowired
    protected ICommonDao commonDao;
}
