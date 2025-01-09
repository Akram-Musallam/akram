/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.restaurant;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Alaa
 */
public class InsertCustomerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextField textFieldFirstName;

    @FXML
    private TextField textFieldLastName;

    @FXML
    private TextField textFieldPhoneNumber;

    @FXML
void insertButton(ActionEvent event) {
    
    String firstName = textFieldFirstName.getText().trim();
    String lastName = textFieldLastName.getText().trim();
    String phoneNumber = textFieldPhoneNumber.getText().trim();

    String conn = "jdbc:postgresql://localhost:5432/postgres";
    String user = "postgres";
    String pass = "123456";

    try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
       String insertQuery = "INSERT INTO customer (first_name, last_name, phone_number) VALUES (?, ?, ?) RETURNING ssn";

        try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
            insertStmt.setString(1, firstName);
            insertStmt.setString(2, lastName);
            insertStmt.setString(3, phoneNumber);

            ResultSet resultSet = insertStmt.executeQuery();
            if (resultSet.next()) {
                int ssn = resultSet.getInt("ssn");

                String successMessage = String.format(
                    "Customer added successfully!\n\nSSN: %d\nFirst Name: %s\nLast Name: %s\nPhone Number: %s",
                    ssn, firstName, lastName, phoneNumber
                );
                showAlert("Success", successMessage, Alert.AlertType.INFORMATION);
            } else {
                showAlert("Failure", "Failed to retrieve SSN after insertion.", Alert.AlertType.ERROR);
            }
            
        }
    } catch (Exception ex) {
        ex.printStackTrace();
        showAlert("Database Error", "An error occurred while inserting the customer.", Alert.AlertType.ERROR);
    }
}

  private void showAlert(String title, String message, Alert.AlertType alertType) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
