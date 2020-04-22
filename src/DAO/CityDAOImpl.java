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
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    @Override
    public ObservableList<City> getAllCities() throws SQLException, Exception {
        ObservableList<City> allCities = FXCollections.observableArrayList();
        City cityResult;
        
        
        String sql = "SELECT* FROM city"; // query

        Connection conn = DBConnection.makeConnection(); // making the connection

        PreparedStatement prSt = conn.prepareStatement(sql);
        ResultSet result = prSt.executeQuery();

        if (result != null) {

            while (result.next()) {
                int cityid = result.getInt("cityId");
                String city = result.getString("city");

                // save in the city object
                cityResult = new City(cityid, city);
                allCities.add(cityResult);

                return allCities;
            }
        }
        DBConnection.closeConnection();

        return null;
    
        
    }

    /**
     * 
     * @param cityName
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    @Override
    public int getCityId(String cityName) throws SQLException, Exception {
        
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        String sql = "SELECT cityId FROM city WHERE city = ?";
        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setString(1,cityName);
        
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
                      "VALUES(?, ?,'CURRENT_DATE',?,'CURRENT_TIMESTAMP',?)";
        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setString(1,city);
        prSt.setInt(2, countryId);
        prSt.setString(3,userName);
        prSt.setString(4,userName);
        
        prSt.executeUpdate();
        
        DBConnection.closeConnection();
    }
    
}
