package com.legocms.test.sys;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.legocms.LegoCMSApplication;
import com.legocms.data.dao.sys.ISysPermissionDao;
import com.legocms.data.entities.sys.SysPermission;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LegoCMSApplication.class)
public class SysPermissionDaoTest {

    @Autowired
    private ISysPermissionDao permissionDao;

    @Test
    public void testFind() {
        List<SysPermission> permissions = permissionDao.findBy("admin", "admin", true);
        Assert.assertTrue(!permissions.isEmpty());
    }

}