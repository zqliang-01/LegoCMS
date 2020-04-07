CREATE PROCEDURE ADDCMSSIMPLETYPE (
    IN code VARCHAR(255),
    IN name VARCHAR(255),
    IN classType VARCHAR(255),
    IN sequence INT(10)
) 
BEGIN

    DECLARE vId BIGINT(15);

    SET vId = nextval('sys');

	 INSERT INTO cms_simple_type
	     (id, version, code, create_time, name, class_type, sequence)
	 VALUES
	     (vId, 1, code, sysdate(), name, classType, sequence);
END;