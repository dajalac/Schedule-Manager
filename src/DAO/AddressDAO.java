/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Address;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author Danielle
 */
public interface AddressDAO {
    
    public boolean getAllAddress(String address, String address2,int cityId,String postalCode, String phone) throws SQLException, Exception;
    
    public int getAdressId(String address, String address2,int cityId,String postalCode, String phone) throws SQLException, Exception;
    
    public void insertAddress (String addres1, String address2, int CityId, 
            String postalCode, String phone, String userName )  throws SQLException, Exception;

    public Address selectedAddress(int addressId) throws SQLException, Exception;
}
