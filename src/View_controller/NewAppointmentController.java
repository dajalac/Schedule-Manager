/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_controller;

import DAO.AppointmentDAOImpl;
import DAO.CustomerDAOImpl;
import DAO.UserDAOImpl;
import Model.Appointment;
import Model.Customer;
import Model.User;
import Utils.InputValidation;
import Utils.TimeConversion;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Danielle
 */
public class NewAppointmentController implements Initializable {

    @FXML
    private Label customerNameLbl;
    @FXML
    private ComboBox<Customer> customerNameCbox;
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
    private Label timeLbl;
    @FXML
    private ComboBox<String> timeCbox;
    @FXML
    private ComboBox<String> locationCbox;
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
    @FXML
    private TableView<Customer> customerTableView;
     @FXML
    private TableColumn<?, ?> customerNameColumn;
    @FXML
    private TableColumn<?, ?> customerIDcolumn;
    
    
    CustomerDAOImpl customerDAO = new CustomerDAOImpl();
    AppointmentDAOImpl appointmentDAO = new AppointmentDAOImpl();
    UserDAOImpl userDAO = new UserDAOImpl();
    boolean flag = true;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       //load comboBoxes
        try {
            setComboBoxValues();
            
        } catch (Exception ex) {
            Logger.getLogger(NewAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // service comboBox action
        serviceCbox.setOnAction(e -> {
             String selectedShow = serviceCbox.getValue();
            try {
                String selected = serviceCbox.getValue();
                switch (selected) {
                    case "Health":
                        consultantCbox.getItems().clear();
                        consultantCbox.getItems().addAll("Dr. Meredith Grey", "Dr. Alex Karev", "Dr. Miranda Balley");
                        break;
                    case "Beauty":
                        consultantCbox.getItems().clear();
                        consultantCbox.getItems().addAll("Katy White", "Valeria Sales", "Rick Johson");
                        break;
                    case "Spa":
                        consultantCbox.getItems().clear();
                        consultantCbox.getItems().addAll("Zoe Namaste", "Vic Montana", "Simon Park");
                }
               
            } catch (Exception ex) {
                Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
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
    private void saveBtnAction(ActionEvent event) throws Exception {
        
        

        String title = serviceCbox.getValue();
        String type = typeCbox.getValue();
        String note = descriptionTxtArea.getText();
        String contact = consultantCbox.getValue();
        String hour = Integer.toString(timeCbox.getSelectionModel().getSelectedIndex());
        LocalDate date = null;
        String location = locationCbox.getValue();
        Customer customer = customerTableView.getSelectionModel().getSelectedItem();
        int customerId = 0;
        try{
         customerId = customer.getCustomerId();
         date = datePicker.getValue();
        }
        catch (Exception e){}

        inputValidation(title, type, hour, date, location, customerId, contact);
       
        if(!flag){
        addToDabase(customerId, title, type, hour, date, location, note, contact);

        confirmationAndClean();
        }
              

        

    }
    
    private void goToMain(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Appointment Scheduler");
        stage.show();  
    }
    /**
     * 
     * @throws Exception 
     */
    
    private void  setComboBoxValues() throws Exception{
        
        ObservableList<Customer> allCustomers =FXCollections.observableArrayList();
        allCustomers.addAll(customerDAO.getAllCustomers());
        
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerIDcolumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerTableView.setItems(allCustomers);

        serviceCbox.getItems().addAll("Health", "Beauty", "Spa");
        serviceCbox.setValue("Select");
        consultantCbox.getItems().addAll("Choose one service first");
        consultantCbox.setValue("Select");
        typeCbox.getItems().addAll("New appointment", "Follow up");
        typeCbox.setValue("Select");
        locationCbox.getItems().addAll("Pheonix, Arizona", "New York, New York", "London, England");
        locationCbox.setValue("Select");
        
        timeCbox.getItems().addAll("00:00","01:00","02:00","03:00","04:00","05:00",
                "06:00","07:00","08:00","09:00","10:00","11:00",
                "12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00",
                "21:00","22:00","23:00");
        timeCbox.setValue("00:00");
        

        // to desable past dates   
        LocalDate minDate = LocalDate.now();
        datePicker.setDayCellFactory(d
                -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isBefore(minDate));

            }
        });
       
    }
    /**
     * 
     * @param title
     * @param type
     * @param hour
     * @param date
     * @param location
     * @throws Exception 
     */
    private void inputValidation(String title,String type,
            String hour,LocalDate date,String location, int customerId,String contact) throws Exception{
       
        StringBuilder msg = new StringBuilder();
        
        //Lambda expresion
        //It provide code reuse,I don't need to write the comparasion expression more than once. I also didn't have to create a new method just for it. Which increase the code readability  
        InputValidation iV = (input) -> {
            if (input.equals("Select")) {
                return true;
            } else {
                return false;
            }
        };
        
        if(customerId ==0)
            msg.append("Select one customer \n");
     
        if(iV.inputValidation(title))
            msg.append("Select one Service \n");
        if(iV.inputValidation(type))
            msg.append("Select one type of appointment \n");
        if(iV.inputValidation(location))
            msg.append("Select one location \n");
        
        if (date == null) {
            msg.append("Select one date");
            
        } else {
            LocalDateTime apptmntLdt = TimeConversion.dateTimeCombine(date, hour, "00");
            Timestamp apptmentTmz = TimeConversion.utcToStore(apptmntLdt);
            String slot = TimeConversion.utcToLocalTime(apptmentTmz);

            if (appointmentDAO.checkOverloadAppt(apptmentTmz, title, contact, location)) {
                msg.append("Our partner " + contact + "is not available during " + slot + " Try another date or time \n");
            }

            LocalDate ld = apptmntLdt.toLocalDate();
            if (ld.getDayOfWeek() == DayOfWeek.SATURDAY || ld.getDayOfWeek() == DayOfWeek.SUNDAY) {
                msg.append("Our facilities operate just through Monday to Friday. Select a week day");
            }

            if (Integer.parseInt(hour) < 8 || Integer.parseInt(hour) > 17) {
                msg.append("Due our new police we are operating just over business time (8:00 to 17:00)");
            }
        }
        
        if(msg.length()!=0){
        errorMessage(msg);
        flag = true;}
        else{flag= false;} 
    }
    /**
     * 
     * @param customerId
     * @param title
     * @param type
     * @param hour
     * @param date
     * @param location
     * @param note
     * @param contact
     * @throws Exception 
     */
    private void addToDabase(int customerId,String title,String type,String hour,
            LocalDate date,String location,String note,String contact) throws Exception{
        
        LocalDateTime startLdt = TimeConversion.dateTimeCombine(date, hour, "00");
        Timestamp  startTmz = TimeConversion.utcToStore(startLdt);
        
        LocalDateTime endLdt = TimeConversion.dateTimeCombine(date, hour, "55");
        Timestamp  endTmz = TimeConversion.utcToStore(endLdt);
        
        User user = userDAO.getUserNameId(1);
        String userName = user.getUserName();
        int userId = user.getUserId();
        
 
        appointmentDAO.insertAppointment(customerId,userId, title, note, location, 
                contact,type, startTmz, endTmz, userName);
    }
    
    private void errorMessage (StringBuilder msg){
        Alert a = new Alert(Alert.AlertType.WARNING);
		a.setContentText(msg.toString());
		a.setHeaderText(null);
               
                Optional<ButtonType> result = a.showAndWait();
                if (result.get() == ButtonType.OK){
                    a.close(); 
                    msg.setLength(0);
                }
 
    }
     
    private void confirmationAndClean(){
        
       
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Appointment scheduled");
            a.setHeaderText(null);

            Optional<ButtonType> result = a.showAndWait();
            if (result.get() == ButtonType.OK) {
                a.close();

                serviceCbox.setValue("Select");
                typeCbox.setValue("Select");
                descriptionTxtArea.clear();
                consultantCbox.setValue("Select");
                datePicker.getEditor().clear();
                locationCbox.setValue("Select");
                timeCbox.setValue("00:00");

            }
        }
    

}
