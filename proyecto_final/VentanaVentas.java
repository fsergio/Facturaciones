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
/**
 * Write a description of class VentanaVentas here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class VentanaVentas {
    private JFrame ventanaVentas = new JFrame("Ventas");
    private Container panelPrincipal = ventanaVentas.getContentPane();
    //JTables
    private ModelVentas mv = new ModelVentas();
    private JTable tblVentas = new JTable(mv);
    //paneles
    private JPanel pCentro = new JPanel();
    private JPanel pSur = new JPanel();
    //botones
    private JButton btnActualizar  = new JButton("Actualizar");   
    public VentanaVentas() {
        crarVentanaVentas();
    }
    
    public void crarVentanaVentas() {
        //gestores de disposicion
        panelPrincipal.setLayout(new BorderLayout());
        //eventos de jtables
        btnActualizar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(mv.recargar()) {
                  //nada
                }else {
                    JOptionPane.showMessageDialog(null, "No se pudo Actualizar desde Base De Datos");
                }
                
            }});              
        JScrollPane sc = new JScrollPane(tblVentas);
        pCentro.add(sc);
        pSur.add(btnActualizar);        
        panelPrincipal.add(pSur);        
        panelPrincipal.add(pCentro, BorderLayout.NORTH);        
        
        //ventanaVentas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaVentas.pack();
        ventanaVentas.setResizable(false);
        ventanaVentas.setVisible(true);
        
    }
    
    
    
}
