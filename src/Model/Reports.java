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
    
    private String month;
    private int numberOfAppointment;
    private String type;
    private String consultant;
    private Timestamp dateAndTime;
    private String customers; 

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
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

    public String getCustomers() {
        return customers;
    }

    public void setCustomers(String customers) {
        this.customers = customers;
    }
    
    
}