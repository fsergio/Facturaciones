
/**
 * Write a description of class Productos here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Productos {
    private  int idProducto;
    private String descripcion;
    private float precio;
    private float stock;
    private float can;//solo lo usa detalle venta, para no crear dicha clase... para guardar la cantidad guardada
    
    //constructores
    public Productos() {
        setIdProducto(1);
        setDescripcion("producto nuevo");
        setPrecio(1);
        setStock(1);
        setCan(1);
    }  
    public Productos(int idProducto, String descripcion) {
        setIdProducto(idProducto);
        setDescripcion(descripcion);
        setPrecio(1);
        setStock(1);
        setCan(1);
    }   
    public Productos(int idProducto, String descripcion, float precio) {
        setIdProducto(idProducto);
        setDescripcion(descripcion);
        setPrecio(precio);
        setStock(1);
        setCan(1);
    }  
    public Productos(int idProducto, String descripcion, float precio, float stock) {
        setIdProducto(idProducto);
        setDescripcion(descripcion);
        setPrecio(precio);
        setStock(stock);
        setCan(1);
    }    
    public Productos(int idProducto, String descripcion, float precio, float stock, float can) {
        setIdProducto(idProducto);
        setDescripcion(descripcion);
        setPrecio(precio);
        setStock(stock);
        setCan(can);
    }
    
    //setters
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void setPrecio(float precio) {
        this.precio = precio;
    }
    public void setStock(float stock) {
        this.stock = stock;
    }
    //cantidad vendida de este producto
    public void setCan(float can) {
        this.can = can;
    }
    
    //getters
    public int getIdProducto() {
        return idProducto;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public float getPrecio() {
        return precio;
    }
    public float getStock() {
        return stock;
    }
    //cantidad vendida de este producto
    public float getCan() {
        return can;
    }
}