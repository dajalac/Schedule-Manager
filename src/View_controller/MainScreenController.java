/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_controller;

import DAO.AddressDAOImpl;
import DAO.AppointmentDAOImpl;
import DAO.CustomerDAOImpl;
import DAO.ReportsDAOImpl;
import Model.Appointment;
import Model.Customer;
import Model.Reports;
import Utils.CallNewScreen;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Utils.DisplayTerminal;

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
         
         radiobuttonsEvents();
        
    }    

    @FXML
    private void addCustomerAction(ActionEvent event) throws IOException {
        
        CallNewScreen ns = displayScreen(event);
        ns.displayScreen("AddCustomer.fxml", "New customer");
        
        /**
        // call add customer screen 
        Parent root = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
        Scene scene = new Scene(root );
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("New customer");
        stage.show(); 
        */
    }

    @FXML
    private void updateCustomeAction(ActionEvent event) throws IOException {
        
        CallNewScreen ns = displayScreen(event);
        ns.displayScreen("UpdadeCustomer.fxml", "Customer update");

        /**
        // call updade customer screen
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdadeCustomer.fxml"));
        root= loader.load();
        Scene customerScene = new Scene(root );
        Stage updadeCustomerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        updadeCustomerStage.setScene(customerScene);
        updadeCustomerStage.setTitle("Customer update");
        updadeCustomerStage.show();
        */
     
    }

    @FXML
    private void showReportsAction(ActionEvent event) throws IOException, Exception {
        String selectedRbutton = null;
        ObservableList<Reports> reportsList =FXCollections.observableArrayList();
        ReportsDAOImpl reportsDAO = new ReportsDAOImpl ();
        
        if(monthRbtn.isSelected()){
           reportsList.addAll(reportsDAO.totalyMonth());
           selectedRbutton = "month";
        }
        if(consultantRbtn.isSelected()){
           reportsList.addAll(reportsDAO.byConsultant());
           selectedRbutton = "consultant";
        }
        if(customerRbtn.isSelected()){
           reportsList.addAll(reportsDAO.byCustomers());
           selectedRbutton = "customer";
        }
        if (selectedRbutton == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Please select one type of report");
            a.setHeaderText(null);

            Optional<ButtonType> result = a.showAndWait();
            if (result.get() == ButtonType.OK) {
                a.close();
            }
        } else {
            // call report screen 
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Records.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Reports");
            stage.show();

            RecordsController controller = loader.getController();
            controller.getReportsList(reportsList, selectedRbutton);
        }
       
        
        
    }

    @FXML
    private void newAptAction(ActionEvent event) throws IOException {
        
        CallNewScreen ns = displayScreen(event);
        ns.displayScreen("NewAppointment.fxml", "New appointment");
        
        /**
        // call new appointment screen
        Parent root = FXMLLoader.load(getClass().getResource("NewAppointment.fxml"));
        Scene scene = new Scene(root );
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("New appointment");
        stage.show(); 
        */
        
    }

    @FXML
    private void editAptAction(ActionEvent event) throws IOException, Exception {

        Appointment appointment = tableViewAppt.getSelectionModel().getSelectedItem();
        
        if (appointment != null) {
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditAppointment.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Appointment upadate");
            stage.show();

            EditAppointmentController controller = loader.getController();
            controller.displayAppointment(appointment);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Please select one appointment first");
            a.setHeaderText(null);

            Optional<ButtonType> result = a.showAndWait();
            if (result.get() == ButtonType.OK) {
                a.close();
            }

        }
        
    }

    @FXML
    private void deleteAptAction(ActionEvent event) throws Exception {
       
       Appointment appointment = tableViewAppt.getSelectionModel().getSelectedItem();
      
        if (appointment != null) {
            int appointmentId = appointment.getAppointmentId();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);

            alert.setContentText("Are you sure do you want to cancel this appointment?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                appointmentDAO.deleteAppointment(appointmentId);
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("Information Dialog");
                al.setHeaderText(null);
                al.setContentText("Appointment canceled");

                Optional<ButtonType> r = al.showAndWait();
                if (r.get() == ButtonType.OK) {
                    appointmentDAO.deleteAppointment(appointmentId);
                    loadAppointments();
                    MonthCbox.setVisible(false);
                    monthLbl.setVisible(false);
                    showCbox.setValue("All");
                }
            }    
        } else {

            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Please select one appointment first");
            a.setHeaderText(null);

            Optional<ButtonType> result = a.showAndWait();
            if (result.get() == ButtonType.OK) {
                a.close();

            }
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
    /**
     * 
     * @throws Exception 
     */
    private void showCboxAction () throws Exception{
        
        String selectedShow = showCbox.getValue();
        switch (selectedShow){
            case "This week":
                showThisWeek();
                MonthCbox.setVisible(false);
                monthLbl.setVisible(false);
                break;
                
            case "Month":
                MonthCbox.setVisible(true);
                monthLbl.setVisible(true);
                break;
            case "All":
                loadAppointments();
                MonthCbox.setVisible(false);
                monthLbl.setVisible(false);
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
        
        Timestamp fistDayOfMonthUtc = TimeConversion.utcToStore(firstDaylcdt);
        Timestamp lastDayOfMonthUtc = TimeConversion.utcToStore(lastDaylcdt);
        
        ObservableList<Appointment> appointmentsOfMonth =FXCollections.observableArrayList();
        
        try{
        appointmentsOfMonth.addAll(appointmentDAO.selectedDatesAndTime(fistDayOfMonthUtc,lastDayOfMonthUtc));
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
    /**
     * 
     */
    private void tableViewEvent(){
       
        tableViewAppt.setOnMouseClicked(( event)->{
            
          Appointment appointment = tableViewAppt.getSelectionModel().getSelectedItem();
          
        });
    }
   /**
    * 
    */
    private void radiobuttonsEvents() {
       
        // create a toggle group
        ToggleGroup tg = new ToggleGroup();
        
        // add toggle
        monthRbtn.setToggleGroup(tg);
        consultantRbtn.setToggleGroup(tg);
        customerRbtn.setToggleGroup(tg);
      
    }
    /**
     * 
     * @param allAppointments 
     */
    public void fifteenMinutesAlarm (ObservableList<Appointment> allAppointments){
        
        if(!allAppointments.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Comming soon");
            alert.setHeaderText(null);
            
            for(Appointment a : allAppointments)
            alert.setContentText("Customes " + a.getCustomerName() +" has an appointment at "+ a.getStartTime() + " in "+ a.getLocation()+"\n");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                alert.close();
            }
        }
    }
    
    private CallNewScreen displayScreen(ActionEvent event){
        // lambda expression
        //It reduced the amount of code lines.It is reusable, used more than once for different buttons, and it improved the code readability 
        CallNewScreen nS = (fxmlName, title)->{
            try {
                Parent root;
                root = FXMLLoader.load(getClass().getResource(fxmlName));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle(title);     
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } ;
        
        return nS;    
    }
 
}
