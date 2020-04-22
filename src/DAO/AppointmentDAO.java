/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Appointment;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.collections.ObservableList;

/**
 *
 * @author Danielle
 */
public interface AppointmentDAO {
    
    public void insertAppointment ()throws SQLException, Exception; 
    
    public void deleteAppointment(int appointmentId) throws SQLException, Exception;
    
    public Appointment selectedAppointment (String type,int customerId, Timestamp dateTime )throws SQLException, Exception;
    
    public ObservableList<Appointment> getAllAppointments ()throws SQLException, Exception;
    
    public ObservableList<Appointment> selectedDates (LocalDate toDate, LocalDate fromDate)throws SQLException, Exception;
    
    public boolean checkOverloadAppt (LocalDateTime selectedDateTime)throws SQLException, Exception;
    // need one to 15 min 
    
    
    
}
