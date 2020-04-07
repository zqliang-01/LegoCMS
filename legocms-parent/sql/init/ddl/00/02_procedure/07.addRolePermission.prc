CREATE PROCEDURE ADDROLEPERMISSION (
    IN roleCode VARCHAR(255),
    IN permissionCode VARCHAR(255)
) 
BEGIN
    DECLARE vRoleId BIGINT(15);
    DECLARE vPermissionId BIGINT(15);
    
    SELECT r.id INTO vRoleId FROM sys_role r WHERE r.CODE = roleCode;
    SELECT p.id INTO vPermissionId FROM sys_permission p WHERE p.CODE = permissionCode;
 
    insert sys_role_permission
        (role_id, permission_id)
   values
	     (vRoleId, vPermissionId);

END;