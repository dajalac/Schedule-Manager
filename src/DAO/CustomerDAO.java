/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Customer;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author Danielle
 */
public interface CustomerDAO {
    
    public void insertCustomer (String customer, int addressId, String userName)throws SQLException, Exception; 
    
    public void UpdateCustomer (int customerId,String customer, int addressId, String userName)throws SQLException, Exception; 
    
    public Customer selectedCustomer (String customerName)throws SQLException, Exception; 
    
    public ObservableList<Customer> getAllCustomers ()throws SQLException, Exception; 
    
    public void deleteCustomer (int customerId)throws SQLException, Exception;
    
    public Customer customerName (int customerId)throws SQLException, Exception;
}
