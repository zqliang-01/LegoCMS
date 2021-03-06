CREATE FUNCTION nextval(vName VARCHAR(50)) RETURNS bigint(15)  
DETERMINISTIC 
BEGIN 
	DECLARE vValue BIGINT;
	
	IF vName IS NULL OR vName = '' THEN
		SET vName = 'general';
	END IF;
	
	UPDATE SEQUENCE SET id = LAST_INSERT_ID(id + 1)
	WHERE  NAME = vName;
	SELECT LAST_INSERT_ID() INTO vValue FROM SEQUENCE WHERE NAME = vName;
	
	RETURN vValue;
END;
