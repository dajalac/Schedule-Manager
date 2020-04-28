/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_controller;

import DAO.AddressDAOImpl;
import DAO.CityDAOImpl;
import DAO.CountryDAOImpl;
import DAO.CustomerDAOImpl;
import DAO.UserDAOImpl;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Danielle
 */
public class AddCustomerController implements Initializable {

    @FXML
    private Pane namePane;
    @FXML
    private Label nameLbl;
    @FXML
    private TextField nameTxt;
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
    private ComboBox<String> countryCbox;
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
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
       // display countries options
        ObservableList<String> countryList =FXCollections.observableArrayList();
       
        Locale.setDefault(Locale.US); // my pc keep showing the names in portugueses
        
        String[] countryISO = Locale.getISOCountries();
       
       for(String countryName : countryISO){
           Locale locale = new Locale(" ", countryName);
           String name = locale.getDisplayCountry();
           countryList.add(name);
       }
         countryCbox.setItems(countryList);
         countryCbox.setValue("Select");
       
    }    

    /**
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    private void cancelBtnAction(ActionEvent event) throws IOException {
        
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       alert.setTitle("Confirmation Dialog");
       alert.setHeaderText(null);
       
       alert.setContentText("Are you sure do you want to cancel this operation?");

        Optional<ButtonType> result = alert.showAndWait();
         if (result.get() == ButtonType.OK){ 
        
        goToMain(event);
        
        }
    }

    /**
     * 
     * @param event 
     */
    @FXML
    private void saveBtnAction(ActionEvent event) throws Exception {
        boolean flag = true;
        
        UserDAOImpl userDAO = new UserDAOImpl();
        CountryDAOImpl countryDAO = new CountryDAOImpl();
        CityDAOImpl cityDAO = new CityDAOImpl();
        AddressDAOImpl addressDAO = new AddressDAOImpl();
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
        StringBuilder msg = new StringBuilder();
        String userName= userDAO.getUserNameId(1).getUserName(); 
        // GET user input
       
        String customerName = nameTxt.getText().trim();
        String countryName = countryCbox.getValue(); 
        String city = cityTxt.getText().trim();
        String address1 = addres1Txt.getText().trim(); 
        String address2 = addres2Txt.getText().trim();
        int postalCodeInt = 0; 
        int phoneNumberInt = 0; 
   
         int countryId=addCountry(userName, countryDAO, countryName, msg);
        addCity(userName, countryDAO, cityDAO, countryName,city,msg);
        int addressId = addAddress(userName, cityDAO, city,phoneNumberInt ,postalCodeInt,
                         msg, address1, address2, addressDAO,countryId);
        
        checkIfCustomerExist(customerDAO, addressId, customerName, msg);
               
        
        if( msg.length()!= 0)
         errorMessage(msg);
        else
            flag = false;
        
        if(!flag){
            addCustomer (userName, addressId,customerName,customerDAO, msg); 
            clearFields();
            
        }
    }
    /**
     * 
     * @param event
     * @throws IOException 
     */
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
     * @param userName
     * @param countryDAO
     * @param selected
     * @throws Exception 
     */
    private int addCountry (String userName, CountryDAOImpl countryDAO, String selected,
            StringBuilder msg) throws Exception{
       
        //error message if any country is selected
        
        if(selected.equalsIgnoreCase("Select")){
            msg.append("Please,select one Country \n");
            
        }
        try {
            //check if country already exist in bd
            if(!countryDAO.getAllCountries(selected)){
                countryDAO.insertCountry(selected,userName);
            }
        } catch (Exception ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
       return countryDAO.getCountryId(selected); 
    }
    
    /**
     * 
     * @param userName
     * @param countryDAO
     * @param cityDAO
     * @param countryName
     * @param cityName
     * @throws Exception 
     */
    private void addCity(String userName, CountryDAOImpl countryDAO, CityDAOImpl cityDAO, String countryName,
                         String city, StringBuilder msg ) throws Exception{
        
        
        int countryId = countryDAO.getCountryId(countryName);
        String cityName = " ";

        //input validation
        try {
            cityName = city.substring(0, 1).toUpperCase() + city.substring(1).toLowerCase();// to fomrtat city name entry
        } catch (StringIndexOutOfBoundsException e) {
            msg.append("City's name must have at least two characters \n");
        }
               
        // insert into db 
        if(!cityDAO.checkAllCities(cityName, countryId)){
           cityDAO.newCity(cityName, userName, countryId);
        }
        
        
        
    }
    
    /**
     * 
     * @param userName
     * @param cityDAO
     * @param cityName
     * @param phoneInt
     * @param postalCodeInt
     * @param msg
     * @param address1
     * @param address2 
     */
    private int addAddress(String userName,CityDAOImpl cityDAO,String cityName, 
                  int phoneInt , int postalCodeInt ,StringBuilder msg,  String address1,
                  String address2, AddressDAOImpl addressDAO,int countryId ) throws Exception{
        
        int cityId = cityDAO.getCityId(cityName, countryId);

        // input validation 
        try {
            postalCodeInt = Integer.parseInt(postalCodeTxt.getText());

        } catch (NumberFormatException e) {
            msg.append("The postal code cannot be empty and must contain just numbers \n");
        }
        try {
            phoneInt = Integer.parseInt(phoneTxt.getText());
        } catch (NumberFormatException e) {
            msg.append("The phone number cannot be empty and must contain just numbers \n");
        }
        if (address1.isEmpty()) {
            msg.append("The address cannot be empty.");
        }
        if (address2.isEmpty()) {
            address2 = " ";
        }

        // insert into db
        if (!addressDAO.getAllAddress(address1, address2, cityId, Integer.toString(postalCodeInt),
                Integer.toString(phoneInt))) {

            addressDAO.insertAddress(address1, address2, cityId, Integer.toString(postalCodeInt),
                    Integer.toString(phoneInt), userName);
        }

        // get address Id
        int addressId = addressDAO.getAdressId(address1, address2, cityId, Integer.toString(postalCodeInt),
                Integer.toString(phoneInt));
        return addressId; 
    }
   /**
    * 
    * @param userName
    * @param addressId
    * @param customerName
    * @param customerDAO
    * @param msg 
    */
    private void addCustomer (String userName, int addressId,String customerName,
            CustomerDAOImpl customerDAO, StringBuilder msg) throws Exception{
        
        
        
        if(customerName.isEmpty()){
            msg.append("Please, insert the customer name");
        }
        
        if(customerDAO.selectedCustomer(customerName)== null){ // new name
            
            customerDAO.insertCustomer(customerName, addressId, userName);
            
        } 
            
        
    } 
    /**
     * 
     * @param msg 
     */
    private void errorMessage (StringBuilder msg){
        Alert a = new Alert(Alert.AlertType.ERROR);
		a.setContentText(msg.toString());
		a.setHeaderText(null);
               
                Optional<ButtonType> result = a.showAndWait();
                if (result.get() == ButtonType.OK){
                    a.close();
                    msg.setLength(0);
                }
    }
    
    public void clearFields(){
        
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("New customer saved!");
        a.setHeaderText(null);

        Optional<ButtonType> result = a.showAndWait();
        if (result.get() == ButtonType.OK) {
            a.close();
            nameTxt.clear();
            countryCbox.setValue("Select");
            cityTxt.clear();
            addres1Txt.clear();
            addres2Txt.clear();
            postalCodeTxt.clear();
            phoneTxt.clear();

        }
    }
    
    private void checkIfCustomerExist(CustomerDAOImpl customerDAO, int addressId, String customerName, StringBuilder msg) throws Exception {

      //  System.out.println("here "+ customerName + addressId);
        if ( customerDAO.selectedCustomer(customerName)!= null &&
                customerDAO.selectedCustomer(customerName).getAddressId() == addressId) {
            msg.append("The customer already exist in the system");
        }
    }  
  
}
