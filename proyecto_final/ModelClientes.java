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
public class ModelClientes extends AbstractTableModel {
    private List<Clientes> cli = new ArrayList<Clientes>();   
    
    public ModelClientes() {
        recargar();
    }
    //metodos abstractos de AbstractTableModel
    public int getRowCount() {
        return cli.size();
    }
    public int getColumnCount() {
        return 3;
    }
    public Object getValueAt(int row, int column) {
        //return data.get(row).get(column);//le doy la ubicacion del dato a mostrar en la matriz {0.0},{0.1},{0.2},{0.3}
        switch (column) {
            case 0:
                return cli.get(row).getID();
            case 1:
                return cli.get(row).getNombreCliente();
            case 2:
                return cli.get(row).getSaldo();
        }
        return null;
    }   
    // le doy un titulo a cada columna
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "ID";
            case 1:
                return "NOMBRE CLIENTE";
            case 2:
                return "SALDO";
        }
        return null;
    }
    public Class getColumnClass(int c) {
        switch(c) {
            case 0:
                return Integer.class;
            case 1: 
                return String.class;
            case 2:
                return Float.class;
        } 
        return null;
    }    
    public boolean recargar() {
        cli.clear();
        try{
            //traigo los datos de la tabla
            ResultSet rs = new ServicioBaseDatos().getR("select *from clientes");        
            // obtengo datos de la tabla desde el objeto resultset
            while (rs.next()) {
                cli.add(new Clientes(rs.getInt("ID_CLIENTE"), rs.getString("NOMBRE_CLIENTE"), rs.getFloat("SALDO")));
            } 
            fireTableDataChanged();
            return true;
        } catch(SQLException e) {
            System.out.println("algo paso con el sql...");
            return false;
        }         
    }
   
}
