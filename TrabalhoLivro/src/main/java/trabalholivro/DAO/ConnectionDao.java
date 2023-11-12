package trabalholivro.DAO;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConnectionDao {
    public Connection conectaBD(){
        Connection conn = null;
    
        try {
            
            String url = "jdbc:mysql://localhost:3306/library?user=root&password=";
            conn = DriverManager.getConnection(url);       
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "javaCrude.Dao/ConnectionDAO: " + erro.getMessage());
        }
        
        return conn; 
        
    }
}