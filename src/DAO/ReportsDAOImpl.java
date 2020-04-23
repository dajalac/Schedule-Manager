/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Reports;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Danielle
 */
public class ReportsDAOImpl implements ReportsDAO{

    @Override
    public ObservableList<Reports> totalyMonth() throws SQLException, Exception {
        ObservableList<Reports> allReports =  FXCollections.observableArrayList();
        Reports reportsResult;
        
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        String sql = "SELECT type, count(distinct appointmentId) AS 'number of appointment', EXTRACT(MONTH FROM start) as 'month'"+
                      " FROM appointment GROUP BY  EXTRACT(MONTH FROM start)";
        
        PreparedStatement prSt = conn.prepareStatement(sql);
        
        ResultSet result = prSt.executeQuery();
        
        if (result != null) {

            while (result.next()) {
                int month = result.getInt("month");
                int apptmNumber = result.getInt("number of appointment");
                String type = result.getString("type");
                
                reportsResult = new Reports (month, apptmNumber, type);
                allReports.add(reportsResult);
                
                return allReports;
            }
        }
        DBConnection.closeConnection();

        return null;
    
    }

    @Override
    public ObservableList<Reports> byConsultant() throws SQLException, Exception {
        ObservableList<Reports> allReports =  FXCollections.observableArrayList();
        Reports reportsResult;
        
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        String sql = "SELECT customerId, start , contact " +
                     " FROM appointment " +
                     "GROUP BY contact";
        
        PreparedStatement prSt = conn.prepareStatement(sql);
        
        ResultSet result = prSt.executeQuery();
        
        if (result != null) {

            while (result.next()) {
                int customerId = result.getInt("customerId");
                Timestamp start = result.getTimestamp("start");
                String contact = result.getString("contact");
                
                reportsResult = new Reports (customerId, start, contact);
                allReports.add(reportsResult);
                
                return allReports;
            }
        }
        DBConnection.closeConnection();

        return null;
    }

    @Override
    public ObservableList<Reports> byCustomers() throws SQLException, Exception {
        
             ObservableList<Reports> allReports =  FXCollections.observableArrayList();
        Reports reportsResult;
        
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        String sql = "SELECT customerId, start , type " + 
                     "FROM appointment "+ 
                     "GROUP BY customerId " ;
        
        PreparedStatement prSt = conn.prepareStatement(sql);
        
        ResultSet result = prSt.executeQuery();
        
        if (result != null) {

            while (result.next()) {
                int customerId = result.getInt("customerId");
                Timestamp start = result.getTimestamp("start");
                String type = result.getString("type");
                
                reportsResult = new Reports (type, customerId, start);
                allReports.add(reportsResult);
                
                return allReports;
            }
        }
        DBConnection.closeConnection();

        return null;
        
    }
    
}
