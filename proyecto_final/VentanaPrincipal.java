import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.BorderFactory;
import javax.swing.border.*;//TitledBorder, EtchedBorder, EmptyBorder
import javax.swing.table.*;//AbstractTableModel
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Clase controladora de la APP.
 *
 * @author  Fernandez Sergio
 * @version 1.0, 13 Jul 2017
 * @see     FacturaVentas
 */
public class VentanaPrincipal extends WindowAdapter {
    private static final Date fechaActual = new Date(0);
    /*cargo app cuando arranca, y pregunt si quiere cargar la ubicacion de la base de datos, sino, intenta con cfg por defecto*/
    private ServicioBaseDatos sbd = new ServicioBaseDatos("cfg.properties");
    private ArrayList<Productos> prodAgregados = new ArrayList<Productos>();
    private Productos p;
    private Ventas ventaActual = new Ventas();
    private JOptionPane can = new JOptionPane();
    //ventana principal y panel    
    private JFrame ventanaPrincipal  = new JFrame("Facturaciones");
    private Container panelPrincipal = ventanaPrincipal.getContentPane();//panel principal
    private JButton btn1 = new JButton("Buscar");
    private JButton btn2 = new JButton("Seleccionar Producto");
    private JButton btn5 = new JButton("Productos");
    private JButton btn6 = new JButton("Clientes");
    private JButton btn7 = new JButton("Ventas");
    private JButton btn8 = new JButton("Reportes");
    private JButton btn9 = new JButton("Cerrar Factura");
    private JButton btn10 = new JButton("Seleccionar Cliente");
    private JButton btn11 = new JButton("Cancelar");  
    private JButton btn12 = new JButton("Actualizar");  
    //paneles norte
    private JPanel pNorte = new JPanel();
    //paneles oeste
    private JPanel pOeste = new JPanel();//para pegar los botones principales en la parte oete de la app
    private JPanel pFlowOeste = new JPanel();//acomoda botones dentro de panel oeste y que no ocupe todo el espacio oeste
    //paneles sur
    private JPanel pSur = new JPanel();
    //panel central
    private JPanel pCentro = new JPanel();
    private JPanel pGridLayoutCentro = new JPanel();
    private JTextArea output = new JTextArea(13, 48);
    private JPanel pBtnClientes = new JPanel();
    private JPanel pBtnProductos = new JPanel();
    //JTables
    private ModelProductos mp = new ModelProductos();
    private JTable tbProductos = new JTable(mp);    
    private ModelClientes mc = new ModelClientes();
    private JTable tbClientes = new JTable(mc);
    //constructor
    public VentanaPrincipal() {
        
    }
    /**
     * Crea ventana principal de la APP.
     */    
    public void crearVentana(){
         //#####################eventos de botones#####################
         btn2.setEnabled(false);//desactivado hasta que  selecciono cliente
         btn9.setEnabled(false);//primero selecciono cliente y al menos un producto seleccionado
         //ventas
         btn7.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){  
                new VentanaVentas();
            }});
         btn5.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new VentanaProductos();
            }});            
         //seleccionar cliente   
         btn10.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){  
                int rowSel = tbClientes.getSelectedRow();
                if(rowSel == -1){//si no hay fila seleccionada emito mensaje
                    JOptionPane.showMessageDialog(panelPrincipal, "Primero debe seleccionar un Cliente");
                }else {
                    btn10.setEnabled(false);//desactivo boton cliente cuando ya hay uno seleccionado
                    tbClientes.setEnabled(false);//lo mismo con la jtable
                    btn2.setEnabled(true);//habilito boton seleccionar producto
                    tbProductos.setEnabled(true);//la tabla productos tambien 
                    output.append(String.format("Cliente Seleccionado [ID : %d] - [NOMBRE : %s] - [SALDO : %f] %s\n", 
                    tbClientes.getValueAt(rowSel, 0),
                    tbClientes.getValueAt(rowSel, 1), tbClientes.getValueAt(rowSel, 2),"]"));
                }
                
            }});  
         //cancelar cliente (BORRA TODOS LOS ARTICULOS, CLIENTE DE LA FACTURA QUE HABIA CARGADO A MEMORIA.)
         btn11.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){  
                output.selectAll();
                ventaActual = null;//elimino objeto ventas
                prodAgregados.clear();//elimino todos los productos para esta factura
                output.replaceSelection("");//limpio JTextArea
                btn10.setEnabled(true);//puedo seleccionar nuevamente un cliente
                tbClientes.setEnabled(true);//lo mismo con tabla cliente
                btn2.setEnabled(false);//no puedo seleccionar productos hasta que no seleccione un cliente
                tbProductos.setEnabled(false);//lo mismo con tabla productos
                btn9.setEnabled(false);//no puedo cerrar factura, hasta seleccionar nuevamente un cliente
            }}); 
         //seleccionar producto
         btn2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int rowSel = tbProductos.getSelectedRow();
                float can = 0;
                if(rowSel == -1) {
                    JOptionPane.showMessageDialog(ventanaPrincipal,"Primero debe seleccionar un Producto");
                }else {//cargo nuevo producto a memoria    
                    if ((float)mp.getValueAt(rowSel, 3) != 0) {//por ahora,SOLO CARGAMOS PRODUCTOS CON STOCK, LO MANEJAMOS DESDE JAVA
                        can = Float.parseFloat(JOptionPane.showInputDialog(panelPrincipal, "Ingrese Cantidad", JOptionPane.INFORMATION_MESSAGE));
                       if((can > 0) && (can <= (float)mp.getValueAt(rowSel, 3))){//que haya suficiente stock/ingreso  mayor que cero
                            p = new Productos((int)tbProductos.getValueAt(rowSel, 0),//id producto 
                                                            (String)tbProductos.getValueAt(rowSel, 1),//descripcion
                                                            (float)tbProductos.getValueAt(rowSel, 2),//precio 
                                                            (float)tbProductos.getValueAt(rowSel, 3));//stock   
                            p.setCan(can);//cantidad a vender de cada producto
                            prodAgregados.add(p);
                            btn9.setEnabled(true);//habilito cerrar factura   
                            output.append(String.format("Producto Agregado- [ID : %d] - [DESCRIPCION : %s] - [PRECIO : %f] - [CANTIDAD : %f %s\n",
                            p.getIdProducto(),
                            p.getDescripcion(), 
                            p.getPrecio(),
                            p.getCan(),"]")); 
                            mp.removeRow(rowSel);//cargo ese productos seleccionado y luego elimino fila de jtable. para que no se vuelva a seleccionar.
                       }else {
                           JOptionPane.showMessageDialog(ventanaPrincipal,"Intente de nuevo!");
                        }
                    }else {
                        JOptionPane.showMessageDialog(ventanaPrincipal,"No hay stock suficiente!");
                    }
                 }
            }}); 
         //para cerrar factura, debo tener al menos un cliente y un producto seleccionado
         btn9.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                btn10.setEnabled(true);
                btn9.setEnabled(false);
                btn2.setEnabled(false);
                btn11.setEnabled(true);
                tbClientes.setEnabled(true);
                tbProductos.setEnabled(false);//que ponga cancelar si quiere crear otra factura
                ventaActual = new Ventas(1, //id venta
                                        (int)tbClientes.getValueAt(tbClientes.getSelectedRow(), 0),//id ciente
                                        new Date(0),//fecha cualquiera
                                        0);//estado
                if(cerrarFacturaActual(ventaActual, prodAgregados)) {//CIERRA FACTURA
                    JOptionPane.showMessageDialog(ventanaPrincipal,"Factura Cerrada!");
                    prodAgregados.clear();//elimino todo en memoria
                    ventaActual = null;
                    output.selectAll();
                    output.replaceSelection("");
                    mp.recargar();
                    mc.recargar();
                }else {
                    JOptionPane.showMessageDialog(ventanaPrincipal,"Vuelva a intentarlo!");
                    prodAgregados.clear();//elimino todo en memoria
                    ventaActual = null;
                    mp.recargar();
                    mc.recargar();                    
                }             
            }});
         btn12.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                mp.recargar();
            }});     
         btn8.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new VentanaFacturaVentas();
            }});   
         btn6.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new VentanaClientes();
            }}); 
         btn1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(panelPrincipal, "aca iria el buscador!!!");
            }});             
         //--------------configuraciones de JTables----------------------------------------------------------
         tbProductos.setEnabled(false);//primero selecciono cliente
         tbProductos.setPreferredScrollableViewportSize(new Dimension(500, 70));//para no ver toda las filas, se ve 500 x 70 px
         tbClientes.setPreferredScrollableViewportSize(new Dimension(500, 70));       
         tbClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//un solo cliente para crear venta(solo sel filas)
         tbProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//un solo producto para crear venta(solo sel filas)
         //#####################eventos de JTables#####################
         //implementar...
         JScrollPane sc = new JScrollPane(tbProductos);
         JScrollPane sc2 = new JScrollPane(tbClientes);            
         //jtextarea
         output.setEditable(false);
         JScrollPane outputPane = new JScrollPane(output, 
                                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
         //----------------gestores de disposicion-----------------------------------------------------------
         panelPrincipal.setLayout(new BorderLayout(5, 5));
         pOeste.setLayout(new GridLayout(0, 1, 10, 6));
         pNorte.setLayout(new FlowLayout(FlowLayout.LEFT));//en realidad por defecto es flowlayout
         pCentro.setLayout(new FlowLayout(FlowLayout.LEFT));
         pGridLayoutCentro.setLayout(new GridLayout(0, 1, 6, 6));
         pSur.setLayout(new FlowLayout(FlowLayout.RIGHT));
         pBtnClientes.setLayout(new FlowLayout(FlowLayout.LEFT));
         pBtnProductos.setLayout(new FlowLayout(FlowLayout.LEFT));
         //-----------------bordes a panel-------------------------------------------------------------------
         sc.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                                "PRODUCTOS",
                                TitledBorder.LEFT,
                                TitledBorder.TOP));
         sc2.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                                "CLIENTES",
                                TitledBorder.LEFT,
                                TitledBorder.TOP));
         pCentro.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                                "PRINCIPAL",
                                TitledBorder.CENTER,
                                TitledBorder.TOP));  
         outputPane.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                                "FACTURA ACTUAL",
                                TitledBorder.CENTER,
                                TitledBorder.TOP));                                 
         pSur.setBorder(new EtchedBorder());
         pNorte.setBorder(new EtchedBorder());
         //----------------componentes swing a paneles------------------------------------------------------
         pNorte.add(btn1);
         pOeste.add(btn5);
         pOeste.add(btn6);
         pOeste.add(btn7);
         pOeste.add(btn8); 
         pOeste.add(btn9); 
         pFlowOeste.add(pOeste);         
         pSur.add(outputPane);
         pGridLayoutCentro.add(sc2);
         pBtnClientes.add(btn10);
         pBtnClientes.add(btn11);
         pGridLayoutCentro.add(pBtnClientes);         
         pGridLayoutCentro.add(sc); 
         pBtnProductos.add(btn2); 
         pBtnProductos.add(btn12);
         pGridLayoutCentro.add(pBtnProductos);
         pCentro.add(pGridLayoutCentro);
         //----------------agregar paneles, al panel principal-------------------------------------------
         panelPrincipal.add(pNorte, BorderLayout.NORTH);
         panelPrincipal.add(pFlowOeste, BorderLayout.WEST);
         panelPrincipal.add(pSur, BorderLayout.SOUTH);
         panelPrincipal.add(pCentro, BorderLayout.CENTER);
         //----------------especifico parametros para ventana principal----------------------------------        
         //ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         ventanaPrincipal.addWindowListener(this);
         ventanaPrincipal.setSize(1175,375);
         ventanaPrincipal.pack();
         ventanaPrincipal.setResizable(false);
         ventanaPrincipal.setVisible(true);
    }
    //metodo sobrecargado de WindowAdapter
    public void windowClosing(WindowEvent e) {
        sbd.cerrarConexion();
        System.out.println("Conexion: "+sbd.getIsCon());
        ventanaPrincipal.dispose();//Releases all of the native screen resources used by this Window, its subcomponents, and all of its owned children.
        System.exit(0);
    }

    /**
     * Metodo que intenta cerrar la factura actual, crando una nueva venta.
     * ranges from <code>0</code> to <code>length() - 1</code>.
     *
     * @param     Ventas ventaActual, ArrayList<Productos> prodAgregados.
     * @return    Boolean Verdad si realizo la transaccion exitosamente.
     * @see       Query, getUVenta, getR, Productos
     */    
    public boolean cerrarFacturaActual(Ventas ventaActual, ArrayList<Productos> prodAgregados) {
        boolean res = true;
       if(sbd.query("INSERT INTO VENTAS  ( ID_VENTA , ID_CLIENTE , FECHA , ESTADO ) VALUES ( "+1+" , "+ventaActual.getIdCliente()+" , '"+ventaActual.getFecha()+"' , 0 )")) {
           int u = getUVenta();
           for(Productos p : prodAgregados) {//ahora genero tuplas en detalle_ventas
               if(sbd.query("INSERT INTO DETALLE_VENTAS (ID_PRODUCTO, ID_VENTA, CANTIDAD_PRODUCTO, SUBTOTAL_VENTA) VALUES ("+p.getIdProducto()+" , "+u+" ,"+p.getCan()+", "+-111+" )")== false)res = false;
           }
           sbd.query("UPDATE VENTAS a SET a.ESTADO = 1 WHERE a.ID_CLIENTE = "+ventaActual.getIdCliente()+" AND a.ESTADO = 0");
        }else {
            res = false;
        }
        return res;
    }   
    
    /**
     * Retorna ultimo id para crear nueva venta.
     * @return Retorna ultimo id para crear nueva venta.
     */
    public int getUVenta() {
        int id = -1;
        try {
            // obtengo datos de la tabla desde el objeto resultset
            ResultSet rs = sbd.getR("SELECT GEN_ID(GEN_VENTAS_ID,0) FROM RDB$DATABASE");
            rs.next(); 
            id = rs.getInt("GEN_ID");
        }catch(SQLException e) {
            System.out.println("algo paso obteniendo la ultima venta.");
            System.out.println(e.getMessage());            
        }finally {
            return id;
        }
    }  
    
    public static void main(String args[]) {
        new VentanaPrincipal().crearVentana();
    }
}