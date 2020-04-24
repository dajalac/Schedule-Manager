/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.City;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author Danielle
 */
public interface CityDAO {
    
    public boolean checkAllCities (String cityName,int countryId )throws SQLException, Exception;
    
    public int getCityId(String cityName, int countryId)throws SQLException, Exception;
    
    public void newCity (String city,String userName, int countryId )throws SQLException, Exception;
    
    public City selectedCity (int cityId)throws SQLException, Exception;
    
}
