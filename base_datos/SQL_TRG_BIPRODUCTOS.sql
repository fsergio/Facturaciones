SET TERM ^ ;
ALTER TRIGGER TRG_BIPRODUCTOS ACTIVE
BEFORE insert POSITION 0
AS
BEGIN
    /* PRODUCTO CON STOCK MAYOR A CERO */ 
    NEW.ID_PRODUCTO = GEN_ID(GEN_PRODUCTOS_ID, 1);
    IF(NEW.STOCK <= 0) THEN
        EXCEPTION EX_PRODUCTOS1;
    /*PRECIO MAYOR A CERO */
    IF(NEW.PRECIO <= 0) THEN
        EXCEPTION EX_PRODUCTOS2;
END^
SET TERM ; ^
