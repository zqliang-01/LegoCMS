
insert into sys_organization
    (id, version, code, create_date, name, state)
values
    (nextval('sys'), 1, 'root', sysdate(), '根部门', 1);

insert into sys_user
    (id, version, code, create_date, name, password, organization_id)
values
    (nextval('sys'), 1, 'admin', sysdate(), 'admin', 'admin', (select o.id from sys_organization o where o.code = 'root'));

insert into sys_role
    (id, version, code, create_date, name)
values
    (nextval('sys'), 1, 'super', sysdate(), '超级管理员');

insert sys_user_role
    (user_id, role_id)
values
    ((select u.id from sys_user u where u.CODE = 'admin'), (select r.id from sys_role r where r.code = 'super'));

insert into sys_permission
    (id, version, code, create_date, icon, menu, sort)
values
    (nextval('sys'), 1, 'permission:admin', sysdate(), 'pe-7s-diamond', 1, 1);

insert into sys_permission_lang
    (id, version, code, create_date, value, permission_id)
values
    (nextval('sys'), 1, 'zh', sysdate(), '系统管理',(select p.id from sys_permission p where p.code = 'permission:admin'));

insert into sys_permission
    (id, version, code, create_date, parent_id, menu, sort)
values
    (nextval('sys'), 1, 'permission:test', sysdate(), (select p.id from sys_permission p where p.code = 'permission:admin'), 1, 2);

insert into sys_permission_lang
    (id, version, code, create_date, value, permission_id)
values
    (nextval('sys'), 1, 'zh', sysdate(), '测试菜单',(select p.id from sys_permission p where p.code = 'permission:test'));

insert sys_role_permission
    (role_id, permission_id)
values
    ((select r.id from sys_role r where r.code = 'super'), (select p.id from sys_permission p where p.code = 'permission:admin'));

insert sys_role_permission
    (role_id, permission_id)
values
    ((select r.id from sys_role r where r.code = 'super'), (select p.id from sys_permission p where p.code = 'permission:test'));