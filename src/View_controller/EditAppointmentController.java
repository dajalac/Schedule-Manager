/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_controller;

import Model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Danielle
 */
public class EditAppointmentController implements Initializable {

    @FXML
    private Label customerNameLbl;
    @FXML
    private ComboBox<String> customerNameCbox;
    @FXML
    private Label serviceLbl;
    @FXML
    private ComboBox<String> serviceCbox;
    @FXML
    private Label consultantLbl;
    @FXML
    private ComboBox<String> consultantCbox;
    @FXML
    private Label typeLbl;
    @FXML
    private ComboBox<String> typeCbox;
    @FXML
    private ComboBox<String> locationCbox;
    @FXML
    private Label timeLbl;
    @FXML
    private ComboBox<String> timeCbox;
    @FXML
    private Label dateLbl;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label noteLbl;
    @FXML
    private TextArea descriptionTxtArea;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Label apptLabl;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cancelBtnAction(ActionEvent event) throws IOException {
        
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       alert.setTitle("Confirmation Dialog");
       alert.setHeaderText(null);
       
       alert.setContentText("Are you sure do you want to cancel this operation?");

        Optional<ButtonType> result = alert.showAndWait();
         if (result.get() == ButtonType.OK)
            goToMain(event);
        
    }

    @FXML
    private void saveBtnAction(ActionEvent event) {
    }
    
    private void goToMain(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Appointment Scheduler");
        stage.show();  
    }
    
    public void displayAppointment (Appointment appointment){
        customerNameCbox.setValue(appointment.getCustomerName());
        serviceCbox.setValue(appointment.getTitle());
        consultantCbox.setValue(appointment.getContact());
        typeCbox.setValue(appointment.getType());
        locationCbox.setValue(appointment.getLocation());
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Formate the time
        LocalDateTime ldt = LocalDateTime.parse(appointment.getStartTime(),formatter);
        
        LocalTime lt = ldt.toLocalTime();
        
        LocalDate ld = ldt.toLocalDate();
    

        datePicker.setValue(ld);
        timeCbox.setValue(lt.toString());
        descriptionTxtArea.setText(appointment.getDescription());
        
        
        
    }
  
}
