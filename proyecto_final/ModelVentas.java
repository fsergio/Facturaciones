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
public class ModelVentas extends AbstractTableModel {
    private List<Ventas> ventas = new ArrayList<Ventas>();   
    
    public ModelVentas() {
        recargar();
    }
    //metodos abstractos de AbstractTableModel
    public int getRowCount() {
        return ventas.size();
    }
    public int getColumnCount() {
        return 4;
    }
    public Object getValueAt(int row, int column) {
        //return data.get(row).get(column);//le doy la ubicacion del dato a mostrar en la matriz {0.0},{0.1},{0.2},{0.3}
        switch (column) {
            case 0:
                return ventas.get(row).getIdVenta();
            case 1:
                return ventas.get(row).getIdCliente();
            case 2:
                return ventas.get(row).getFecha();
            case 3:
                return ventas.get(row).getEstado();
        }
        return null;
    }   
    // le doy un titulo a cada columna
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "ID VENTA";
            case 1:
                return "ID CLIENTE";
            case 2:
                return "FECHA";
            case 3:
                return "ESTADO";
        }
        return null;
    }  
    public void removeRow(int row) {
        // remove a row from your internal data structure
        ventas.remove(row);
        fireTableRowsDeleted(row, row);
    }    
    public void addRow(Ventas v) {
        ventas.add(v);
        fireTableDataChanged();
    }
    public void setValueAt(Ventas v, int row, int col) {
        ventas.add(v);
        fireTableCellUpdated(row, col);
    }   
    public Class getColumnClass(int c) {
        switch(c) {
            case 0:
                return Integer.class;
            case 1: 
                return Integer.class;
            case 2:
                return Date.class;
            case 3:
                return Integer.class;
        } 
        return null;
    }    
    public boolean recargar() {
        ventas.clear();
        try{
            //traigo los datos de la tabla
            ResultSet rs = new ServicioBaseDatos().getR("SELECT *FROM VENTAS");         
            // obtengo datos de la tabla desde el objeto resultset
            while (rs.next()) {
                ventas.add(new Ventas(rs.getInt("ID_VENTA"), rs.getInt("ID_CLIENTE"), rs.getDate("FECHA"), rs.getInt("ESTADO")));
            }  
            fireTableDataChanged();
            return true;
        } catch(SQLException e) {
            System.out.println("modelVentas.recargar: "+e.getMessage());
            return false;
        }     
    }    
}
