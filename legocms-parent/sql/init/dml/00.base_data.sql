insert into sequence(ID, NAME) values ('100000000000001', 'cms');
insert into sequence(ID, NAME) values ('500000000000001', 'sys');
insert into sequence(ID, NAME) values ('100100000000001', 'general');

CALL addSysSimpleType('Using', '在用', 'UserStatus', 1);
CALL addSysSimpleType('Terminated', '停用', 'UserStatus', 2);
CALL addSysSimpleType('Using', '在用', 'OrganizationStatus', 1);
CALL addSysSimpleType('Terminated', '停用', 'OrganizationStatus', 2);
CALL addSysSimpleType('add', '增加', 'ActionType', 1);
CALL addSysSimpleType('modify', '修改', 'ActionType', 2);
CALL addSysSimpleType('delete', '删除', 'ActionType', 3);
CALL addSysSimpleType('synchronize', '同步', 'ActionType', 4);

CALL addCmsSimpleType('file', '文件', 'TemplateType', 1);
CALL addCmsSimpleType('dir', '目录', 'TemplateType', 2);
CALL addCmsSimpleType('file', '文件', 'PlaceType', 1);
CALL addCmsSimpleType('dir', '目录', 'PlaceType', 2);
CALL addCmsSimpleType('file', '文件', 'FileType', 1);
CALL addCmsSimpleType('dir', '目录', 'FileType', 2);

CALL addCmsSimpleType('text', '输入框', 'ModelAttributeType', 1);
CALL addCmsSimpleType('textArea', '文本域', 'ModelAttributeType', 2);
CALL addCmsSimpleType('edit', '编辑器', 'ModelAttributeType', 3);
CALL addCmsSimpleType('picture', '图片选择', 'ModelAttributeType', 4);
CALL addCmsSimpleType('dateTime', '日期选择', 'ModelAttributeType', 5);

CALL addCmsSimpleType('Normal', '正常', 'CategoryStatus', 1);
CALL addCmsSimpleType('Inactive', '停用', 'CategoryStatus', 2);

CALL addCmsSimpleType('General', '普通类型', 'CategoryType', 1);
CALL addCmsSimpleType('Article', '文章类型', 'CategoryType', 2);

CALL addOrganization('root', '根部门', null);

CALL addUser('admin', '管理员', '21232F297A57A5A743894A0E4A801FC3', 'root');

CALL addRole('super', '系统管理员');
CALL addUserRole('admin', 'super');

CALL addPermission('root', null, null, null, 0, 0);
CALL addPermissionLang('zh', '根', 'root');
CALL addPermissionLang('en', 'root', 'root');

/** =======系统管理====== */
CALL addPermission('admin', 'root', 'pe-7s-config', null, 1, 1);
CALL addPermissionLang('zh', '系统管理', 'admin');
CALL addPermissionLang('en', 'System Management', 'admin');

CALL addPermission('user', 'admin', 'pe-7s-add-user', '/admin/user/init', 1, 1);
CALL addPermissionLang('zh', '用户管理', 'user');
CALL addPermissionLang('en', 'User Management', 'user');

CALL addPermission('user-edit', 'user', null, null, 0, 2);
CALL addPermissionLang('zh', '修改', 'user-edit');
CALL addPermissionLang('en', 'edit', 'user-edit');

CALL addPermission('organization', 'admin', 'pe-7s-users', '/admin/organization/init', 1, 2);
CALL addPermissionLang('zh', '部门管理', 'organization');
CALL addPermissionLang('en', 'Organization Management', 'organization');

CALL addPermission('organization-edit', 'organization', null, null, 0, 1);
CALL addPermissionLang('zh', '修改', 'organization-edit');
CALL addPermissionLang('en', 'edit', 'organization-edit');

CALL addPermission('organization-delete', 'organization', null, null, 0, 2);
CALL addPermissionLang('zh', '删除', 'organization-delete');
CALL addPermissionLang('en', 'delete', 'organization-delete');

CALL addPermission('role', 'admin', 'pe-7s-id', '/admin/role/init', 1, 3);
CALL addPermissionLang('zh', '角色管理', 'role');
CALL addPermissionLang('en', 'Role Management', 'role');

CALL addPermission('role-authorize', 'role', null, null, 0, 1);
CALL addPermissionLang('zh', '授权', 'role-authorize');
CALL addPermissionLang('en', 'authorization', 'role-authorize');

CALL addPermission('role-edit', 'role', null, null, 0, 2);
CALL addPermissionLang('zh', '修改', 'role-edit');
CALL addPermissionLang('en', 'edit', 'role-edit');

CALL addPermission('role-delete', 'role', null, null, 0, 3);
CALL addPermissionLang('zh', '删除', 'role-delete');
CALL addPermissionLang('en', 'delete', 'role-delete');

CALL addPermission('permission', 'admin', 'pe-7s-hammer', '/admin/permission/init', 1, 4);
CALL addPermissionLang('zh', '权限管理', 'permission');
CALL addPermissionLang('en', 'Permission Management', 'permission');

CALL addPermission('permission-edit', 'permission', null, null, 0, 1);
CALL addPermissionLang('zh', '修改', 'permission-edit');
CALL addPermissionLang('en', 'edit', 'permission-edit');

CALL addPermission('permission-delete', 'permission', null, null, 0, 2);
CALL addPermissionLang('zh', '删除', 'permission-delete');
CALL addPermissionLang('en', 'delete', 'permission-delete');

CALL addPermission('log', 'admin', 'pe-7s-server', '/admin/log/init', 1, 4);
CALL addPermissionLang('zh', '操作日志', 'log');
CALL addPermissionLang('en', 'Operation Log', 'log');

/** =======站点维护====== */
CALL addPermission('site-maintenance', 'root', 'pe-7s-tools', null, 1, 2);
CALL addPermissionLang('zh', '站点维护', 'site-maintenance');
CALL addPermissionLang('en', 'Site Maintenance', 'site-maintenance');

CALL addPermission('site', 'site-maintenance', 'pe-7s-science', '/admin/site/init', 1, 1);
CALL addPermissionLang('zh', '站点管理', 'site');
CALL addPermissionLang('en', 'Site Management', 'site');

CALL addPermission('site-edit', 'site', null, null, 0, 1);
CALL addPermissionLang('zh', '修改', 'site-edit');
CALL addPermissionLang('en', 'edit', 'site-edit');

CALL addPermission('site-delete', 'site', null, null, 0, 2);
CALL addPermissionLang('zh', '删除', 'site-delete');
CALL addPermissionLang('en', 'delete', 'site-delete');


CALL addPermission('domain', 'site-maintenance', 'pe-7s-display2', '/admin/domain/init', 1, 2);
CALL addPermissionLang('zh', '域名管理', 'domain');
CALL addPermissionLang('en', 'Domain Management', 'domain');

CALL addPermission('domain-edit', 'domain', null, null, 0, 1);
CALL addPermissionLang('zh', '修改', 'domain-edit');
CALL addPermissionLang('en', 'edit', 'domain-edit');

CALL addPermission('domain-delete', 'domain', null, null, 0, 2);
CALL addPermissionLang('zh', '删除', 'domain-delete');
CALL addPermissionLang('en', 'delete', 'domain-delete');


CALL addPermission('template', 'site-maintenance', 'pe-7s-note', '/admin/template/init', 1, 3);
CALL addPermissionLang('zh', '模板管理', 'template');
CALL addPermissionLang('en', 'Template Management', 'template');

CALL addPermission('template-edit', 'template', null, null, 0, 1);
CALL addPermissionLang('zh', '修改', 'template-edit');
CALL addPermissionLang('en', 'edit', 'template-edit');

CALL addPermission('template-delete', 'template', null, null, 0, 2);
CALL addPermissionLang('zh', '删除', 'template-delete');
CALL addPermissionLang('en', 'delete', 'template-delete');

CALL addPermission('place', 'site-maintenance', 'pe-7s-ribbon', '/admin/place/init', 1, 4);
CALL addPermissionLang('zh', '页面片段管理', 'place');
CALL addPermissionLang('en', 'Place Management', 'place');

CALL addPermission('place-edit', 'place', null, null, 0, 1);
CALL addPermissionLang('zh', '修改', 'place-edit');
CALL addPermissionLang('en', 'edit', 'place-edit');

CALL addPermission('place-delete', 'place', null, null, 0, 2);
CALL addPermissionLang('zh', '删除', 'place-delete');
CALL addPermissionLang('en', 'delete', 'place-delete');

CALL addPermission('file', 'site-maintenance', 'pe-7s-wallet', '/admin/file/init', 1, 5);
CALL addPermissionLang('zh', '站点文件管理', 'file');
CALL addPermissionLang('en', 'File Management', 'file');

CALL addPermission('file-edit', 'file', null, null, 0, 1);
CALL addPermissionLang('zh', '修改', 'file-edit');
CALL addPermissionLang('en', 'edit', 'file-edit');

CALL addPermission('file-synchronize', 'file', null, null, 0, 1);
CALL addPermissionLang('zh', '同步', 'file-synchronize');
CALL addPermissionLang('en', 'synchronize', 'file-synchronize');

CALL addPermission('file-delete', 'file', null, null, 0, 2);
CALL addPermissionLang('zh', '删除', 'file-delete');
CALL addPermissionLang('en', 'delete', 'file-delete');

/** =======内容维护====== */
CALL addPermission('content-maintenance', 'root', 'pe-7s-browser', null, 1, 3);
CALL addPermissionLang('zh', '内容维护', 'content-maintenance');
CALL addPermissionLang('en', 'Site Maintenance', 'content-maintenance');

CALL addPermission('model', 'content-maintenance', 'pe-7s-box2', '/admin/model/init', 1, 1);
CALL addPermissionLang('zh', '内容模型管理', 'model');
CALL addPermissionLang('en', 'Model Management', 'model');

CALL addPermission('model-edit', 'model', null, null, 0, 1);
CALL addPermissionLang('zh', '修改', 'model-edit');
CALL addPermissionLang('en', 'edit', 'model-edit');

CALL addPermission('model-delete', 'model', null, null, 0, 2);
CALL addPermissionLang('zh', '删除', 'model-delete');
CALL addPermissionLang('en', 'delete', 'model-delete');

CALL addPermission('category', 'content-maintenance', 'pe-7s-keypad', '/admin/category/init', 1, 2);
CALL addPermissionLang('zh', '分类管理', 'category');
CALL addPermissionLang('en', 'Category Management', 'category');

CALL addPermission('category-edit', 'category', null, null, 0, 1);
CALL addPermissionLang('zh', '修改', 'category-edit');
CALL addPermissionLang('en', 'edit', 'category-edit');


CALL addRolePermission('super', 'root');
CALL addRolePermission('super', 'admin');
CALL addRolePermission('super', 'user');
CALL addRolePermission('super', 'user-edit');
CALL addRolePermission('super', 'permission');
CALL addRolePermission('super', 'permission-edit');
CALL addRolePermission('super', 'permission-delete');
CALL addRolePermission('super', 'role');
CALL addRolePermission('super', 'role-authorize');
CALL addRolePermission('super', 'role-edit');
CALL addRolePermission('super', 'role-delete');
CALL addRolePermission('super', 'organization');
CALL addRolePermission('super', 'organization-edit');
CALL addRolePermission('super', 'organization-delete');
CALL addRolePermission('super', 'site-maintenance');
CALL addRolePermission('super', 'site');
CALL addRolePermission('super', 'site-edit');
CALL addRolePermission('super', 'site-delete');
CALL addRolePermission('super', 'domain');
CALL addRolePermission('super', 'domain-edit');
CALL addRolePermission('super', 'domain-delete');
CALL addRolePermission('super', 'template');
CALL addRolePermission('super', 'template-edit');
CALL addRolePermission('super', 'template-delete');
CALL addRolePermission('super', 'place');
CALL addRolePermission('super', 'place-edit');
CALL addRolePermission('super', 'place-delete');
CALL addRolePermission('super', 'file');
CALL addRolePermission('super', 'file-edit');
CALL addRolePermission('super', 'file-synchronize');
CALL addRolePermission('super', 'file-delete');
CALL addRolePermission('super', 'log');
CALL addRolePermission('super', 'content-maintenance');
CALL addRolePermission('super', 'model');
CALL addRolePermission('super', 'model-edit');
CALL addRolePermission('super', 'model-delete');
CALL addRolePermission('super', 'category');
CALL addRolePermission('super', 'category-edit');