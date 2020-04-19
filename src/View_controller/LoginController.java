/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author Danielle
 */
public class LoginController implements Initializable {
    
    
    @FXML
    private Button enterBtn;
    @FXML
    private Label userLbl;
    @FXML
    private Label passwordLbl;
    @FXML
    private TextField userTxt;
    @FXML
    private TextField passwordTxt;
    @FXML
    private ImageView imageView;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // to show user icon 
        Image image = new Image("/images/user.png");
        imageView.setImage(image);
        
    }    

    @FXML
    private void enterBtnAction(ActionEvent event) throws IOException {
        
        // call the main Screen 
        Parent addProductsRoot = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene addProductsScene = new Scene(addProductsRoot );
        Stage addProductsStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addProductsStage.setScene(addProductsScene);
        addProductsStage.setTitle("Appointment Scheduler");
        addProductsStage.show();  
    }
    
}
