CREATE PROCEDURE ADDROLE (
    IN roleCode VARCHAR(255),
    IN roleName VARCHAR(255)
) 
BEGIN
    DECLARE vId BIGINT(15);

    SET vId = nextval('sys');

	 insert into sys_role
	     (id, version, code, create_time, name)
	 values
	     (vId, 1, roleCode, sysdate(), roleName);

END;