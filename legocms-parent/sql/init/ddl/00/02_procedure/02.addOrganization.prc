CREATE PROCEDURE ADDORGANIZATION (
    IN organizationCode VARCHAR(255),
    IN organizationName VARCHAR(255),
    IN parentCode VARCHAR(255)
) 
BEGIN
    DECLARE vStatusId BIGINT(15);
    DECLARE vParentId BIGINT(15);
    DECLARE vOrganizationId BIGINT(15);

    SET vOrganizationId = nextval('sys');

    SELECT p.id INTO vParentId FROM sys_organization p WHERE p.CODE = parentCode;
    
    SELECT s.id INTO vStatusId FROM sys_simple_type s WHERE s.CODE = 'Using' AND s.CLASS_TYPE = 'OrganizationStatus';

	 insert into sys_organization
	     (id, version, code, create_time, name, parent_id, status)
	 values
	     (vOrganizationId, 1, organizationCode, sysdate(), organizationName, vParentId, vStatusId);

END;