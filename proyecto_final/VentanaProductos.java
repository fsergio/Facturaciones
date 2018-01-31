import javax.swing.JTable;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Container;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JOptionPane;
import javax.swing.JTextField ;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.border.*;//TitledBorder, EtchedBorder, EmptyBorder
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Write a description of class VentanaProductos here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class VentanaProductos {
    private ServicioBaseDatos sbd = new ServicioBaseDatos();
    private ArrayList<Productos> prod = new ArrayList<Productos>();
    //JTables
    private ModelProductos mp = new ModelProductos();
    private JTable tblProductos = new JTable(mp);
    //paneles
    private JFrame ventanaProductos = new JFrame("Productos");
    private Container panelPrincipal = ventanaProductos.getContentPane();
    private JPanel pCentro = new JPanel();
    private JPanel pSur = new JPanel();
    private JPanel pNorte = new JPanel();
    private JPanel pNuevoProducto = new JPanel();
    private JPanel pNuevoProductoBtn = new JPanel();
    private JPanel pModificarBtn = new JPanel();
    private JPanel pModificar = new JPanel();    
    //botones
    private JButton btnAgregarProducto = new JButton("Agregar nuevo producto");
    private JButton btnActualizar = new JButton("Actualizar");
    private JButton btnModificar = new JButton("Modificar Producto");
    //JText
    private JTextField tDescripcion = new JTextField("descripcion", 20);
    private JTextField tPrecio = new JTextField("precio", 20);    
    private JTextField tStock = new JTextField("stock", 20);
    //prueba
    private JTextField tIdM = new JTextField("id producto a modificar", 20);
    private JTextField tPrecioM = new JTextField("precio", 20);    
    private JTextField tStockM = new JTextField("stock", 20);    

    
    public VentanaProductos() {
        crearVentanaProductos();
    }
    
    public void crearVentanaProductos() {
        //configuraciones JTables
        tblProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sc = new JScrollPane(tblProductos); 
        //-----------------bordes a panel-------------------------------------------------------------------
  
        sc.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                                "STOCK",
                                TitledBorder.LEFT,
                                TitledBorder.TOP));  
        pSur.setBorder(new EtchedBorder()); 
        pModificar.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                                "MODIFICAR PRODUCTO",
                                TitledBorder.CENTER,
                                TitledBorder.TOP)); 
        pNuevoProducto.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                                "NUEVO PRODUCTO",
                                TitledBorder.CENTER,
                                TitledBorder.TOP));                             
        pNorte.setBorder(new EtchedBorder()); 
        pCentro.setBorder(new EtchedBorder()); 
        //gestores de disposicion
        panelPrincipal.setLayout(new BorderLayout(6, 6));
        pNuevoProducto.setLayout(new GridLayout(0, 1, 6, 6));
        pCentro.setLayout(new FlowLayout(FlowLayout.LEFT));
        pModificar.setLayout(new GridLayout(0, 1, 6, 6));
        //eventos de botones
        btnAgregarProducto.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Productos p = new Productos(1, 
                tDescripcion.getText().toString(), 
                (Float.parseFloat(tPrecio.getText())), 
                (Float.parseFloat(tStock.getText())));
                if(sbd.query("INSERT INTO PRODUCTOS (ID_PRODUCTO, DESCRIPCION, PRECIO, STOCK) VALUES ("+p.getIdProducto()+",'"+p.getDescripcion()+"',"+p.getPrecio()+","+p.getStock()+")")) {
                    JOptionPane.showMessageDialog(ventanaProductos.getContentPane(), "Nuevo Producto Agregado");
                    mp.recargar();
                }else {
                    JOptionPane.showMessageDialog(panelPrincipal, "No se pudo agregar Nuevo Producto");
                }
            }});  
        btnActualizar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(mp.recargar()) {
                    JOptionPane.showMessageDialog(ventanaProductos.getContentPane(), "Pagina Actualizada");
                }else {
                    JOptionPane.showMessageDialog(ventanaProductos.getContentPane(), "No se pudo Actualizar");
                }
                
            }});   
        btnModificar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Productos p = new Productos(Integer.parseInt(tIdM.getText()),
                "para actualizar", 
                (Float.parseFloat(tPrecioM.getText().toString())), 
                (Float.parseFloat(tStockM.getText())));
                if(sbd.query("UPDATE PRODUCTOS P SET P.PRECIO = "+p.getPrecio()+" WHERE P.ID_PRODUCTO = "+p.getIdProducto())) {
                    if(sbd.query("UPDATE PRODUCTOS P SET P.STOCK = "+p.getStock()+"  WHERE P.ID_PRODUCTO = "+p.getIdProducto())) {
                        JOptionPane.showMessageDialog(ventanaProductos.getContentPane(), "Modificacion exitosa");
                        mp.recargar();
                    }
                }else {
                    JOptionPane.showMessageDialog(panelPrincipal, "Verifique los datos ingresados");
                }                
            }});             
        //----------------componentes swing a paneles------------------------------------------------------
        pNorte.add(sc);               
        pNuevoProducto.add(tDescripcion);
        pNuevoProducto.add(tPrecio);
        pNuevoProducto.add(tStock);
        pNuevoProductoBtn.add(btnAgregarProducto);
        pNuevoProducto.add(pNuevoProductoBtn);
        //para modificar un producto
        pModificarBtn.add(btnModificar);
        pModificar.add(tIdM);
        pModificar.add(tPrecioM);
        pModificar.add(tStockM);
        pModificar.add(pModificarBtn);
        //--
        pSur.add(btnActualizar);
        //pSur.add(btnModificar);
        pCentro.add(pNuevoProducto);
        pCentro.add(pModificar);
        //----------------agregar paneles, al panel principal-------------------------------------------
        panelPrincipal.add(pCentro, BorderLayout.CENTER);        
        panelPrincipal.add(pNorte, BorderLayout.NORTH);  
        panelPrincipal.add(pSur, BorderLayout.SOUTH);
        //ventanaVentas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaProductos.pack();
        ventanaProductos.setResizable(false);
        ventanaProductos.setVisible(true);
    }  
}
