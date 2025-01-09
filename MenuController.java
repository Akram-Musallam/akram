/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.restaurant;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tables.*;

/**
 * FXML Controller class
 *
 * @author Alaa
 */
public class MenuController implements Initializable {
    
    @FXML
    private TableColumn<Dishes, String> dishesName;

    @FXML
    private TableColumn<Dishes, Double> dishesPrice;
    
    @FXML
    private TableColumn<Dishes, Integer> dishesPrepTime;

    @FXML
    private TableColumn<Dishes, String> dishesDescription;

    @FXML
    private TableView<Dishes> tableView;
    
    @FXML
    private Button deleteButton;
    
    @FXML
    private Button searchButton;
    
    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldPrice;
    
    
    private final ObservableList<Dishes> dishesListDishes = FXCollections.observableArrayList();
    
    @FXML
    public void ButtonBurger(ActionEvent event){
        loadDataDishes("Burger");
    }
    @FXML
    public void ButtonChicken(ActionEvent event){
        loadDataDishes("Grilled Chicken");
    }
    @FXML
    public void ButtonPizza(ActionEvent event){
        loadDataDishes("Pizza");
    }
    @FXML
    public void ButtonFish(ActionEvent event){
        loadDataDishes("Grilled Fish");
    }
    @FXML
    public void ButtonHummus(ActionEvent event){
        loadDataDishes("Hummus");
    }
    @FXML
    public void ButtonSamosa(ActionEvent event){
        loadDataDishes("Samosa");
    }
    @FXML
    public void ButtonPotatoes(ActionEvent event){
        loadDataDishes("Potatoes");
    }
    @FXML
    public void ButtonSalad(ActionEvent event){
        loadDataDishes("Cabbage Salad");
    }
    @FXML
    public void ButtonCola(ActionEvent event){
        loadDataDishes("Cola");
    }
    @FXML
    public void ButtonLemonade(ActionEvent event){
        loadDataDishes("Lemonade");
    }
    @FXML
    public void ButtonStrawberry(ActionEvent event){
        loadDataDishes("Strawberry Juice");
    }
    @FXML
    public void ButtonSprite(ActionEvent event){
        loadDataDishes("Sprite");
    }
    
    @FXML
    public void ButtonTea(ActionEvent event){
        loadDataDishes("Mint Tea");
    }
    @FXML
    public void ButtonKunafa(ActionEvent event){
        loadDataDishes("Kunafa");
    }
    @FXML
    public void ButtonBaklava(ActionEvent event){
        loadDataDishes("Baklava");
    }
    @FXML
    public void ButtonNamoura(ActionEvent event){
        loadDataDishes("Namoura");
    }
    @FXML
    public void ButtonQatayef(ActionEvent event){
        loadDataDishes("Qatayef");
    }
    
    private int orderId;
     void setOrderId(int orderId) {
    this.orderId = orderId;
    }
    
    
    @FXML
    public void loadDataDishes(String nameDishe){
        
        String conn = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String pass = "123456";
        
        try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
            String dishesQuery = "SELECT * FROM dishes WHERE name=?";
            try (PreparedStatement dishesStmt = connection.prepareStatement(dishesQuery)) {
                dishesStmt.setString(1,nameDishe);
                
                try (ResultSet rs = dishesStmt.executeQuery()) {
                    while (rs.next()) {
                        String name = rs.getString("name");
                        int prepTime = rs.getInt("preparation_time");
                        String description = rs.getString("description");
                        BigDecimal price = rs.getBigDecimal("price");
                        dishesListDishes.add(new Dishes(name, prepTime, description, price));
                    }
                }
            }
 
        
        dishesName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        dishesPrepTime.setCellValueFactory(new PropertyValueFactory<>("PreparationTime"));
        dishesDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        dishesPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableView.setItems(dishesListDishes);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        
    @FXML
    void searchButton(ActionEvent event) {
        String name = textFieldName.getText().trim();
        String price = textFieldPrice.getText().trim();

        ObservableList<Dishes> filteredList = FXCollections.observableArrayList();

        if (name.isEmpty() && price.isEmpty()) {
            tableView.setItems(dishesListDishes);
            return;
        }

        for (Dishes dish : dishesListDishes) {
            boolean matchesName = true;
            boolean matchesPrice = true;

            if (!name.isEmpty()) {
                matchesName = dish.getName().toLowerCase().contains(name.toLowerCase());
            }

            if (!price.isEmpty()) {
                try {
                    matchesPrice = dish.getPrice().compareTo(new BigDecimal(price)) == 0;
                } catch (NumberFormatException e) {
                    matchesPrice = false;
                }
            }

            if (matchesName && matchesPrice) {
                filteredList.add(dish);
            }
        }

        tableView.setItems(filteredList);
    }

    
    @FXML
    void deleteButton(ActionEvent event) {
        String name = textFieldName.getText().trim().toLowerCase();
        String price = textFieldPrice.getText().trim();

        Iterator<Dishes> iterator = dishesListDishes.iterator();

        while (iterator.hasNext()) {
            Dishes dish = iterator.next();

            boolean matchesName = true;
            if (!name.isEmpty()) {
                matchesName = dish.getName().toLowerCase().contains(name);
            }

            boolean matchesPrice = true;
            if (!price.isEmpty()) {
                try {
                    matchesPrice = dish.getPrice().compareTo(new BigDecimal(price)) == 0;
                } catch (NumberFormatException e) {
                    matchesPrice = false;
                }
            }

            if (matchesName && matchesPrice) {
                iterator.remove();
            }
        }

        tableView.setItems(dishesListDishes);
    }
    
    @FXML
    void insertButton(ActionEvent event) {
        
        if (orderId <= 0) {
            showAlert("Error", "Invalid Order ID. Please ensure order ID is set.", Alert.AlertType.ERROR);
            return;
        }
        
        if (dishesListDishes.isEmpty()) {
            showAlert("Error", "No dishes available to link with the order.", Alert.AlertType.ERROR);
            return;
        }

        String conn = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String pass = "123456";

        try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
            String insertQuery = "INSERT INTO order_dishes (order_id, name) VALUES (?, ?)";

            try (PreparedStatement stmt = connection.prepareStatement(insertQuery)) {
                for (Dishes dish : dishesListDishes) {
                    stmt.setInt(1, orderId); 
                    stmt.setString(2, dish.getName()); 
                    stmt.addBatch(); 
                }

                stmt.executeBatch();
                showAlert("Success", "Dishes linked to Order ID: " + orderId, Alert.AlertType.INFORMATION);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to insert data into order_dishes.", Alert.AlertType.ERROR);
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
