/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Country;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author Danielle
 */
public interface CountryDAO {
    
    public void insertCountry(String country, String userName)throws SQLException, Exception;
    
    public int getCountryId (String countryName)throws SQLException, Exception;
    
    public boolean getAllCountries (String countryName)throws SQLException, Exception;
    
    public String getCountryName (int countryId)throws SQLException, Exception;
   // public boolean checkIfCountryExist(String countryName)throws SQLException, Exception;
}
