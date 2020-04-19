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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Danielle
 */
public class RecordsController implements Initializable {

    @FXML
    private TableView<?> reportTableView;
    @FXML
    private TableColumn<?, ?> Column1;
    @FXML
    private TableColumn<?, ?> column2;
    @FXML
    private TableColumn<?, ?> column3;
    @FXML
    private Button okBtn;
    @FXML
    private Label reportLabel;
    @FXML
    private ComboBox<?> reportCbox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void okBtnAction(ActionEvent event) throws IOException {
        
        Parent modifyProductsRoot = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene modifyProductsScene = new Scene(modifyProductsRoot);
        Stage modifyProductsStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        modifyProductsStage.setScene(modifyProductsScene);
        modifyProductsStage.show();
    }
    
}
