/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Timestamp;

/**
 *
 * @author Danielle
 */
public class Reports {
    
    private int month;
    private int numberOfAppointment;
    private String type;
    private String consultant;
    private Timestamp dateAndTime;
    private int customerId; 

    public Reports(int month, int apptmNumber, String type) {
         this.month = month;
         this.numberOfAppointment = apptmNumber;
         this.type = type; 
    }

    public Reports(int customerId, Timestamp start, String contact) {
        this.customerId = customerId;
        this.dateAndTime = start;
        this.consultant = contact; 
    }
    
    public Reports(String type, int customerId, Timestamp start) {
        this.customerId = customerId;
        this.dateAndTime = start;
        this.type = type; 
    }
    

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getNumberOfAppointment() {
        return numberOfAppointment;
    }

    public void setNumberOfAppointment(int numberOfAppointment) {
        this.numberOfAppointment = numberOfAppointment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConsultant() {
        return consultant;
    }

    public void setConsultant(String consultant) {
        this.consultant = consultant;
    }

    public Timestamp getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Timestamp dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public int getCustomers() {
        return customerId;
    }

    public void setCustomers(int customerId) {
        this.customerId = customerId;
    }
    
    
}
