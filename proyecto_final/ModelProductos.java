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
public class ModelProductos extends AbstractTableModel {
    private List<Productos> prod = new ArrayList<Productos>();   
    
    public ModelProductos() {
        recargar();
    }
    //metodos abstractos de AbstractTableModel
    public int getRowCount() {
        return prod.size();
    }
    public int getColumnCount() {
        return 4;
    }
    public Object getValueAt(int row, int column) {
        //return data.get(row).get(column);//le doy la ubicacion del dato a mostrar en la matriz {0.0},{0.1},{0.2},{0.3}
        switch (column) {
            case 0:
                return prod.get(row).getIdProducto();
            case 1:
                return prod.get(row).getDescripcion();
            case 2:
                return prod.get(row).getPrecio();
            case 3:
                return prod.get(row).getStock();
        }
        return null;
    }   
    // le doy un titulo a cada columna
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "ID";
            case 1:
                return "DESCRIPCION";
            case 2:
                return "PRECIO";
            case 3:
                return "STOCK";
        }
        return null;
    }
    
    public void removeRow(int row) {
        // remove a row from your internal data structure
        prod.remove(row);
        fireTableRowsDeleted(row, row);
    }
    
    public void addRow(Productos p) {
        prod.add(p);
        fireTableDataChanged();
    }
    
    public void setValueAt(Productos p, int row, int col) {
        prod.add(p);
        fireTableCellUpdated(row, col);        
    }       
    public Class getColumnClass(int c) {
        switch(c) {
            case 0:
                return Integer.class;
            case 1: 
                return String.class;
            case 2:
                return Float.class;
            case 3:
                return Float.class;
        } 
        return null;
    }     
    public boolean recargar() {
        try{
            prod.clear();
            //traigo los datos de la tabla
            ResultSet rs = new ServicioBaseDatos().getR("select *from productos");
            // obtengo datos de la tabla desde el objeto resultset
            while (rs.next()) {
                prod.add(new Productos(rs.getInt("ID_PRODUCTO"), rs.getString("DESCRIPCION"), rs.getFloat("PRECIO"), rs.getFloat("STOCK")));
            }  
            fireTableDataChanged();
            return true;
        } catch(SQLException e) {
            System.out.println("algo paso con el sql...");
            return false;
        }          
    }   
}
