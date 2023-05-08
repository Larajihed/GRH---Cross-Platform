/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.infrastructure;
import java.sql.*;
import COM.HRSTORMDESKTOP.Config;
/**
 *
 * @author 21623
 */
public class DBConnector {
    private String url = Config.getUrl();
    private String user = Config.getUser();
    private String password = Config.getPassword();

    private Connection cnx;
    private static DBConnector instance;
    
    private DBConnector() {
        
        try {
            cnx = DriverManager.getConnection(url, user, password);
            System.out.println("Database Connected");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static DBConnector getInstance() {
        if(instance == null){
            instance = new DBConnector();
        }
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }    
      
}
