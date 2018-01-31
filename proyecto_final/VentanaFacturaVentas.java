import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.event.*;
import java.util.*;
import java.awt.Dimension;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.*;
import java.sql.*;
/**
 * Clase que representa una ventana, donde se visualizan todas las ventas.
 */
public class VentanaFacturaVentas {
    private JFrame VentanaFacturaVentas = new JFrame("Facturas Ventas");
    private Container panelPrincipal = VentanaFacturaVentas.getContentPane();
    //JTables
    private ModelFacturaVentas mfv = new ModelFacturaVentas();
    private JTable tblFVentas = new JTable(mfv);
    //paneles
    private JPanel pCentro = new JPanel();
    private JPanel pSur = new JPanel();
    //botones
    private JButton btnActualizar  = new JButton("Actualizar");   
    private JButton btnPorCliente = new JButton("Por Cliente"); 
    private JButton btnPorVenta = new JButton("Por Venta"); 
    private JButton btnPorFecha = new JButton("Por Fecha"); 
    
    public VentanaFacturaVentas() {
        crarVentanaFacturaVentas();
    }
    /**
     * Un metodo para crear ventana VentanaFacturaVentas.
     */
    public void crarVentanaFacturaVentas() {
        //gestores de disposicion
        panelPrincipal.setLayout(new BorderLayout(6, 6));                             
        pCentro.setBorder(new EtchedBorder());   
        pSur.setBorder(new EtchedBorder()); 
        //eventos de jtables
        btnActualizar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(mfv.recargar()) {
                   JOptionPane.showMessageDialog(panelPrincipal, "Pagina Actualizada!");
                }else {
                    JOptionPane.showMessageDialog(panelPrincipal, "No se pudo Actualizar desde Base De Datos");
                }
                
            }});   
        btnPorCliente.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int id = Integer.parseInt((JOptionPane.showInputDialog(panelPrincipal, "Ingrese id Cliente", "1")));
                if(mfv.recargarPorIdCliente(id)) {    
                  JOptionPane.showMessageDialog(panelPrincipal, "Cliente Cargado!");
                }else {
                    JOptionPane.showMessageDialog(panelPrincipal, "Verifique Datos!");
                }
                
            }});  
        btnPorVenta.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int id = Integer.parseInt((JOptionPane.showInputDialog(panelPrincipal, "Ingrese id Venta", "1")));
                if(mfv.recargarPorIdVenta(id)) {    
                  JOptionPane.showMessageDialog(panelPrincipal, "Venta Cargada!");
                }else {
                    JOptionPane.showMessageDialog(panelPrincipal, "Verifique Datos!");
                }
                
            }});  
        btnPorFecha.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String str = JOptionPane.showInputDialog(panelPrincipal, "Ingrese Fecha", "mm/dd/yyyy");
                if(mfv.recargarPorFecha(str)) {    
                  JOptionPane.showMessageDialog(panelPrincipal, "Fecha Cargada!");
                }else {
                    JOptionPane.showMessageDialog(panelPrincipal, "Verifique Datos!");
                }
                
            }});              
        JScrollPane sc = new JScrollPane(tblFVentas);
        pCentro.add(sc);
        pSur.add(btnPorCliente);
        pSur.add(btnPorVenta);
        pSur.add(btnPorFecha);
        pSur.add(btnActualizar);        
        panelPrincipal.add(pSur);        
        panelPrincipal.add(pCentro, BorderLayout.NORTH);        
        
        //VentanaFacturaVentas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        VentanaFacturaVentas.pack();
        VentanaFacturaVentas.setResizable(false);
        VentanaFacturaVentas.setVisible(true);
        
    }
    
    
    
}
