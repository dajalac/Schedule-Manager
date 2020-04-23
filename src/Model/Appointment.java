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
public class Appointment {
   
    private int appointmentId;
    private int customerId;
    private int userId; 
    private String title; // service
    private String description;
    private String location;
    private String contact; // consultant
    private String type;
    private Timestamp startTime; 
    private Timestamp endTime;
    private String createdBy; 

    public Appointment(int apptmtId, int customerid, int userId, String title, String description, String location, String contact, String type, Timestamp start, Timestamp end, String createdby) {
         this.appointmentId = apptmtId;
         this.customerId = customerid;
         this.userId = userId;
         this.title = title;
         this.description = description;
         this.location = location;
         this.contact = contact;
         this.type = type;
         this.startTime = start;
         this.endTime = end;
         this.createdBy = createdby; 
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
    
    
}
