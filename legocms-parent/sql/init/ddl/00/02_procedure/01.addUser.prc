CREATE PROCEDURE ADDUSER (
    IN userCode VARCHAR(255),
    IN userName VARCHAR(255),
    IN password VARCHAR(255),
    IN organizationCode VARCHAR(255)
) 
BEGIN
    DECLARE vUserId BIGINT(15);
    DECLARE vStatusId BIGINT(15);
    DECLARE vOrganizationId BIGINT(15);

    SET vUserId = nextval('sys');

    SELECT o.id INTO vOrganizationId FROM sys_organization o WHERE o.CODE = organizationCode;
    
    SELECT s.id INTO vStatusId FROM sys_simple_type s WHERE s.CODE = 'Using' AND s.CLASS_TYPE = 'UserStatus';

	 INSERT INTO sys_user
	     (id, version, code, create_time, name, password, organization_id, status_id)
	 VALUES
	     (vUserId, 1, userCode, sysdate(), userName, password, vOrganizationId, vStatusId);
END;