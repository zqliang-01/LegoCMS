insert into sequence(ID, NAME) values ('100000000000001', 'sys');
insert into sequence(ID, NAME) values ('100100000000001', 'general');

CALL addSimpleType('Using', '在用', 'UserStatus', 1);

CALL addSimpleType('Terminated', '停用', 'UserStatus', 2);

CALL addSimpleType('Using', '在用', 'OrganizationStatus', 1);

CALL addSimpleType('Terminated', '停用', 'OrganizationStatus', 2);

CALL addOrganization('root', '根部门', null);

CALL addUser('admin', '管理员', 'admin', 'root');

CALL addRole('super', '系统管理员');

CALL addUserRole('admin', 'super');

CALL addPermission('root', null, null, null, 0, 0);

CALL addPermissionLang('zh', '根', 'root');

CALL addPermission('admin', 'root', 'pe-7s-diamond', null, 1, 1);

CALL addPermissionLang('zh', '系统管理', 'admin');

CALL addPermission('user', 'admin', null, '/admin/user/init', 1, 2);

CALL addPermissionLang('zh', '员工管理', 'user');

CALL addPermission('user:query', 'user', null, null, 0, 3);

CALL addPermissionLang('zh', '查询', 'user:query');

CALL addRolePermission('super', 'root');

CALL addRolePermission('super', 'admin');

CALL addRolePermission('super', 'user');

CALL addRolePermission('super', 'user:query');
