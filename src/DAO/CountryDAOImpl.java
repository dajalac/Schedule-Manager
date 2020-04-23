/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Country;
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
         
       // ObservableList<Country> allCountries = FXCollections.observableArrayList();
       // Country countryResult;
       
        //query
        String sql = "SELECT* FROM country WHERE country = ? ";

        Connection conn = DBConnection.makeConnection(); // making the connection

        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setString(1, countryName);
        ResultSet result = prSt.executeQuery();

        if (result.next()) {
            /**
            while (result.next()) {
                int countryid = result.getInt("countryId");
                String country = result.getString("country");

                // save in the country object
                countryResult = new Country(countryid, country);
                allCountries.add(countryResult);

                return allCountries;
                }
                */
            DBConnection.closeConnection();
           
            return true;
            
        }
        else{
           DBConnection.closeConnection();
           
           return false;
        }
    }
}
