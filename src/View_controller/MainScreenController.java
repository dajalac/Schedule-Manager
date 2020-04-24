/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_controller;

import DAO.CustomerDAOImpl;
import Model.Customer;
import java.io.IOException;
import java.net.URL;
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
    private TableView<?> tableViewAppt;
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
    private TableColumn<?, ?> dateTimeColmn;
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
    private ComboBox<?> showCbox;
    @FXML
    private Label monthLbl;
    @FXML
    private ComboBox<?> MonthCbox;
    @FXML
    private Label appointmentLbl;
    
    CustomerDAOImpl customerDAO = new CustomerDAOImpl();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }    

    @FXML
    private void addCustomerAction(ActionEvent event) throws IOException {
        
        // call add customer screen 
        Parent addProductsRoot = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
        Scene addProductsScene = new Scene(addProductsRoot );
        Stage addProductsStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addProductsStage.setScene(addProductsScene);
        addProductsStage.setTitle("New customer");
        addProductsStage.show(); 
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
        Parent addProductsRoot = FXMLLoader.load(getClass().getResource("Records.fxml"));
        Scene addProductsScene = new Scene(addProductsRoot );
        Stage addProductsStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addProductsStage.setScene(addProductsScene);
        addProductsStage.setTitle("Reports");
        addProductsStage.show();
        
    }

    @FXML
    private void newAptAction(ActionEvent event) throws IOException {
        
        // call new appointment screen
        Parent addProductsRoot = FXMLLoader.load(getClass().getResource("NewAppointment.fxml"));
        Scene addProductsScene = new Scene(addProductsRoot );
        Stage addProductsStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addProductsStage.setTitle("New appointment");
        addProductsStage.setScene(addProductsScene);
        addProductsStage.show();
    }

    @FXML
    private void editAptAction(ActionEvent event) throws IOException {
        
        // call appointment update screen
        Parent addProductsRoot = FXMLLoader.load(getClass().getResource("EditAppointment.fxml"));
        Scene addProductsScene = new Scene(addProductsRoot );
        Stage addProductsStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addProductsStage.setScene(addProductsScene);
        addProductsStage.setTitle("Appointment upadate");
        addProductsStage.show();
    }

    @FXML
    private void deleteAptAction(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       alert.setTitle("Confirmation Dialog");
       alert.setHeaderText(null);
       
       alert.setContentText("Are you sure do you want to delete this customer?");

        Optional<ButtonType> result = alert.showAndWait();
         if (result.get() == ButtonType.OK){
            // show what to do
         }
    }
   
   
  
}
