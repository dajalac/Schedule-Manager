/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Danielle
 */
public class CustomerDAOImpl implements CustomerDAO {

    /**
     * 
     * @param customer
     * @param addressId
     * @param userName
     * @throws SQLException
     * @throws Exception 
     */
    @Override
    public void insertCustomer(String customer, int addressId, String userName) throws SQLException, Exception {
        
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        String sql = "INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy)"+
                      "VALUES(?,?,1,CURRENT_DATE(),?,CURRENT_TIMESTAMP,?)";
        
        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setString(1, customer);
        prSt.setInt(2, addressId);
        prSt.setString(3, userName);
        prSt.setString(4, userName);
        
        prSt.executeUpdate();
        
        DBConnection.closeConnection();
    }

    /**
     * 
     * @param customerId
     * @param customer
     * @param addressId
     * @param userName
     * @throws SQLException
     * @throws Exception 
     */
    @Override
    public void UpdateCustomer(int customerId, String customer, int addressId, String userName) throws SQLException, Exception {
      
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        String sql = "UPDATE customer SET "+
                "customerName = ? , " + 
                "addressId = ? , "+
                "lastUpdateBy = ? "+
                "WHERE customerId = ?";
        
        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setString(1,customer);
        prSt.setInt(2, addressId);
        prSt.setString(3, userName);
        prSt.setInt(4, customerId);
        
        prSt.executeUpdate();
        
        DBConnection.closeConnection();
        
    }

    /**
     * 
     * @param customerName
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    @Override
    public Customer selectedCustomer(String customerName) throws SQLException, Exception {
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        Customer customerResult;
        
        String sql = "SELECT customerId, customerName, addressId FROM customer WHERE customerName = ? ";
        
        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setString(1,customerName);
        
         ResultSet result = prSt.executeQuery();
        
        if (result != null) {

            while (result.next()) {
                int customerId = result.getInt("customerId");
                String name = result.getString("customerName");
                int addressId = result.getInt("addressId");
                
                customerResult = new Customer(customerId, name, addressId);
                return customerResult ;
            }
        }
        DBConnection.closeConnection();

        return null;
        
        
    }

    /**
     * 
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    @Override
    public ObservableList<Customer> getAllCustomers() throws SQLException, Exception {
        
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        
        Customer customerResult; 
        
        String sql = "SELECT customerId, customerName, addressId FROM customer";
        
        PreparedStatement prSt = conn.prepareStatement(sql);
       
        
         ResultSet result = prSt.executeQuery();
         
            while (result.next()) {
                int customerId = result.getInt("customerId");
                String customer = result.getString("customerName");
                int addressId = result.getInt("addressId");
               
                // save in the Address object
                customerResult = new Customer(customerId, customer, addressId);
                allCustomers.addAll(customerResult);

            }
        
        DBConnection.closeConnection();

         return allCustomers;
        
        
    }
    
    /**
     * 
     * @param customerId
     * @throws SQLException
     * @throws Exception 
     */
    @Override
    public void deleteCustomer (int customerId)throws SQLException, Exception{
        
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        String sql = "DELETE FROM customer WHERE customerId = ? ";
        
        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setInt(1,customerId);
        
        prSt.executeUpdate();
        
        DBConnection.closeConnection();
        
        
    }
    
    @Override
    public Customer customerName (int customerId)throws SQLException, Exception{
        Customer customerResult; 
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        String sql = "SELECT * FROM customer WHERE customerId = ?";
        
        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setInt(1, customerId);
       
        
         ResultSet result = prSt.executeQuery();
         
            while (result.next()) {
                int customerid = result.getInt("customerId");
                String customer = result.getString("customerName");
                int addressId = result.getInt("addressId");
               
                // save in the Address object
               return customerResult = new Customer(customerid, customer, addressId);
            }
          
        return null; 
    }
}
