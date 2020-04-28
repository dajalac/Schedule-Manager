/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Danielle
 */
public class CountryDAOImpl implements CountryDAO {

    /**
     * 
     * @param country
     * @param userName
     * @throws SQLException
     * @throws Exception 
     */
    @Override
    public void insertCountry(String country, String userName)throws SQLException, Exception {
        
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        String sql = "INSERT INTO country (country,createDate, createdBy,lastUpdate, lastUpdateBy) "+
                      "VALUES(?, CURRENT_DATE(),?,CURRENT_TIMESTAMP,?)";
        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setString(1,country);
        prSt.setString(2,userName);
        prSt.setString(3,userName);
        
        prSt.executeUpdate();
        
        DBConnection.closeConnection();
        
        
    }
/**
 * 
 * @param countryName
 * @return
 * @throws SQLException
 * @throws Exception 
 */
    @Override
    public int getCountryId(String countryName)throws SQLException, Exception {
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        String sql = "SELECT countryId FROM country WHERE country = ?";
        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setString(1,countryName);
        
        ResultSet result = prSt.executeQuery();
        
        if (result != null) {

            while (result.next()) {
                int countryid = result.getInt("countryId");

                return countryid ;
            }
        }
        DBConnection.closeConnection();

        return 0;
        
    
       
    }
/**
 * 
 * @return
 * @throws SQLException
 * @throws Exception 
 */
    @Override
    public boolean getAllCountries(String countryName) throws SQLException, Exception{
       
       
        //query
        String sql = "SELECT* FROM country WHERE country = ? ";

        Connection conn = DBConnection.makeConnection(); // making the connection

        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setString(1, countryName);
        ResultSet result = prSt.executeQuery();

        if (result.next()) {
           
            DBConnection.closeConnection();
           
            return true;
            
        }
        else{
           DBConnection.closeConnection();
           
           return false;
        }
    }
    
    /**
     * 
     * @param country
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    @Override
    public String getCountryName (int countryId)throws SQLException, Exception{
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        String sql = "SELECT country FROM country WHERE countryId = ?";
        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setInt(1,countryId);
        
        ResultSet result = prSt.executeQuery();
        
        if (result != null) {

            while (result.next()) {
                String country = result.getString("country");

                return country ;
            }
        }
        DBConnection.closeConnection();

        return null ;
    }
}
