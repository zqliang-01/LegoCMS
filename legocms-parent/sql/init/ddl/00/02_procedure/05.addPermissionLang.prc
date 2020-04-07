CREATE PROCEDURE ADDPERMISSIONLANG (
    IN code VARCHAR(255),
    IN value VARCHAR(255),
    IN permissionCode VARCHAR(255)
) 
BEGIN
    DECLARE vId BIGINT(15);
    DECLARE vPermissionId BIGINT(15);
    
    SET vId = nextval('sys');
    
    SELECT p.id INTO vPermissionId FROM sys_permission p WHERE p.CODE = permissionCode;
 
	 insert into sys_permission_lang
	     (id, version, code, create_date, value, permission_id)
	 values
	     (vId, 1, code, sysdate(), value, vPermissionId);

END;