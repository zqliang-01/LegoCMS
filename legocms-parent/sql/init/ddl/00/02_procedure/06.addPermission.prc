CREATE PROCEDURE ADDPERMISSION (
    IN code VARCHAR(255),
    IN parentCode VARCHAR(255),
    IN icon VARCHAR(255),
    IN url VARCHAR(255),
    IN menu INT(1),
    IN sort INT(10)
) 
BEGIN
    DECLARE vId BIGINT(15);
    DECLARE vParentId BIGINT(15);
    
    SET vId = nextval('sys');
    
    SELECT p.id INTO vParentId FROM sys_permission p WHERE p.CODE = parentCode;
 
	insert into sys_permission
		(id, version, code, create_date, icon, parent_id, menu, sort, url)
	values
		(vId, 1, code, sysdate(), icon, vParentId, menu, sort, url);

END;