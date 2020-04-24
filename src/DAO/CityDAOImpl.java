/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.City;
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
public class CityDAOImpl implements CityDAO {

    /**
     * 
     * @param cityName
     * @param countryId
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    @Override
    public boolean checkAllCities(String cityName , int countryId) throws SQLException, Exception {
        
        String sql = "SELECT* FROM city WHERE city = ? AND countryId = ? "; // query

        Connection conn = DBConnection.makeConnection(); // making the connection

        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setString(1, cityName);
        prSt.setInt(2, countryId);
        
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
     * @param cityName
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    @Override
    public int getCityId(String cityName, int countryId) throws SQLException, Exception {
        
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        String sql = "SELECT cityId FROM city WHERE city = ? AND countryId = ?";
        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setString(1,cityName);
        prSt.setInt(2, countryId);
        
        ResultSet result = prSt.executeQuery();
        
        if (result != null) {

            while (result.next()) {
                int cityid = result.getInt("cityId");

                return cityid ;
            }
        }
        DBConnection.closeConnection();

        return 0;
    }

    /**
     * 
     * @param city
     * @param userName
     * @param countryId
     * @throws SQLException
     * @throws Exception 
     */
    @Override
    public void newCity(String city, String userName, int countryId) throws SQLException, Exception {
       
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        String sql = "INSERT INTO city (city,countryId, createDate, createdBy,lastUpdate, lastUpdateBy) "+
                      "VALUES(?, ?,CURRENT_DATE(),?,CURRENT_TIMESTAMP,?)";
        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setString(1,city);
        prSt.setInt(2, countryId);
        prSt.setString(3,userName);
        prSt.setString(4,userName);
        
        prSt.executeUpdate();
        
        DBConnection.closeConnection();
    }
    
    /**
     * 
     * @param cityId
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    @Override
    public City selectedCity (int cityId)throws SQLException, Exception{
        Connection conn= DBConnection.makeConnection(); // making the connection
        City cityResult; 
        
        String sql = "SELECT* FROM city WHERE cityId = ?";
        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setInt(1,cityId);
        
        ResultSet result = prSt.executeQuery();
        
        if (result != null) {

            while (result.next()) {
                String cityName = result.getString("city");
                int countryId = result.getInt("countryId");
                
                cityResult = new City (cityName, countryId);
                return cityResult ;
            }
        }
        DBConnection.closeConnection();

        return null;
        
        
    }
}
