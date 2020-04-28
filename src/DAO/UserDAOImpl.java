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
public class UserDAOImpl implements UserDAO{

      
    /**
     * 
     * @param user
     * @param pswd
     * @return
     * @throws SQLException
     * @throws Exception 
     */        
    @Override
    public User getUser(String user, String pswd) throws SQLException, Exception {
      
        User userResult;
        
        //query
        String sql = "SELECT userId, userName, password, active FROM user WHERE userName = BINARY ? AND password = BINARY ?";
     
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setString(1, user);
	prSt.setString(2, pswd);
        
        ResultSet result = prSt.executeQuery();
       
        if(result !=null){
	    
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
	
	   }
        DBConnection.closeConnection();
         
	    return null;
       
    }

    /**
     * 
     * @param ID
     * @throws SQLException
     * @throws Exception 
     */
    @Override
    public void setUserAct(int ID)throws SQLException, Exception  {
       
        
        Connection conn= DBConnection.makeConnection(); // making the connection
       
        // turn on safe mode
        String safeUpdadeOn = "SET SQL_SAFE_UPDATES =0; ";
        PreparedStatement prStsafeOn = conn.prepareStatement(safeUpdadeOn );
        prStsafeOn.executeUpdate();
        
        // update all to false
        String setToFalse = "UPDATE user SET active = 0 ";
   
        PreparedStatement prStSetFalse = conn.prepareStatement(setToFalse);
        prStSetFalse.executeUpdate();
        
        //turning off safe update
        String safeUpdadeOff = "SET SQL_SAFE_UPDATES = 1" ;
        PreparedStatement prStOff = conn.prepareStatement(safeUpdadeOff);
        prStOff.executeUpdate(); // no needs for result set, because I won't retrive any value
        
       // Set just the current user to active true
        
        String setTruActive = "UPDATE user SET active = 1 WHERE userId= ?" ;
        PreparedStatement prStSetTrue = conn.prepareStatement(setTruActive);
        prStSetTrue.setInt(1, ID);
        prStSetTrue.executeUpdate();
        
    }

    /**
     * 
     * @param act
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    @Override
    public User getUserNameId(int act)throws SQLException, Exception {
        User userResult;
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        String sql = "SELECT userId, userName FROM user WHERE active = ?"; 
        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setInt(1, act);
        ResultSet result = prSt.executeQuery();
       
        if(result !=null){
            while(result.next()){
	      int userid=result.getInt("userid");
	      String userName=result.getString("userName");
 
              // save in the User object
	      userResult= new User(userid, userName);
	     
	      return userResult;
            }
	
	   }
        DBConnection.closeConnection();
         
	    return null;
        
    }

  }
  
