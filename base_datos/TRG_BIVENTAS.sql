SET TERM ^ ;
ALTER TRIGGER TRG_BIVENTAS ACTIVE
BEFORE insert POSITION 0
AS
BEGIN
    /*FECHA DEL SERVIDOR PARA CADA VENTA INSERTADA */ 
    NEW.ID_VENTA = GEN_ID(GEN_VENTAS_ID, 1);
    NEW.FECHA = CURRENT_DATE;
    

END^
SET TERM ; ^