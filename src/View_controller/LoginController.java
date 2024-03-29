/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_controller;

import DAO.AppointmentDAOImpl;
import DAO.UserDAOImpl;
import Model.Appointment;
import Model.User;
import Utils.TimeConversion;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
    private PasswordField passwordTxt;
      
    @FXML
    private ImageView imageView;
    
    boolean autentication = false;

    
    ResourceBundle rb= ResourceBundle.getBundle("Utils/ResourceBundle_pt_br",Locale.getDefault());
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // Locale.setDefault(Locale.US);

        // to show user icon 
        Image image = new Image("/images/user.png");
        imageView.setImage(image);

        // to change the language
        rb = ResourceBundle.getBundle("Utils/ResourceBundle_pt_br", Locale.getDefault());
        if (Locale.getDefault().getLanguage().equals("pt")) {
            userLbl.setText(rb.getString("user")); // pronpt is the key word
            passwordLbl.setText(rb.getString("password"));
            enterBtn.setText(rb.getString("enterBtn")); // set the button button text as the key 
        }
       
       
       
       
    }    

    @FXML
    private void enterBtnAction(ActionEvent event) throws IOException, Exception {
      
          String userName = userTxt.getText().trim(); 
          String password = passwordTxt.getText().trim();
          
          UserDAOImpl userDaoImpl = new UserDAOImpl();
          User user = userDaoImpl.getUser(userName, password);
           
        if (userName.isEmpty() || password.isEmpty()) {
            emptyField();
        } else {
            if (!autentication) {
                if (user != null) {
                    autentication = true;
                    userDaoImpl.setUserAct(user.getUserId()); // set the active user
                    
                    String msg = "Access granted"; // log txt
                    log(userName, msg );
                    
                    goToMain(event); // go to main screen 
                } else {
                    wrongLogin();
                    String msg = "Access denied!"; // tog txt
                    log(userName, msg );
                }
            }
        }
    }
    
    /**
     * @param event
     * @throws IOException 
     * calls the main screen
     */
    private void goToMain(ActionEvent event) throws IOException, Exception{
        
        AppointmentDAOImpl appointmentDAO = new AppointmentDAOImpl();
        LocalDateTime minutesLater = LocalDateTime.now().plusMinutes(15);
        Timestamp fifteenMinutes = TimeConversion.utcToStore(minutesLater);
        Timestamp now = TimeConversion.utcToStore(LocalDateTime.now()); 
        
        ObservableList<Appointment> allAppointments =FXCollections.observableArrayList();
    
         allAppointments.addAll(appointmentDAO.selectedDatesAndTime(now,fifteenMinutes));
   
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Appointment Scheduler");
        stage.show();  
        
        MainScreenController controller = loader.getController();
        controller.fifteenMinutesAlarm(allAppointments);
    }
    
    /**
     * to show wrong login error message
     */
    private void wrongLogin (){

             Alert a = new Alert(AlertType.ERROR);
	        if (Locale.getDefault().getLanguage().equals("pt")){
		     a.setContentText(rb.getString("message"));// muda texto
                     a.setHeaderText(null);
                     a.setTitle(rb.getString("title"));
                }
                else{
		    a.setContentText("User and/or password incorrect. Please try again!");
		    a.setHeaderText(null);
                    a.setTitle("Error");
                    
                }
                Optional<ButtonType> result = a.showAndWait();
                if (result.get() == ButtonType.OK)
                    userTxt.clear(); 
                    passwordTxt.clear();
                    a.close();     
    }
    /**
     * show empty field error
     */
    
    private void emptyField(){
        
        Alert a = new Alert(AlertType.ERROR);
	        if (Locale.getDefault().getLanguage().equals("pt")){
		     a.setContentText(rb.getString("empty"));// muda texto
                }
                else{
		    a.setContentText("User and password cannot be empty");
		    a.setHeaderText(null);
                }
                Optional<ButtonType> result = a.showAndWait();
                if (result.get() == ButtonType.OK)
                    a.close(); 
    }
    
    /**
     * To write the logging attempts
     * @param userName
     * @param succeedOrNot
     * @throws IOException 
     */
    private void log(String userName, String succeedOrNot) throws IOException{
        
       LocalDateTime ldt = LocalDateTime.now();
       String timeUtc = TimeConversion. toUtcString(ldt);

       String msg = timeUtc + " [UTC]"+ "- User: "+ userName +" "+ succeedOrNot+"\n"; 
       
       //write to the file 
       FileWriter writer = new FileWriter("log.txt", true);
       BufferedWriter buffW = new BufferedWriter(writer); 
       buffW.write(msg);
       buffW.close();
        
       // to check without open the TXT file
        Logger logger = Logger.getLogger(LoginController.class.getName());
        logger.info(msg);

    }
}
