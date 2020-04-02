CREATE FUNCTION currval(seq_name VARCHAR(50)) RETURNS bigint(15)    
DETERMINISTIC 
BEGIN 
	DECLARE value BIGINT;          
	SET value = 0; 
	SELECT id INTO value FROM sequence WHERE name=seq_name; 
 	RETURN value; 
END;