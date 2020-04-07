CREATE PROCEDURE ADDUSERROLE (
    IN userCode VARCHAR(255),
    IN roleCode VARCHAR(255)
) 
BEGIN
    DECLARE vUserId BIGINT(15);
    DECLARE vRoleId BIGINT(15);
    
    SELECT u.id INTO vUserId FROM sys_user u WHERE u.CODE = userCode;
    SELECT r.id INTO vRoleId FROM sys_role r WHERE r.code = roleCode;
 
	 INSERT sys_user_role
	     (user_id, role_id)
	 VALUES
	     (vUserId, vRoleId);

END;