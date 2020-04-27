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
    private String monthName; 
    private int numberOfAppointment;
    private String type;
    private String consultant;
    private String dateAndTime;
    private int customerId;
    String customerName; 

    public Reports(int month, int apptmNumber, String type, String monthName) {
         this.month = month;
         this.numberOfAppointment = apptmNumber;
         this.type = type; 
         this.monthName = monthName;
    }

    public Reports(String customerName, String start, String contact) {
        this.customerName =customerName;
        this.dateAndTime = start;
        this.consultant = contact;
    }
    
    public Reports(String type, String customerName, String start, int customerId) {
        this.customerName =customerName;
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

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public int getCustomers() {
        return customerId;
    }

    public void setCustomers(int customerId) {
        this.customerId = customerId;
    }
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
     public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
}
