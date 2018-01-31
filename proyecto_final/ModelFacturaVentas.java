import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
/**
 * Write a description of class ModelCliente here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ModelFacturaVentas extends AbstractTableModel {
    private List<FacturaVentas> fv = new ArrayList<FacturaVentas>();   
    
    public ModelFacturaVentas() { 
        recargar();
    }
    //metodos abstractos de AbstractTableModel
    public int getRowCount() {
        return fv.size();
    }
    public int getColumnCount() {
        return 4;
    }
    public Object getValueAt(int row, int column) {
        //return data.get(row).get(column);//le doy la ubicacion del dato a mostrar en la matriz {0.0},{0.1},{0.2},{0.3}
        switch (column) {
            case 0:
                return fv.get(row).getIdVenta();
            case 1:
                return fv.get(row).getFecha();
            case 2:
                return fv.get(row).getIdCliente();
            case 3:
                return fv.get(row).getTotal();
        }
        return null;
    }   
    // le doy un titulo a cada columna
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "ID VENTA";
            case 1:
                return "FECHA";
            case 2:
                return "ID CLIENTE";
            case 3:
                return "TOTAL";
        }
        return null;
    }  
    public void removeRow(int row) {
        // remove a row from your internal data structure
        fv.remove(row);
        fireTableRowsDeleted(row, row);
    }
    
    public void addRow(FacturaVentas f) {
        fv.add(f);
        fireTableDataChanged();
    }
    
    public void setValueAt(FacturaVentas f, int row, int col) {
        fv.add(f);
        fireTableCellUpdated(row, col);
    }  
    public Class getColumnClass(int c) {
        switch(c) {
            case 0:
                return Integer.class;
            case 1: 
                return Date.class;
            case 2:
                return Integer.class;
            case 3:
                return Float.class;
        } 
        return null;
    }    
    public boolean recargar() {
        fv.clear();
        try{
            //traigo los datos de la tabla
            ResultSet rs = new ServicioBaseDatos().getR("SELECT *FROM v_VENTA");         
            // obtengo datos de la tabla desde el objeto resultset
            while (rs.next()) {
                fv.add(new FacturaVentas(rs.getInt("ID_VENTA"), rs.getDate("FECHA"), rs.getInt("ID_CLIENTE"), rs.getFloat("VTOTAL")));
            }  
            fireTableDataChanged();
            return true;
        } catch(SQLException e) {
            System.out.println("modelfacturaventas.recargar: "+e.getMessage());
            return false;
        }     
    }    
    public boolean recargarPorIdCliente(int id) {
        fv.clear();
        try{
            //traigo los datos de la tabla
            ResultSet rs = new ServicioBaseDatos().getR("SELECT *FROM v_VENTA WHERE ID_CLIENTE = "+id);      
            // obtengo datos de la tabla desde el objeto resultset
            while (rs.next()) {
                fv.add(new FacturaVentas(rs.getInt("ID_VENTA"), rs.getDate("FECHA"), rs.getInt("ID_CLIENTE"), rs.getFloat("VTOTAL")));
            }  
            fireTableDataChanged();
            return true;
        } catch(SQLException e) {
            System.out.println("modelfacturaventas.recargaridc: "+e.getMessage());
            return false;
        }     
    }  
    public boolean recargarPorIdVenta(int id) {
        fv.clear();
        try{
            //traigo los datos de la tabla
            ResultSet rs = new ServicioBaseDatos().getR("SELECT *FROM v_VENTA WHERE ID_VENTA = "+id);        
            // obtengo datos de la tabla desde el objeto resultset
            while (rs.next()) {
                fv.add(new FacturaVentas(rs.getInt("ID_VENTA"), rs.getDate("FECHA"), rs.getInt("ID_CLIENTE"), rs.getFloat("VTOTAL")));
            }  
            fireTableDataChanged();
            return true;
        } catch(SQLException e) {
            System.out.println("modelfacturaventas.recargaridv: "+e.getMessage());
            return false;
        }     
    }     
    public boolean recargarPorFecha(String fecha) {
        fv.clear();
        try{
            //traigo los datos de la tabla
            ResultSet rs = new ServicioBaseDatos().getR("SELECT *FROM v_VENTA WHERE FECHA = '"+fecha+"'");         
            // obtengo datos de la tabla desde el objeto resultset
            while (rs.next()) {
                fv.add(new FacturaVentas(rs.getInt("ID_VENTA"), rs.getDate("FECHA"), rs.getInt("ID_CLIENTE"), rs.getFloat("VTOTAL")));
            }  
            fireTableDataChanged();
            return true;
        } catch(SQLException e) {
            System.out.println("modelfacturaventas.recargarf: "+e.getMessage());
            return false;
        }     
    }      
}
