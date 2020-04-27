/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Reports;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author Danielle
 */
public interface ReportsDAO {
    
    public ObservableList<Reports> totalyMonth()throws SQLException, Exception;
    
    public ObservableList<Reports> byConsultant()throws SQLException, Exception;
    
    public ObservableList<Reports> byCustomers()throws SQLException, Exception;
   
    public ObservableList<Reports> byChosenMonth(int month)throws SQLException, Exception;
    
    public ObservableList<Reports> byChosenConsultant(String consultant)throws SQLException, Exception;
    
    public ObservableList<Reports> byChosenCustomer(int customerChoosen)throws SQLException, Exception;
    
}
