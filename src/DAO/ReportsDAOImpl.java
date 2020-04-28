/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Customer;
import Model.Reports;
import Utils.TimeConversion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
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
        
        String sql = "SELECT type, count(distinct appointmentId) AS 'number', EXTRACT(MONTH FROM start) as 'month'"+
                      " FROM appointment GROUP BY  EXTRACT(MONTH FROM start), type";
        
        PreparedStatement prSt = conn.prepareStatement(sql);
        
        ResultSet result = prSt.executeQuery();
        
        

            while (result.next()) {
                int month = result.getInt("month");
                int apptmNumber = result.getInt("number");
                String type = result.getString("type");
                
                String monthName = new DateFormatSymbols().getMonths()[month-1];
                
                reportsResult = new Reports (month, apptmNumber, type, monthName);
                allReports.addAll(reportsResult);

            }
       
        DBConnection.closeConnection();

        return allReports;
    
    }

    @Override
    public ObservableList<Reports> byConsultant() throws SQLException, Exception {
        ObservableList<Reports> allReports =  FXCollections.observableArrayList();
        Reports reportsResult;
        Customer customer;
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
        
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        String sql = "SELECT customerId, start , contact " +
                     " FROM appointment " +
                     "GROUP BY contact, customerId";
        
        PreparedStatement prSt = conn.prepareStatement(sql);
        
        ResultSet result = prSt.executeQuery();
       

            while (result.next()) {
                int customerId = result.getInt("customerId");
                Timestamp start = result.getTimestamp("start");
                String contact = result.getString("contact");
                
                String startTime = TimeConversion.utcToLocalTime(start);
                // get user name
                customer = customerDAO.customerName(customerId);
                String customerName = customer.getCustomerName();
                
                reportsResult = new Reports (customerName, startTime, contact);
                allReports.addAll(reportsResult);
            }
        
        DBConnection.closeConnection();

        return allReports;
    }

    @Override
    public ObservableList<Reports> byCustomers() throws SQLException, Exception {
        
        ObservableList<Reports> allReports =  FXCollections.observableArrayList();
        Reports reportsResult;
        Customer customer;
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
        
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        String sql = "SELECT customerId, start , type " + 
                     "FROM appointment "+ 
                     "GROUP BY customerId, contact " ;
        
        PreparedStatement prSt = conn.prepareStatement(sql);
        
        ResultSet result = prSt.executeQuery();
        

            while (result.next()) {
                int customerId = result.getInt("customerId");
                Timestamp start = result.getTimestamp("start");
                String type = result.getString("type");
                String startTime = TimeConversion.utcToLocalTime(start);
                
                // get user name
                customer = customerDAO.customerName(customerId);
                String customerName = customer.getCustomerName();
                
                reportsResult = new Reports (type, customerName, startTime, customerId);
                allReports.addAll(reportsResult);
                

            }
        
        DBConnection.closeConnection();

        return allReports;
        
    }
    @Override
    public ObservableList<Reports> byChosenMonth(int monthChosen)throws SQLException, Exception{
        
        ObservableList<Reports> allReports =  FXCollections.observableArrayList();
        Reports reportsResult;
        
        Connection conn= DBConnection.makeConnection(); // making the connection
        String sql ="SELECT type, count(distinct appointmentId) AS 'number', EXTRACT(MONTH FROM start) as 'month'\n" +
"                      FROM appointment WHERE EXTRACT(MONTH FROM start) = ?"
                + "  GROUP BY  EXTRACT(MONTH FROM start), type " ;
        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setInt(1, monthChosen);
        
        ResultSet result = prSt.executeQuery();

            while (result.next()) {
                int month = result.getInt("month");
                int apptmNumber = result.getInt("number");
                String type = result.getString("type");
                //Locale.setDefault(Locale.US);
                String monthName = new DateFormatSymbols().getMonths()[month-1];
                
                reportsResult = new Reports (month, apptmNumber, type, monthName);
                allReports.addAll(reportsResult);

            }
       
        DBConnection.closeConnection();

        return allReports;
        
    }
    @Override
    public ObservableList<Reports> byChosenConsultant(String consultant)throws SQLException, Exception{
        ObservableList<Reports> allReports =  FXCollections.observableArrayList();
        Reports reportsResult;
        Customer customer;
        
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
        
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        String sql = "SELECT customerId, start , contact " +
                     " FROM appointment " +
                     "WHERE contact = ? " +
                     "GROUP BY contact, customerId";
        
        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setString(1,consultant);
        
        ResultSet result = prSt.executeQuery();
       

            while (result.next()) {
                int customerId = result.getInt("customerId");
                Timestamp start = result.getTimestamp("start");
                String contact = result.getString("contact");
                
                String startTime = TimeConversion.utcToLocalTime(start);
                // get user name
                customer = customerDAO.customerName(customerId);
                String customerName = customer.getCustomerName();
                
                reportsResult = new Reports (customerName, startTime, contact);
                allReports.addAll(reportsResult);
            }
        
        DBConnection.closeConnection();

        return allReports;
    }
    @Override
    public ObservableList<Reports> byChosenCustomer(int customerChoosen)throws SQLException, Exception{
        
        ObservableList<Reports> allReports =  FXCollections.observableArrayList();
        Reports reportsResult;
        Customer customer;
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
        
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        String sql = "SELECT customerId, start , type " + 
                     "FROM appointment "+
                     "WHERE customerId = ? "+
                     "GROUP BY customerId, contact " ;
        
        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setInt(1, customerChoosen);
        
        ResultSet result = prSt.executeQuery();
        

            while (result.next()) {
                int customerId = result.getInt("customerId");
                Timestamp start = result.getTimestamp("start");
                String type = result.getString("type");
                String startTime = TimeConversion.utcToLocalTime(start);
                
                // get user name
                customer = customerDAO.customerName(customerId);
                String customerName = customer.getCustomerName();
                
                reportsResult = new Reports (type, customerName, startTime, customerId);
                allReports.addAll(reportsResult);
                

            }
        
        DBConnection.closeConnection();

        return allReports;
              
    }
    
}
