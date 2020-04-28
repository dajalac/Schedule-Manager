/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Appointment;
import Model.Customer;
import Utils.TimeConversion;
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
public class AppointmentDAOImpl implements AppointmentDAO{
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
   /**
    * 
    * @param customerId
    * @param userId
    * @param title
    * @param notes
    * @param location
    * @param contact
    * @param type
    * @param start
    * @param end
    * @param userName
    * @throws SQLException
    * @throws Exception 
    */
    @Override
    public void insertAppointment(int customerId, int userId, String title, String notes,
            String location,String contact, String type, Timestamp start, Timestamp end, String userName) throws SQLException, Exception {
        
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        String sql = "INSERT INTO appointment (customerId, userId, title,description,"
                + " location, contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateby) "
                +"VALUES (?,?,?,?,?,?,?,'www.apptmscheduler.com',?,?,CURRENT_DATE(),?, CURRENT_TIMESTAMP,? )";
        
         PreparedStatement prSt = conn.prepareStatement(sql);
         prSt.setInt(1, customerId);
         prSt.setInt(2, userId);
         prSt.setString(3, title);
         prSt.setString(4, notes);
         prSt.setString(5, location);
         prSt.setString(6,contact);
         prSt.setString(7, type);
         prSt.setTimestamp(8, start);
         prSt.setTimestamp(9, end);
         prSt.setString(10, userName);
         prSt.setString(11, userName);
         
         prSt.executeUpdate();
        
        DBConnection.closeConnection();
    }

    /**
     * 
     * @param appointmentId
     * @throws SQLException
     * @throws Exception 
     */
    @Override
    public void deleteAppointment(int appointmentId) throws SQLException, Exception {
        
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        String sql = "DELETE FROM appointment WHERE appointmentId = ?";
         PreparedStatement prSt = conn.prepareStatement(sql);
         prSt.setInt(1,appointmentId );
         
         prSt.executeUpdate();
        
        DBConnection.closeConnection();
                 
    }

    /**
     * 
     * @param appointmentId
     * @param customerId
     * @param userId
     * @param title
     * @param notes
     * @param location
     * @param contact
     * @param type
     * @param start
     * @param end
     * @param userName
     * @throws SQLException
     * @throws Exception 
     */
    @Override
    public void updateAppointment (int appointmentId, int customerId, int userId, String title, String notes,
            String location,String contact, String type, Timestamp start, Timestamp end, String userName)throws SQLException, Exception{
        
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        String sql = "UPDATE appointment SET " 
                +  " customerId = ?,"
                + " userId = ?,"
                + " title = ?,"
                + " description = ?,"
                + " location = ?, "
                + "contact = ?, "
                + "type = ?, "
                + "start = ?, "
                + "end = ?,"
                + " lastUpdate = CURRENT_TIMESTAMP,"
                + " lastUpdateby = ? "
                + "WHERE appointmentId = ?";
        
        PreparedStatement prSt = conn.prepareStatement(sql);
         prSt.setInt(1, customerId);
         prSt.setInt(2, userId);
         prSt.setString(3, title);
         prSt.setString(4, notes);
         prSt.setString(5, location);
         prSt.setString(6,contact);
         prSt.setString(7, type);
         prSt.setTimestamp(8, start);
         prSt.setTimestamp(9, end);
         prSt.setString(10, userName);
         prSt.setInt(11, appointmentId);
         
         prSt.executeUpdate();
         
         DBConnection.closeConnection();
        

        
    }

    /**
     * 
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    @Override
    public ObservableList<Appointment> getAllAppointments() throws SQLException, Exception {
        ObservableList<Appointment> allApptmt =  FXCollections.observableArrayList();
        Appointment appointmentResult;
        Customer customer;
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        String sql = "SELECT* FROM appointment" ;
        
        PreparedStatement prSt = conn.prepareStatement(sql);
        
        ResultSet result = prSt.executeQuery();
        
        

            while (result.next()) {
                int apptmtId = result.getInt("appointmentId");
                int customerid =result.getInt("customerId");
                int userId = result.getInt("userId");
                String title = result.getString("title");
                String description = result.getString("description");
                String location = result.getString("location");
                String contact = result.getString("contact");
                String type = result.getString("type");
                Timestamp start= result.getTimestamp("start");
                Timestamp end = result.getTimestamp("end");
                String createdby = result.getString("createdBy");
                
                String startTime = TimeConversion.utcToLocalTime(start);
                String endTime = TimeConversion.utcToLocalTime(end);
                
                // get user name
                customer = customerDAO.customerName(customerid);
                String customerName = customer.getCustomerName();
                appointmentResult = new Appointment(apptmtId,customerid,userId,title,description,
                                    location,contact, type, startTime, endTime, createdby,customerName );
                allApptmt.addAll(appointmentResult);
                
                
            }
        
        DBConnection.closeConnection();

        return allApptmt;
    }

    /**
     * 
     * @param toDate
     * @param fromDate
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    @Override
    public ObservableList<Appointment> selectedDatesAndTime (Timestamp fromDate, Timestamp toDate) throws SQLException, Exception {
        ObservableList<Appointment> allApptmt =  FXCollections.observableArrayList();
        Appointment appointmentResult;
        Customer customer;
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        String sql = "SELECT* FROM appointment WHERE start BETWEEN ? AND ? " ;
        
        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setTimestamp(1, fromDate);
        prSt.setTimestamp(2, toDate);
       
        ResultSet result = prSt.executeQuery();
        
            while (result.next()) {
                int apptmtId = result.getInt("appointmentId");
                int customerid =result.getInt("customerId");
                int userId = result.getInt("userId");
                String title = result.getString("title");
                String description = result.getString("description");
                String location = result.getString("location");
                String contact = result.getString("contact");
                String type = result.getString("type");
                Timestamp start= result.getTimestamp("start");
                Timestamp end = result.getTimestamp("end");
                String createdby = result.getString("createdBy");
               
                // convert time
                String startTime = TimeConversion.utcToLocalTime(start);
                String endTime = TimeConversion.utcToLocalTime(end);
                
                // get the customer
                customer = customerDAO.customerName(customerid);
                String customerName = customer.getCustomerName();
                
                appointmentResult = new Appointment(apptmtId,customerid,userId,title,description,
                                    location,contact, type, startTime, endTime, createdby,customerName );
                
                allApptmt.addAll(appointmentResult);

        }
          
        DBConnection.closeConnection();

        return allApptmt;
    }

    /**
     * 
     * @param selectedDateTime
     * @param service
     * @param contact
     * @param location
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    @Override
    public boolean checkOverloadAppt(Timestamp selectedDateTime,String contact) throws SQLException, Exception {
        
        Connection conn= DBConnection.makeConnection(); // making the connection
        
        String sql = "SELECT* FROM appointment WHERE start = ?"+
                     "AND contact = ?";
        
        PreparedStatement prSt = conn.prepareStatement(sql);
        prSt.setTimestamp(1, selectedDateTime);
        prSt.setString(2,contact);
        
        ResultSet result = prSt.executeQuery();
        
        if (result.next()){ 
            DBConnection.closeConnection(); 
            return true;
        }
        else{
         DBConnection.closeConnection();
         return false;  
        }
    }

    
}
