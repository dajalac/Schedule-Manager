/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Address;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author Danielle
 */
public class AddressDAOImpl  implements AddressDAO {

    /**
     * 
     * @param address
     * @param address2
     * @param cityId
     * @param phone
     * @param postalCode
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    @Override
    public boolean getAllAddress(String address, String address2, int cityId, String postalCode, String phone) throws SQLException, Exception {
       
        String sql = "SELECT* FROM address WHERE address = ?"
                     + "AND address2 = ? "
                    + "AND cityId = ? "
                    + "AND (postalCode = ? "
                    + "AND phone = ? )"; 

        Connection conn = DBConnection.makeConnection(); // making the connection

        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setString(1, address);
        prSt.setString(2, address2);
        prSt.setInt(3, cityId);
        prSt.setString(4, postalCode);
        prSt.setString(5, phone);
 
        ResultSet result = prSt.executeQuery();

        if (result.next()) {
            DBConnection.closeConnection();
            System.out.println("it is true");
            return true;
        } else {
            DBConnection.closeConnection();
            System.out.println ("it is false");
            return false;
        }
    
    }
    /**
     * 
     * @param address
     * @param address2
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    @Override
    public int getAdressId(String address, String address2, int cityId, String postalCode, String phone) throws SQLException, Exception {
         
        String sql = "SELECT* FROM address WHERE address = ?"
                     + "AND address2 = ? "
                    + "AND cityId = ? "
                    + "AND (postalCode = ? "
                    + "AND phone = ? )"; // query

        Connection conn = DBConnection.makeConnection(); // making the connection

        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setString(1, address);
        prSt.setString(2, address2);
        prSt.setInt(3, cityId);
        prSt.setString(4, postalCode);
        prSt.setString(5, phone);
        
        ResultSet result = prSt.executeQuery();

            while (result.next()) {
                int addressid = result.getInt("addressId");

                return addressid ;
            }
        
        DBConnection.closeConnection();

        return 0;
    }

    /**
     * 
     * @param address1
     * @param address2
     * @param cityId
     * @param postalCode
     * @param phone
     * @param userName
     * @throws SQLException
     * @throws Exception 
     */
    @Override
    public void insertAddress(String address1, String address2, int cityId, 
            String postalCode, String phone, String userName ) throws SQLException, Exception {
        
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        String sql = "INSERT INTO address (address,address2, cityId, postalCode, phone, createDate, createdBy,lastUpdate, lastUpdateBy) "+
                      "VALUES(?, ?,?,?,?, CURRENT_DATE() ,?, CURRENT_TIMESTAMP,?)";
        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setString(1,address1);
        prSt.setString(2,address2);
        prSt.setInt(3, cityId);
        prSt.setString(4,postalCode);
        prSt.setString(5,phone);
        prSt.setString(6,userName);
        prSt.setString(7,userName);
        
        prSt.executeUpdate();
        
        DBConnection.closeConnection();
    
    }
/**
 * 
 * @param addressId
 * @return
 * @throws SQLException
 * @throws Exception 
 */
    @Override
    
    public Address selectedAddress(int addressId) throws SQLException, Exception{
        Connection conn= DBConnection.makeConnection(); // making the connection
        Address addressResult; 
        String sql = "SELECT * FROM address WHERE addressId = ? ";
        PreparedStatement prSt = conn.prepareStatement(sql);
        
        prSt.setInt(1,addressId);
        ResultSet result = prSt.executeQuery();
        
        if (result.next()) {
            int addressid = result.getInt("addressId");
            String address = result.getString("address");
            String address2 = result.getString("address2");
            int cityId = result.getInt("cityId");
            String postalCode = result.getString("postalCode");
            String phone = result.getString("phone");
            
            return addressResult = new Address(addressid, address, address2, cityId, postalCode, phone);
        }
        
        return null; 
               
    }
    
    
}
