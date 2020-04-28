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
import Utils.TimeConversion;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
public class EditAppointmentController implements Initializable {

    @FXML
    private Label customerNameLbl;
    @FXML
    private Label customerNameLbl2;
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
    int appointmentId; 
    String oldLocation;
    String oldConsultant;
    LocalDateTime oldldt;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
        
            goToMain(event);
        
    }

    @FXML
    private void saveBtnAction(ActionEvent event) throws Exception {
        
        String title = serviceCbox.getValue();
        String type = typeCbox.getValue();
        String note = descriptionTxtArea.getText();
        String contact = consultantCbox.getValue();
        String hour = Integer.toString(timeCbox.getSelectionModel().getSelectedIndex());
        LocalDate date =null;
        String location = locationCbox.getValue();
        Customer customer = customerTableView.getSelectionModel().getSelectedItem();
        int customerId = 0;
        try{
         customerId = customer.getCustomerId();
         date = datePicker.getValue();
        }
        catch (Exception e){}

        inputValidation(title, type, hour, date, location, customerId, contact);
   
        if (!flag) {
            updateDabase(customerId, title, type, hour, date, location, note, contact);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Appointment updated");
            a.setHeaderText(null);

            Optional<ButtonType> result = a.showAndWait();
            if (result.get() == ButtonType.OK) {
                a.close();
                goToMain(event);
            }

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
     * @param appointment
     * @throws Exception 
     */
    public void displayAppointment (Appointment appointment) throws Exception{

        appointmentId= appointment.getAppointmentId();
        serviceCbox.setValue(appointment.getTitle());
        consultantCbox.setValue(appointment.getContact());
        oldConsultant = consultantCbox.getValue();
        typeCbox.setValue(appointment.getType());
        locationCbox.setValue(appointment.getLocation());
        oldLocation = locationCbox.getValue();
        
        
        ObservableList<Customer> allCustomers =FXCollections.observableArrayList();
        allCustomers.addAll(customerDAO.getAllCustomers());
        
        int count =0; 
        for(Customer customer : allCustomers){
            if(customer.getCustomerId() != appointment.getCustomerId())
              count++;
            else
                break; 
        }
        
       
        customerTableView.getSelectionModel().select(count); 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Formate the time
        oldldt = LocalDateTime.parse(appointment.getStartTime(),formatter);
        
        LocalTime lt = oldldt.toLocalTime();
        
        LocalDate ld = oldldt.toLocalDate();
    
        datePicker.setValue(ld);
        timeCbox.setValue(lt.toString());
        descriptionTxtArea.setText(appointment.getDescription());
  
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
        
        consultantCbox.getItems().addAll("Choose one service first");
        
        typeCbox.getItems().addAll("New appointment", "Follow up");
        
        locationCbox.getItems().addAll("Pheonix, Arizona", "New York, New York", "London, England");
        
        
        timeCbox.getItems().addAll("00:00","01:00","02:00","03:00","04:00","05:00",
                "06:00","07:00","08:00","09:00","10:00","11:00",
                "12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00",
                "21:00","22:00","23:00");
     
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
     * @param customerId
     * @param contact
     * @throws Exception 
     */
    private void inputValidation(String title,String type,
            String hour,LocalDate date,String location, int customerId,String contact) throws Exception{
       
        StringBuilder msg = new StringBuilder();
        
        if(customerId ==0)
            msg.append("Select one customer \n");
        if(title.equals("Select"))
            msg.append("Select one Service \n");
        if(type.equals("Select"))
            msg.append("Select one type of appointment \n");
        if(location.equals("Select"))
            msg.append("Select one location \n");
        if (date == null) {
            msg.append("Select one date");
        } else {
            LocalDateTime apptmntLdt = TimeConversion.dateTimeCombine(date, hour, "00");
            Timestamp apptmentTmz = TimeConversion.utcToStore(apptmntLdt);
            String slot = TimeConversion.utcToLocalTime(apptmentTmz);

            if (oldldt != apptmntLdt && !oldLocation.equals(location) && !oldConsultant.equals(contact)) {
                if (appointmentDAO.checkOverloadAppt(apptmentTmz,contact)) {
                    msg.append("Our partner " + contact + "is not available during " + slot + " Try another date or time \n");
                }
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
    private void updateDabase(int customerId,String title,String type,String hour,
            LocalDate date,String location,String note,String contact) throws Exception{
        
        LocalDateTime startLdt = TimeConversion.dateTimeCombine(date, hour, "00");
        Timestamp  startTmz = TimeConversion.utcToStore(startLdt);
        
        LocalDateTime endLdt = TimeConversion.dateTimeCombine(date, hour, "55");
        Timestamp  endTmz = TimeConversion.utcToStore(endLdt);
        
        User user = userDAO.getUserNameId(1);
        String userName = user.getUserName();
        int userId = user.getUserId();
        
 
       appointmentDAO.updateAppointment(appointmentId,customerId,userId, title, note, location, 
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
      
  
}
