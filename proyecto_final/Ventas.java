import java.util.Date;
/**
 * Write a description of class Ventas here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ventas {
    private int idVenta;
    private int idCliente;
    private Date fecha;
    private int estado;//0 abierta/1- cerrada
    
    //constructores
    public Ventas() {
        setIdVenta(1);
        setIdCliente(1);
        setFecha(new Date());
        setEstado(0);        
    }   
    public Ventas(int idVenta, int idCliente) {
        setIdVenta(idVenta);
        setIdCliente(idCliente);
        setFecha(new Date());
        setEstado(0);
    }   
    public Ventas(int idVenta, int idCliente, Date fecha) {
        setIdVenta(idVenta);
        setIdCliente(idCliente);
        setFecha(fecha);
        setEstado(0);
    }    
    public Ventas(int idVenta, int idCliente, Date fecha, int estado) {
        setIdVenta(idVenta);
        setIdCliente(idCliente);
        setFecha(fecha);
        setEstado(estado);
    }
    
    //setters
    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public void setEstado(int estado) {
        this.estado = estado;
    }
    //getters
    public int getIdVenta() {
        return idVenta;
    }
    public int getIdCliente() {
        return idCliente;
    }
    public Date getFecha() {
        return fecha;
    }
    public int getEstado() {
        return estado;
    }
}
