/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_controller;

import DAO.AddressDAOImpl;
import DAO.AppointmentDAOImpl;
import DAO.CityDAOImpl;
import DAO.CountryDAOImpl;
import DAO.CustomerDAOImpl;
import DAO.UserDAOImpl;
import Model.Appointment;
import Model.Customer;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TableView<Customer> customerTableView;
    @FXML
    private TextField nameTxt;
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
    @FXML
    private Button deleteCustomerBtn;

    CustomerDAOImpl customerDAO = new CustomerDAOImpl();
    AddressDAOImpl addressDAO = new AddressDAOImpl();
    CityDAOImpl cityDAO = new CityDAOImpl();
    CountryDAOImpl countryDAO = new CountryDAOImpl();
    UserDAOImpl userDAO = new UserDAOImpl();
    StringBuilder msg = new StringBuilder();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // show customers list       
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        try {

            allCustomers.addAll(customerDAO.getAllCustomers());
        } catch (Exception ex) {
            Logger.getLogger(UpdadeCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerTableView.setItems(allCustomers);

        Customer customer = customerTableView.getSelectionModel().getSelectedItem();
        if (customer == null) {
            saveCustomerBtn.setDisable(true);
            deleteCustomerBtn.setDisable(true);
        }

        // get table view action
        customerTableView.setOnMouseClicked((event) -> {
            try {
                displaySelected();
                saveCustomerBtn.setDisable(false);
                deleteCustomerBtn.setDisable(false);
            } catch (Exception ex) {
                Logger.getLogger(UpdadeCustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    @FXML
    private void cancelBtnAction(ActionEvent event) throws IOException {

        goToMain(event);

    }

    @FXML
    private void SaveBtnAction(ActionEvent event) throws Exception {
        StringBuilder noCustomer = new StringBuilder();
        String userName = userDAO.getUserNameId(1).getUserName();
        boolean flag = true;

        // GET user input
        String customerName = nameTxt.getText().trim();
        String countryName = countryCbox.getValue();
        String city = cityTxt.getText().trim();
        String address1 = addres1Txt.getText().trim();
        String address2 = addres2Txt.getText().trim();
        int postalCodeInt = 111111;
        int phoneNumberInt = 1111111;

        int countryId = updateCountry(userName, countryDAO, countryName, msg);

        if (msg.length() != 0) {
            errorMessage();
        } else {
            flag = false;
        }

        if (!flag) {
            updateCity(userName, countryDAO, cityDAO, countryId, city, msg);
            int addressId = updateAddress(userName, cityDAO, city, phoneNumberInt,
                    postalCodeInt, msg, address1, address2, addressDAO, countryId);
            updateCustomer(userName, addressId, customerName, customerDAO, msg);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Update saved!");
            a.setHeaderText(null);

            Optional<ButtonType> result = a.showAndWait();
            if (result.get() == ButtonType.OK) {
                a.close();
                goToMain(event);
            }
        }

    }

    @FXML
    private void deleteBtnAction(ActionEvent event) throws IOException, Exception {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);

        alert.setContentText("Are you sure do you want to delete this customer?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            deleteCustomer(event);
        }

    }

    /**
     *
     * @param event
     * @throws IOException
     */
    private void goToMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Appointment Scheduler");
        stage.show();
    }

    /**
     *
     */
    private void errorMessage() {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(msg.toString());
        a.setHeaderText(null);

        Optional<ButtonType> result = a.showAndWait();
        if (result.get() == ButtonType.OK) {
            a.close();
            msg.setLength(0);
        }
    }

    /**
     * display selected customer info in the scree
     *
     * @throws Exception
     */
    private void displaySelected() throws Exception {

        Customer customer = customerTableView.getSelectionModel().getSelectedItem();

        if (customer == null) {
            msg.append("Please select one customer \n");
        }

        int aId = customer.getAddressId();

        String address = addressDAO.selectedAddress(aId).getAddress();
        String address2 = addressDAO.selectedAddress(aId).getAddress2();
        int cityId = addressDAO.selectedAddress(aId).getCityId();
        String city = cityDAO.selectedCity(cityId).getCity();
        String postalcode = addressDAO.selectedAddress(aId).getPostalCode();
        String phone = addressDAO.selectedAddress(aId).getPhone();
        int countryId = cityDAO.selectedCity(cityId).getCountryId();
        String country = countryDAO.getCountryName(countryId);

        nameTxt.setText(customer.getCustomerName());
        addres1Txt.setText(address);
        addres2Txt.setText(address2);
        cityTxt.setText(city);
        postalCodeTxt.setText(postalcode);
        phoneTxt.setText(phone);

        // display countries options
        ObservableList<String> countryList = FXCollections.observableArrayList();

        Locale.setDefault(Locale.US); // my pc keep showing the names in portugueses

        String[] countryISO = Locale.getISOCountries();

        for (String countryName : countryISO) {
            Locale locale = new Locale(" ", countryName);
            String name = locale.getDisplayCountry();
            countryList.add(name);
        }
        countryCbox.setItems(countryList);
        countryCbox.setValue(country);

    }

    /**
     * Add country in case the user changed the country
     *
     * @param userName
     * @param countryDAO
     * @param countryName
     * @param msg
     */
    private int updateCountry(String userName, CountryDAOImpl countryDAO, String countryName, StringBuilder msg) throws Exception {
        //error message if any country is selected

        if (countryName.equalsIgnoreCase("Select")) {
            msg.append("Please,select one Country \n");
        }
        try {
            //check if country already exist in bd
            if (!countryDAO.getAllCountries(countryName)) {
                countryDAO.insertCountry(countryName, userName);
            }
        } catch (Exception ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return countryDAO.getCountryId(countryName);
    }

    /**
     * Add a new city if the user update to a new city
     *
     * @param userName
     * @param countryDAO
     * @param cityDAO
     * @param countryName
     * @param city
     * @param msg
     * @throws Exception
     */
    private void updateCity(String userName, CountryDAOImpl countryDAO, CityDAOImpl cityDAO, int countryId,
            String city, StringBuilder msg) throws Exception {

        String cityName = " ";

        //input validation
        try {
            cityName = city.substring(0, 1).toUpperCase() + city.substring(1).toLowerCase();// to fomrtat city name entry
        } catch (StringIndexOutOfBoundsException e) {
            msg.append("City's name must have at least two characters \n");
        }

        // insert into db if the user add a differente city
        if (!cityDAO.checkAllCities(cityName, countryId)) {
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
     * @param addressDAO
     * @return
     * @throws Exception
     */
    private int updateAddress(String userName, CityDAOImpl cityDAO, String cityName,
            int phoneInt, int postalCodeInt, StringBuilder msg, String address1,
            String address2, AddressDAOImpl addressDAO, int countryId) throws Exception {

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
     * @throws Exception
     */
    private void updateCustomer(String userName, int addressId, String customerName,
            CustomerDAOImpl customerDAO, StringBuilder msg) throws Exception {

        Customer customer = customerTableView.getSelectionModel().getSelectedItem();

        if (customerName.isEmpty()) {
            msg.append("Please, insert the customer name");
        } else {
            customerDAO.UpdateCustomer(customer.getCustomerId(), customerName, addressId, userName);
        }

    }

    /**
     *
     * @throws Exception
     */
    private void deleteCustomer(ActionEvent event) throws Exception {
        boolean flag = false;
        AppointmentDAOImpl appointmentDAO = new AppointmentDAOImpl();
        ObservableList<Appointment> allappointments = FXCollections.observableArrayList();
        allappointments.addAll(appointmentDAO.getAllAppointments());

        Customer customer = customerTableView.getSelectionModel().getSelectedItem();
        int customerId = customer.getCustomerId();

        for (Appointment aptmnt : allappointments) {
            if (aptmnt.getCustomerId() == customerId) {
                flag = true;
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText(customer.getCustomerName() + " has an appontment schedule for : "
                        + aptmnt.getStartTime() + " at " + aptmnt.getLocation() + "\n Please cancel this appointment before "
                        + "delete the customer");
                a.setHeaderText(null);

                Optional<ButtonType> result = a.showAndWait();
                if (result.get() == ButtonType.OK) {
                    a.close();
                }
                break;

            }
        }

        if (!flag) {
            customerDAO.deleteCustomer(customerId);

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Customer deleted!");
            a.setHeaderText(null);

            Optional<ButtonType> result = a.showAndWait();
            if (result.get() == ButtonType.OK) {
                a.close();
                goToMain(event);
            }
        }
    }

}
