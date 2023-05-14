/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Philip
 */
public class DBConnection {
    
    
    //JDBC URL-specify necessary information for java programs to establish connection with any database
    
    private final String URL="jdbc:mysql://localhost:3306/dbemployeedemo";
    private final String USER="root";
    private final String PASS="";
    
    
    private Connection sqlConnection;
    
    
    
    public void connectDatabase(){
        try{
            //establish connection with the database
          
            sqlConnection=DriverManager.getConnection(URL, USER, "");
        //    System.out.println("Connection successful");
        }catch(SQLException ex){
            System.err.println(ex);
            System.out.println("Not connected to database");
        }
    }

    public Connection getSqlConnection() {
        return sqlConnection;
    }
    
    
}
