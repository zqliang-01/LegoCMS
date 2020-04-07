/** =======站点数据====== */
INSERT INTO sys_site
    (ID, VERSION, CODE, NAME, CREATE_TIME, PATH, DYNAMIC_PATH, ORGANIZATION_ID)
VALUES
    (nextval('sys'), 1, 'test', '测试站点', '2020-06-05 07:25:32', '//127.0.0.1:8080/legocms/static', 'http://127.0.0.1:8080/legocms/web',
	 (SELECT o.id FROM sys_organization o WHERE o.code = 'root'));

/** =======域名数据====== */
INSERT INTO sys_domain
    (ID, VERSION, CODE, NAME, CREATE_TIME, PATH, SITE_ID) 
VALUES
    (nextval('sys'), 1, 'domain001', '127.0.0.1', sysdate(), '', (SELECT s.id FROM sys_site s WHERE s.code = 'test'));

