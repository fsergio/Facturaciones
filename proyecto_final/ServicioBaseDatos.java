import java.sql.*;//aca vive la clase Connection, DriverManager
import java.io.*;//aca vive la clase InputStream, FileInputStream
import javax.swing.JOptionPane;
import java.util.Properties;
import javax.swing.JOptionPane;
 /** Clase que intenta conectarse con una base de datos.
 * 
 * @author  Fernandez Sergio
 * @version 1.0, 13 Jul 2017
 */
public class ServicioBaseDatos {
    private static final String CFGR= "cfg.properties";//Variable de instancia que representa la ubicacion, del archivo de configuracion de APP.
    private static Connection conn = null;
    private static boolean isConn = false;
    private Properties connectionProps = new Properties();
    private InputStream cfg = null;
    
    /**
     * Constructor para los casos en que se necesite conectar, a otro motor de base de datos.
     * <pre>
     *      ServicioBaseDatos sbd = new ServicioBaseDatos(PATH/archivo.Properties);
     *      sbd.conectorFirebird();
     * </pre>
     * @param String con PATH/archivo.Properties. Si es relativo a directorio principal, no incluya PATH.
     * @return 
     */
    public ServicioBaseDatos(String cfg) {
        try {
            this.cfg = new FileInputStream(cfg);
            connectionProps.load(this.cfg);
            this.cfg.close();
            if(JOptionPane.showConfirmDialog(null, "Cargar ubicacion bd?", "Configuracion", JOptionPane.YES_NO_OPTION) == 0) {
               connectionProps.setProperty("path", JOptionPane.showInputDialog(null, "Cargar"));
               connectionProps.store(new FileOutputStream(CFGR), null);
               conectorFirebird();
            }else {
               conectorFirebird();  
            }
        } catch(FileNotFoundException e) {
            System.out.println("no se pudo abrir el arhivo...");
            JOptionPane.showMessageDialog(null,"No se pudo abrir el arhivo de configuracion...");
        } catch(SecurityException e) {
            System.out.println("no tiene permiso a este archivo...");
            JOptionPane.showMessageDialog(null,"No tiene permiso a este archivo...");
        } catch(IOException e) {
            System.out.println("algo ocurrio mientras se intentaba leer...");
            JOptionPane.showMessageDialog(null,"Algo ocurrio mientras se intentaba leer...");
        } catch(IllegalArgumentException e) {
            System.out.println("caracteres invalidos...");
            JOptionPane.showMessageDialog(null,"Caracteres invalidos...");
        }
    } 
    /**
     * Constructor rapido para cargar un archivo de configuracion de conexion <Firebird>.
     * @exception Exception FileNotFoundException , SecurityException, IOException, IllegalArgumentException
     */    
    public ServicioBaseDatos() {
        try {
            if(getIsCon() == false) {
                cfg = new FileInputStream(CFGR); 
                connectionProps.load(cfg);
                cfg.close();
                conectorFirebird();
            }
                  
        } catch(FileNotFoundException e) {
            System.out.println("No se pudo abrir el arhivo de configuracion...");
        } catch(SecurityException e) {
            System.out.println("No tiene permiso a este archivo ...");
        } catch(IOException e) {
            System.out.println("Algo ocurrio mientras se intentaba leer...");
        } catch(IllegalArgumentException e) {
            System.out.println("Carateres invalidos...");
        }
    }

    /**
     * Conector para firebird 3.0, y jaybir 3.0.1
     * WireCrypt = Enabled
     * encoding = NONE 
    */
    public  Connection conectorFirebird() {
       try{
           if(isConn == false) {
                System.out.println("conectando a firebird...");
                Class.forName(connectionProps.getProperty("driver"));
                System.out.println("driver cargado.");            
                this.conn = DriverManager.getConnection(connectionProps.getProperty("protocolo")+
                                                    ":"+connectionProps.getProperty("host")+
                                                    "/"+connectionProps.getProperty("puerto")+
                                                    ":"+connectionProps.getProperty("path")+
                                                        connectionProps.getProperty("encoding"),
                                                        connectionProps.getProperty("usuario"),
                                                        connectionProps.getProperty("pass"));
                //this.conn.setAutoCommit(false);
                isConn = true;
                System.out.println("conexion ok...");
           }
       } catch(ClassNotFoundException e) {
           System.out.println("No se pudo cargar el driver...");
           JOptionPane.showMessageDialog(null,"No se pudo cargar el driver...");
       } catch(SQLException e) {
           System.out.println("error de acceso a bd..."+e.getMessage());
           JOptionPane.showMessageDialog(null,"error de acceso a bd...");
       }finally {
           return conn;
       }
    } 
 
    //getters
    public Connection getConexion() {
        return conn;
    }
    public void getCFG() {
        System.out.println(connectionProps.toString());
    }
    public  ResultSet getR(String query) {
        ResultSet rs = null;
        try {
            Statement stm = conn.createStatement();
            rs = stm.executeQuery(query); 
        }catch(SQLException e) {
            System.out.println("queryR: "+e.getMessage());
        }finally {
            return rs;            
        }
    }
    public  boolean query(String query) {
        boolean res = true;
        try {
            Statement stm = conn.createStatement(); 
            stm.execute(query);          
        }catch(SQLException e) {
            res = false;
        }finally { 
            return res;
        }
    } 
    public boolean cerrarConexion() {
        boolean res = false;
        try{
            conn.close();
            isConn = false;
            res = true;
        }catch(SQLException e) {
            System.out.println("cerrarConexion: "+e.getMessage());
        }finally {
            return res;
        }
    }  
    public void commitear() {
        try {
            conn.commit();
        }catch(SQLException e) {
            System.out.println("commiteo fallo!: "+e.getMessage());
        }
    }   
    public boolean getIsCon() {
        return isConn;
    }
}
