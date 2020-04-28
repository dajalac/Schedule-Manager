/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Utils.DisplayTerminal;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Danielle
 */
public class DBConnection {
    
   //private static final String databaseName = "U06MBK ";
   private static final String DB_URL = "jdbc:mysql://3.227.166.251/U06MBK";
   private static final String username = "U06MBK";
   private static final String password = "53688804126";
   private static final String driver =  "com.mysql.jdbc.Driver"; 
   
   // public variable to hold connection 
           static Connection connection;

	// create connection to db
     
     
    
	   public static Connection makeConnection() {
        // Lambda expression
        //It avoid the repetition of System.out.println, can be used for debug and can be reused. 
        DisplayTerminal dT = (msg) -> System.out.println(msg);

        try {
            Class.forName(driver);
            connection = (Connection) DriverManager.getConnection(DB_URL, username, password);
            //dT.showInTerminal("connection successful");
        } catch (ClassNotFoundException e) {
            dT.showInTerminal("ERROR" + e.getMessage());
        } catch (SQLException e) {
            dT.showInTerminal("ERROR" + e.getMessage());

        }
        return connection;
    }

	// to close connection
	
       public static void closeConnection() {

        DisplayTerminal dT = (msg) -> System.out.println(msg);
        
        try {
            connection.close();
            //dT.showInTerminal("Connection is closed!");
        } catch (SQLException e) {
            dT.showInTerminal("ERROR" + e.getMessage());

        }
    }
}
