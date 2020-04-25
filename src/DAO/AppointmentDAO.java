/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Appointment;
import java.sql.SQLException;
import java.sql.Timestamp;
import javafx.collections.ObservableList;

/**
 *
 * @author Danielle
 */
public interface AppointmentDAO {
    
    public void insertAppointment (int customerId, int userId, String title, String notes,
            String location,String contact, String type, Timestamp start, Timestamp end, String userName)throws SQLException, Exception; 
    
    public void deleteAppointment(int appointmentId) throws SQLException, Exception;
    
    public Appointment selectedAppointment (int appointmentId)throws SQLException, Exception;
    
    public ObservableList<Appointment> getAllAppointments ()throws SQLException, Exception;
    
    public ObservableList<Appointment> selectedDatesAndTime (Timestamp toDate, Timestamp fromDate)throws SQLException, Exception;
    
    public boolean checkOverloadAppt (Timestamp selectedDateTime)throws SQLException, Exception;
    
    // public ObservableList<Appointment> getAptm15mim (Timestamp nowTime, Timestamp fromTime)throws SQLException, Exception;
    
    
    
}
