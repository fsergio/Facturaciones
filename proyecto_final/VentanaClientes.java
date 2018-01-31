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
 * Write a description of class VentanaClientes here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class VentanaClientes {
    private ServicioBaseDatos sbd = new ServicioBaseDatos();
    //JTables
    private ModelClientes mc = new ModelClientes();
    private JTable tblClientes = new JTable(mc);
    //paneles
    private JFrame VentanaClientes = new JFrame("Clientes");
    private Container panelPrincipal = VentanaClientes.getContentPane();
    private JPanel pCentro = new JPanel();
    private JPanel pSur = new JPanel();
    private JPanel pNorte = new JPanel();
    private JPanel pNuevoCliente = new JPanel();
    private JPanel pNuevoClienteBtn = new JPanel();   
    //botones
    private JButton btnAgregarCliente = new JButton("Agregar nuevo Cliente");
    private JButton btnActualizar = new JButton("Actualizar");
    //JText
    private JTextField tNombreCliente = new JTextField("nombre cliente", 20);  
    
    public VentanaClientes() {
        crearVentanaClientes();
    }
    
    public void crearVentanaClientes() {
        //configuraciones JTables
        tblClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sc = new JScrollPane(tblClientes); 
        //-----------------bordes a panel-------------------------------------------------------------------
  
        sc.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                                "CLIENTES",
                                TitledBorder.LEFT,
                                TitledBorder.TOP));  
        pSur.setBorder(new EtchedBorder());  
        pNuevoCliente.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                                "NUEVO CLIENTE",
                                TitledBorder.CENTER,
                                TitledBorder.TOP));                             
        pNorte.setBorder(new EtchedBorder()); 
        pCentro.setBorder(new EtchedBorder()); 
        //gestores de disposicion
        panelPrincipal.setLayout(new BorderLayout(6, 6));
        pNuevoCliente.setLayout(new GridLayout(0, 1, 6, 6));
        //eventos de botones
        btnAgregarCliente.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String cliente = tNombreCliente.getText().toString(); 
                if(sbd.query("INSERT INTO CLIENTES (ID_CLIENTE, NOMBRE_CLIENTE, SALDO) VALUES ( 1, '"+cliente+"' , 0 )")) {
                    JOptionPane.showMessageDialog(VentanaClientes.getContentPane(), "Nuevo Cliente Agregado");
                    mc.recargar();
                }else {
                    JOptionPane.showMessageDialog(panelPrincipal, "No se pudo agregar Nuevo Cliente");
                }
            }});  
        btnActualizar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(mc.recargar()) {
                    JOptionPane.showMessageDialog(VentanaClientes.getContentPane(), "Pagina Actualizada");
                }else {
                    JOptionPane.showMessageDialog(VentanaClientes.getContentPane(), "No se pudo Actualizar");
                }
                
            }});               
        //----------------componentes swing a paneles------------------------------------------------------
        pNorte.add(sc);               
        pNuevoCliente.add(tNombreCliente);
        pNuevoClienteBtn.add(btnAgregarCliente);
        pNuevoCliente.add(pNuevoClienteBtn);
        pSur.add(btnActualizar);
        //pSur.add(btnModificar);
        pCentro.add(pNuevoCliente);
        //----------------agregar paneles, al panel principal-------------------------------------------
        panelPrincipal.add(pCentro, BorderLayout.CENTER);        
        panelPrincipal.add(pNorte, BorderLayout.NORTH);  
        panelPrincipal.add(pSur, BorderLayout.SOUTH);
        VentanaClientes.pack();
        VentanaClientes.setResizable(false);
        VentanaClientes.setVisible(true);
    }  
}

