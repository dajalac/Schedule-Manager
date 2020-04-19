/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_controller;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Danielle
 */
public class UpdadeCustomerController implements Initializable {

    @FXML
    private Pane namePane;
    @FXML
    private TableView<?> customerTableView;
    @FXML
    private TableColumn<?, ?> customerNameColumn;
    @FXML
    private Pane namePane1;
    @FXML
    private Label addres1Lbl;
    @FXML
    private TextField addres1Txt;
    @FXML
    private Pane namePane11;
    @FXML
    private Label addres2Lbl;
    @FXML
    private TextField addres2Txt;
    @FXML
    private Pane namePane111;
    @FXML
    private Label countryLbl;
    @FXML
    private ComboBox<?> countryCbox;
    @FXML
    private Pane namePane112;
    @FXML
    private Label cityLbl;
    @FXML
    private TextField cityTxt;
    @FXML
    private Pane namePane1121;
    @FXML
    private Label postalCodeLbl;
    @FXML
    private TextField postalCodeTxt;
    @FXML
    private Pane namePane11211;
    @FXML
    private Label phoneLbl;
    @FXML
    private TextField phoneTxt;
    @FXML
    private Button addCustomerCancelBtn;
    @FXML
    private Button saveCustomerBtn;
    @FXML
    private Label CustomerLbl;
    @FXML
    private Button deleteCustomerBtn;

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
    private void SaveBtnAction(ActionEvent event) {
    }

    @FXML
    private void deleteBtnAction(ActionEvent event) throws IOException {
        
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       alert.setTitle("Confirmation Dialog");
       alert.setHeaderText(null);
       
       alert.setContentText("Are you sure do you want to delete this customer?");

        Optional<ButtonType> result = alert.showAndWait();
         if (result.get() == ButtonType.OK){
            // show what to do
            goToMain(event);
         }
             
    }
    
    private void goToMain(ActionEvent event) throws IOException{
        Parent modifyProductsRoot = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene modifyProductsScene = new Scene(modifyProductsRoot);
        Stage modifyProductsStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        modifyProductsStage.setScene(modifyProductsScene);
        modifyProductsStage.show(); 
    }
}
