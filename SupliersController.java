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
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Alaa
 */
public class SupliersController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    @FXML
    private Button ordersButtonSupliers;

    @FXML
    private AnchorPane ordersForm;

    @FXML
    private Button supliersButtonSupliers;

    @FXML
    private AnchorPane supliersForm;

    @FXML
    private Button warehouseButtonSupliers;

    @FXML
    private AnchorPane warehouseForm;

    @FXML
    void switchForm(ActionEvent event) {
        if(event.getSource()==warehouseButtonSupliers){
            warehouseForm.setVisible(true);
            ordersForm.setVisible(false);
            supliersForm.setVisible(false);
            
            warehouseButtonSupliers.setStyle("-fx-background-color:#ffffff55");
            ordersButtonSupliers.setStyle("-fx-background-color:transparent");
            supliersButtonSupliers.setStyle("-fx-background-color:transparent");
        }
       else if(event.getSource()==ordersButtonSupliers){
            warehouseForm.setVisible(false);
            ordersForm.setVisible(true);
            supliersForm.setVisible(false);
            
            ordersButtonSupliers.setStyle("-fx-background-color:#ffffff55");
            warehouseButtonSupliers.setStyle("-fx-background-color:transparent");
            supliersButtonSupliers.setStyle("-fx-background-color:transparent");
        }
       else if(event.getSource()==supliersButtonSupliers){
            warehouseForm.setVisible(false);
            ordersForm.setVisible(false);
            supliersForm.setVisible(true);
            
            supliersButtonSupliers.setStyle("-fx-background-color:#ffffff55");
            warehouseButtonSupliers.setStyle("-fx-background-color:transparent");
            ordersButtonSupliers.setStyle("-fx-background-color:transparent");
        }
    }
    
}
