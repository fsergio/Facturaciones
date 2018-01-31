import java.util.Date;
/**
 * Write a description of class FacturaVentas here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FacturaVentas {
    private int idVenta;
    private Date fecha;
    private int idCliente;
    private float total;
    
    //constructores
    public FacturaVentas() {
        setIdVenta(1);
        setFecha(new Date());
        setIdCliente(1);
        setTotal(1);
    }   
    public FacturaVentas(int idVenta, Date fecha) {
        setIdVenta(idVenta);
        setFecha(fecha);
        setIdCliente(1);
        setTotal(1);
    }    
    public FacturaVentas(int idVenta, Date fecha, int idCliente) {
        setIdVenta(idVenta);
        setFecha(fecha);
        setIdCliente(idCliente);
        setTotal(1);
    }    
    public FacturaVentas(int idVenta, Date fecha, int idCliente, float total) {
        setIdVenta(idVenta);
        setFecha(fecha);
        setIdCliente(idCliente);
        setTotal(total);
    }
    //setters
    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public void setIdCliente (int idCliente) {
        this.idCliente = idCliente;
    }
    public void setTotal(float total) {
        this.total = total;
    }    
    //setters
    public int getIdVenta () {
        return idVenta;
    }
    public Date getFecha () {
        return fecha;
    }
    public int getIdCliente () {
        return idCliente;
    }
    public float getTotal () {
        return total;
    }    
}
