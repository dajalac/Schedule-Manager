/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.User;
import java.sql.SQLException;

/**
 *
 * @author Danielle
 */
public interface UserDAO {
    
    // used for the login screen
    public User getUser(String user, String password)throws SQLException, Exception; 
    
    // used for set active 1 or 0
    
    public void setUserAct(int ID) throws SQLException, Exception ;
    
    // used for get the user ID needed in the appointment table
    // used for get the user name needed in all tables in the createdby field
    
    public User getUserNameId(int act)throws SQLException, Exception;
    
    
    
}
