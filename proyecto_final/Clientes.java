/**
 * Write a description of class Clientes here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Clientes {
   private int id;
   private String nombreCliente;
   private float saldo;
   
   //constructores
   public Clientes() {
       setID(1);
       setNombreCliente("cliente nuevo");
       setSaldo(0);
   }   
   public Clientes(int id, String nombreCliente) {
       setID(id);
       setNombreCliente(nombreCliente);
       setSaldo(0);
   }       
   public Clientes(int id, String nombreCliente, float saldo) {
       setID(id);
       setNombreCliente(nombreCliente);
       setSaldo(saldo);
   }    
   //setters
   public void setID(int id) {
       this.id = id;
   }
   public void setNombreCliente(String nombreCliente) {
       this.nombreCliente = nombreCliente;
   }
   public void setSaldo(float saldo) {
       this.saldo = saldo;
   }
   
   //getters
   public int getID() {
       return id;
   }
   public String getNombreCliente() {
       return nombreCliente;
   }
   public float getSaldo() {
       return saldo;
   }
}
