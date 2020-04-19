/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Danielle
 */
public class UserDAOImpl implements UserDAO {

      
            
    @Override
    public User getUser(String user, String pswd) throws SQLException, Exception {
      
        
        Connection conn = DBConnection.makeConnection();// needed to create connection
	PreparedStatement prSt = DBQuery.getPreparedStatement(); // to pass variables to query
	ResultSet result;
	User userResult; // criate a user Object
		 
		
	// query 
	StringBuilder sql = new StringBuilder(); // to write the query
	sql.append("SELECT *");
	sql.append("FROM user");
	sql.append("WHERE userName = ?");
	sql.append("AND password = ?");

        // sent to query class
        DBQuery.setPreparedStatement(conn, sql.toString());
	
	// give values to ? 	
	prSt.setString(1,user);
	prSt.setString(2, pswd);

        // to execute the query
         result = prSt.executeQuery();

	
	// to get the user selected
	  while(result.next()){
	      
	      int userid=result.getInt("userid");
	      String userName=result.getString("userName");
	      String password=result.getString("password");
	      int act=result.getInt("active");
              
              // save in the User object
              // I will have just one user, that's why there is no need to create a list
	      userResult= new User(userid, userName, password, act);
	     
	      return userResult;
	
	   }
	   
	    
	    DBConnection.closeConnection();
	    return null;
	}

    @Override
    public void setUserAct(int ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User getUserNameId(int act) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  }
  
