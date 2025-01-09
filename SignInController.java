/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.restaurant;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
/**
 * FXML Controller class
 *
 * @author Alaa
 */
public class SignInController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private TextField textField_email_name;
    
    @FXML
    private Button sign_in;
    
    @FXML
    private Button insert;

    @FXML
    private PasswordField textField_Password;
    
    @FXML
    public void Sign_In(ActionEvent event) {
        
    //EntityManagerFactory emf = Persistence.createEntityManagerFactory("Restaurant_PU");
   // EntityManager em = emf.createEntityManager();
    

    try {
        DriverManager.registerDriver(new org.postgresql.Driver());
        String conn="jdbc:postgresql://localhost:5432/postgres";
        String name="postgres";
        String pass="123456";
            Connection con = DriverManager.getConnection(conn, name, pass);
            String managerQuery  = "SELECT * FROM Manager WHERE email = ? AND ssn = ?";
            PreparedStatement managerStmt  = con.prepareStatement(managerQuery );
            managerStmt.setString(1, textField_email_name.getText()); // Set email parameter
            managerStmt.setInt(2, Integer.parseInt((textField_Password.getText())));
            
            ResultSet resultSet = managerStmt .executeQuery();
            
            if (resultSet.next()) {
                // Redirect to Manager page
                Stage stage = (Stage) sign_in.getScene().getWindow();
                stage.close();
                Parent root = FXMLLoader.load(getClass().getResource("Manager.fxml"));
                Stage newStage = new Stage();
                Scene scene = new Scene(root);
                newStage.setScene(scene);
                newStage.show();
            } else {
                String customerQuery = "SELECT * FROM customer WHERE first_name = ? AND ssn = ?";
                PreparedStatement customerStmt = con.prepareStatement(customerQuery);
                customerStmt.setString(1, textField_email_name.getText());
                customerStmt.setInt(2, Integer.parseInt((textField_Password.getText())));
                ResultSet customerResult = customerStmt.executeQuery();

                if (customerResult.next()) {
                    int ssn = customerResult.getInt("ssn");
                    Stage currentStage = (Stage) sign_in.getScene().getWindow();
                    currentStage.close();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Customer.fxml"));
                    Parent root = loader.load();
                    CustomerController controller = loader.getController();
                    controller.setCustomerId(ssn);
                    Stage newStage = new Stage();
                    Scene scene = new Scene(root);
                    newStage.setScene(scene);
                    newStage.show();
            }
                else {
                String waiterQuery = "SELECT * FROM waiter WHERE email = ? AND ssn = ?";
                PreparedStatement waiterStmt = con.prepareStatement(waiterQuery);
                waiterStmt.setString(1, textField_email_name.getText());
                waiterStmt.setInt(2, Integer.parseInt((textField_Password.getText())));
                ResultSet waiterResult = waiterStmt.executeQuery();

                if (waiterResult.next()) {
                    // Customer found, proceed to Customer page
                    int ssn = waiterResult.getInt("ssn");
                    Stage stage = (Stage) sign_in.getScene().getWindow();
                    stage.close();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Waiter.fxml"));
                    Parent root = loader.load();
                    WaiterController controller = loader.getController();
                    controller.setWaiterId(ssn);
                    Stage newStage = new Stage();
                    Scene scene = new Scene(root);
                    newStage.setScene(scene);
                    newStage.show();
                }
                
            }
        }
    } catch (IOException | SQLException ex) {
        ex.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("An unexpected 11111111error occurred. Please try again later.");
        alert.showAndWait();
    } 
  }
    
    @FXML
    void ClickButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("insertCustomer.fxml"));

        Stage newStage = new Stage();
        Scene scene = new Scene(root);

        newStage.setScene(scene);
        newStage.show();
    }

}
