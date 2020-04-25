/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_controller;

import DAO.AddressDAOImpl;
import DAO.AppointmentDAOImpl;
import DAO.CustomerDAOImpl;
import Model.Appointment;
import Model.Customer;
import Utils.TimeConversion;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Locale;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Danielle
 */
public class MainScreenController implements Initializable {
  
    @FXML
    private Pane customerPane;
    @FXML
    private Label customerlbl;
    @FXML
    private Button addCustomerBtn;
    @FXML
    private Button updateCustomerBtn;
    @FXML
    private Pane reportsPane;
    @FXML
    private Label reportLbl;
    @FXML
    private Button showReportsBtn;
    @FXML
    private RadioButton monthRbtn;
    @FXML
    private RadioButton consultantRbtn;
    @FXML
    private RadioButton customerRbtn;
    @FXML
    private Pane appointmentPane;
    @FXML
    private TableView<Appointment> tableViewAppt;
    @FXML
    private TableColumn<?, ?> customerColmn;
    @FXML
    private TableColumn<?, ?> titleColmn;
    @FXML
    private TableColumn<?, ?> typeColmn;
    @FXML
    private TableColumn<?, ?> consultantColmn;
    @FXML
    private TableColumn<?, ?> descripitonColmn;
    @FXML
    private TableColumn<?, ?> startColmn;
    @FXML
    private TableColumn<?, ?> endColmn;
    @FXML
    private TableColumn<?, ?> locationColmn;
    @FXML
    private Button newAptBtn;
    @FXML
    private Button editAptBtn;
    @FXML
    private Button deleteAptBtn;
    @FXML
    private Label showLbl;
    @FXML
    private ComboBox<String> showCbox;
    @FXML
    private Label monthLbl;
    @FXML
    private ComboBox<String> MonthCbox;
    @FXML
    private Label appointmentLbl;
    
    //CustomerDAOImpl customerDAO = new CustomerDAOImpl();
    AppointmentDAOImpl appointmentDAO = new AppointmentDAOImpl();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // populate tableview
        try {
            loadAppointments();
        } catch (Exception ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         // combo box 
         SetcomboBox();
         
         showCbox.setOnAction(e -> {
             String selectedShow = showCbox.getValue();
            try {
                showCboxAction ( );
            } catch (Exception ex) {
                Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
         
         MonthCbox.setOnAction(e -> {
             String selectedShow = MonthCbox.getValue();
            try {
                showThisMonth();
            } catch (Exception ex) {
                Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
         
         tableViewEvent(); // table view event handler
        
    }    

    @FXML
    private void addCustomerAction(ActionEvent event) throws IOException {
        
        // call add customer screen 
        Parent root = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
        Scene scene = new Scene(root );
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
       stage.setTitle("New customer");
        stage.show(); 
    }

    @FXML
    private void updateCustomeAction(ActionEvent event) throws IOException {
        

        // call updade customer screen
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdadeCustomer.fxml"));
        root= loader.load();
        Scene customerScene = new Scene(root );
        Stage updadeCustomerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        updadeCustomerStage.setScene(customerScene);
        updadeCustomerStage.setTitle("Customer update");
        updadeCustomerStage.show();
     
    }

    @FXML
    private void showReportsAction(ActionEvent event) throws IOException {
        // call report screen 
        Parent root = FXMLLoader.load(getClass().getResource("Records.fxml"));
        Scene scene = new Scene(root );
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Reports");
        stage.show(); 
        
        
    }

    @FXML
    private void newAptAction(ActionEvent event) throws IOException {
        
        // call new appointment screen
        Parent root = FXMLLoader.load(getClass().getResource("NewAppointment.fxml"));
        Scene scene = new Scene(root );
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("New appointment");
        stage.show(); 
        
        
    }

    @FXML
    private void editAptAction(ActionEvent event) throws IOException {
        
        Appointment appointment = tableViewAppt.getSelectionModel().getSelectedItem();
        
        // call appointment update screen
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditAppointment.fxml"));
        root= loader.load();
        Scene scene = new Scene(root );
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Appointment upadate");
        stage.show();
        
        EditAppointmentController controller = loader.getController();
        controller.displayAppointment(appointment);
        
    }

    @FXML
    private void deleteAptAction(ActionEvent event) throws Exception {
       
       Appointment appointment = tableViewAppt.getSelectionModel().getSelectedItem();
       int appointmentId = appointment.getAppointmentId();
       
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       alert.setTitle("Confirmation Dialog");
       alert.setHeaderText(null);
       
       alert.setContentText("Are you sure do you want to cancel this appointment?");

        Optional<ButtonType> result = alert.showAndWait();
         if (result.get() == ButtonType.OK){
            appointmentDAO.deleteAppointment(appointmentId);
            loadAppointments();
         }
    }
    
    /**
     * 
     * @throws Exception 
     */
    private void loadAppointments() throws Exception{
        ObservableList<Appointment> allAppointments =FXCollections.observableArrayList();
        
        allAppointments.addAll(appointmentDAO.getAllAppointments());
       
        populateTableView();
        
        tableViewAppt.setItems(allAppointments);
        
    }
    /**
     * 
     */
    private void SetcomboBox(){
        
        showCbox.getItems().addAll("All","This week","Month");
        showCbox.setValue("All");
        MonthCbox.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        MonthCbox.setValue("Select");
    }
    
    private void showCboxAction () throws Exception{
        
        String selectedShow = showCbox.getValue();
        switch (selectedShow){
            case "This week":
                showThisWeek();
                break;
                
            case "Month":
                MonthCbox.setVisible(true);
                monthLbl.setVisible(true);
                break;
            case "All":
                loadAppointments();
                break;
        }
        
    }
    /**
     * 
     * @throws Exception 
     */
    private void showThisWeek() throws Exception{
        
        LocalDate now = LocalDate.now();
        
        DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        LocalDate startOfCurrentWeek = now.with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
       
        DayOfWeek lastDayOfWeek = firstDayOfWeek.plus(6); 
        LocalDate endOfWeek = now.with(TemporalAdjusters.nextOrSame(lastDayOfWeek));
       
        LocalDateTime firstDaylcdt = TimeConversion.dateTimeCombine(startOfCurrentWeek,"00", "00");
        LocalDateTime lastDaylcdt = TimeConversion.dateTimeCombine(endOfWeek,"00", "00");
        
        Timestamp fistDayOfWeekUtc = TimeConversion.utcToStore(firstDaylcdt);
        Timestamp lastDayOfWeekUtc = TimeConversion.utcToStore(lastDaylcdt);
        
        
        ObservableList<Appointment> appointmentsOfWeek =FXCollections.observableArrayList();
        
         try{
        appointmentsOfWeek.addAll(appointmentDAO.selectedDatesAndTime(fistDayOfWeekUtc,lastDayOfWeekUtc));
        }
        catch (NullPointerException e){
            tableViewAppt.setPlaceholder(new Label ("There is no appointment for this week"));
        }
        
        populateTableView();
        
        tableViewAppt.setItems(appointmentsOfWeek);
          
    }
    /**
     * 
     */
    private void showThisMonth() throws Exception{
        
        Calendar noww = Calendar.getInstance();
        int year = noww.get(Calendar.YEAR);
        int month = MonthCbox.getSelectionModel().getSelectedIndex();
        
        
        YearMonth yearMonth = YearMonth.of(year, month+1);
        LocalDate firstOfMonth = yearMonth.atDay(1);
        LocalDate lastDay = yearMonth.atEndOfMonth();
        
        LocalDateTime firstDaylcdt = TimeConversion.dateTimeCombine(firstOfMonth,"00", "00");
        LocalDateTime lastDaylcdt = TimeConversion.dateTimeCombine(lastDay,"00", "00");
        
        Timestamp fistDayOfWeekUtc = TimeConversion.utcToStore(firstDaylcdt);
        Timestamp lastDayOfWeekUtc = TimeConversion.utcToStore(lastDaylcdt);
        
        ObservableList<Appointment> appointmentsOfMonth =FXCollections.observableArrayList();
        
        try{
        appointmentsOfMonth.addAll(appointmentDAO.selectedDatesAndTime(fistDayOfWeekUtc,lastDayOfWeekUtc));
        }
        catch (NullPointerException e){
            tableViewAppt.setPlaceholder(new Label ("There is no appointment for "+ MonthCbox.getValue()));
        }
        
        populateTableView();
        
        tableViewAppt.setItems(appointmentsOfMonth);
        
    }
    /**
     * 
     */
    private void populateTableView(){
        customerColmn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        titleColmn.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeColmn.setCellValueFactory(new PropertyValueFactory<>("type"));
        consultantColmn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        startColmn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endColmn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        locationColmn.setCellValueFactory(new PropertyValueFactory<>("location"));
    }
    
    private void tableViewEvent(){
       
        tableViewAppt.setOnMouseClicked(( event)->{
            
          Appointment appointment = tableViewAppt.getSelectionModel().getSelectedItem();
          
        });
    }
   
  
}
