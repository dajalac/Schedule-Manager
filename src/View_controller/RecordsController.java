/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_controller;

import DAO.AppointmentDAOImpl;
import DAO.CustomerDAOImpl;
import DAO.ReportsDAOImpl;
import Model.Appointment;
import Model.Customer;
import Model.Reports;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Danielle
 */
public class RecordsController implements Initializable {

    @FXML
    private TableView<Reports> reportTableView;
    @FXML
    private TableColumn<?, ?> column1;
    @FXML
    private TableColumn<?, ?> column2;
    @FXML
    private TableColumn<?, ?> column3;
    @FXML
    private Button okBtn;
    @FXML
    private Label reportLabel;
    @FXML
    private ComboBox<String> reportCbox;
    
    ReportsDAOImpl reportsDAO = new ReportsDAOImpl();
    AppointmentDAOImpl appointmentDAO = new AppointmentDAOImpl();
    CustomerDAOImpl customerDAO = new CustomerDAOImpl();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }    

    @FXML
    private void okBtnAction(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Appointment Scheduler");
        stage.show();  
    }
    
    public void getReportsList( ObservableList<Reports> reportsList, String selectedRbutton ) throws Exception{
        
        switch(selectedRbutton ){
            case "month":
                displayReportsByMonth(reportsList);               
                break;
            case "consultant":
                displayReportsByConsultant(reportsList);            
                break;
            case "customer":
                displayReportsByCustomers(reportsList);            
                break; 
        }
    }
    
    private void displayReportsByMonth(ObservableList<Reports> reportsList){
        
        column1.setText("Month");
        column2.setText("Type");
        column3.setText("Number of appointments");
        
        column1.setCellValueFactory(new PropertyValueFactory<>("monthName"));
        column2.setCellValueFactory(new PropertyValueFactory<>("type"));
        column3.setCellValueFactory(new PropertyValueFactory<>("numberOfAppointment"));
        reportTableView.setItems(reportsList);
        
         reportCbox.getItems().addAll("January", "February", "March", "April", "May",
                "June", "July", "August", "September", "October", "November", "December");
        reportCbox.setValue("All");
        
        //get comboxAction
        reportCbox.setOnAction(e -> {
            try {   
                selectedMonth(reportCbox.getSelectionModel().getSelectedIndex()+1 );
            } catch (Exception ex) {
                Logger.getLogger(RecordsController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        });
   
    }
    
    private void displayReportsByConsultant(ObservableList<Reports> reportsList) throws Exception{

        column1.setText("Consultant");
        column2.setText("Customer");
        column3.setText("Date and time");
        column1.setCellValueFactory(new PropertyValueFactory<>("consultant"));
        column2.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        column3.setCellValueFactory(new PropertyValueFactory<>("dateAndTime"));
        reportTableView.setItems(reportsList);
  
         
        reportCbox.getItems().addAll("Dr. Miranda Balley","Dr. Meredith Grey",
                "Rick Johson","Dr. Alex Karev","Vic Montana",
                "Zoe Namaste","Simon Park", "Valeria Sales","Katy White");
        
        reportCbox.setValue("All");
        
        //get comboxAction
        reportCbox.setOnAction(e -> {
            try {   
                selectedConsultant(reportCbox.getValue());
            } catch (Exception ex) {
                Logger.getLogger(RecordsController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        });
    }
    private void displayReportsByCustomers(ObservableList<Reports> reportsList) throws Exception{
        column1.setText("Custumer");
        column2.setText("Type");
        column3.setText("Date and time");
        column1.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        column2.setCellValueFactory(new PropertyValueFactory<>("type"));
        column3.setCellValueFactory(new PropertyValueFactory<>("dateAndTime"));
        reportTableView.setItems(reportsList);
        
        for(Customer c : customerDAO.getAllCustomers()){
            reportCbox.getItems().addAll(c.getCustomerName());
        }
    
        reportCbox.setValue("All"); 
        
        //get comboxAction
        reportCbox.setOnAction(e -> {
            try {   
                selectedCustomer(reportCbox.getValue());
            } catch (Exception ex) {
                Logger.getLogger(RecordsController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        });
        
    }
    
    private void selectedMonth(int month) throws Exception{
 
        column1.setText("Month");
        column2.setText("Type");
        column3.setText("Number of appointments");
        
        column1.setCellValueFactory(new PropertyValueFactory<>("monthName"));
        column2.setCellValueFactory(new PropertyValueFactory<>("type"));
        column3.setCellValueFactory(new PropertyValueFactory<>("numberOfAppointment"));
        
        if (reportsDAO.byChosenMonth(month).isEmpty()) {
            reportTableView.getItems().clear();
            reportTableView.setPlaceholder(new Label("There is no appointment for the chosen month"));
        } else {
            reportTableView.setItems(reportsDAO.byChosenMonth(month));
            
        }
    }
    
    private void selectedConsultant (String consultant) throws Exception{
        
        column1.setText("Consultant");
        column2.setText("Customer");
        column3.setText("Date and time");
        column1.setCellValueFactory(new PropertyValueFactory<>("consultant"));
        column2.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        column3.setCellValueFactory(new PropertyValueFactory<>("dateAndTime"));
        
        if (reportsDAO.byChosenConsultant(consultant).isEmpty()) {
            reportTableView.getItems().clear();
            reportTableView.setPlaceholder(new Label("There is no appointment for the chosen month"));
        } else {
            reportTableView.setItems(reportsDAO.byChosenConsultant(consultant));
        }
    }
    
    private void selectedCustomer (String customer) throws Exception{
        Customer c = customerDAO.selectedCustomer(customer);
        int customerId = c.getCustomerId();
        
        column1.setText("Custumer");
        column2.setText("Type");
        column3.setText("Date and time");
        column1.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        column2.setCellValueFactory(new PropertyValueFactory<>("type"));
        column3.setCellValueFactory(new PropertyValueFactory<>("dateAndTime"));
        
        if (reportsDAO.byChosenCustomer(customerId).isEmpty()) {
            reportTableView.getItems().clear();
            reportTableView.setPlaceholder(new Label("There is no appointment for the chosen customer"));
        } else {
            reportTableView.setItems(reportsDAO.byChosenCustomer(customerId));
        }
        
        
        
    }
}
