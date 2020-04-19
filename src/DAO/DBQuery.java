/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Danielle
 */
public class DBQuery {
    
    	private static PreparedStatement statement; // statement reference 
	
	// create statement object
	
	public static void setPreparedStatement(Connection connection, String sqlStatement)throws SQLException
	{
		statement = connection.prepareStatement(sqlStatement);
		                                                                                                            // "add throws dause for java.sql.SQLException"
	}
	
	// return statement obj
	
	public static PreparedStatement getPreparedStatement()
	{
	   return statement; 
	}

    
}
