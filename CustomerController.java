package com.mycompany.restaurant;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.time.Clock.system;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import tables.*;
import net.sf.jasperreports.engine.fill.*;

public class CustomerController implements Initializable{
    
    @FXML
    private Button menuButtonCustomer;

    @FXML
    private Button Logout;

    @FXML
    private Button billsButtonCustomer;

    @FXML
    private Button deliveryButtonCustomer;

    @FXML
    private AnchorPane menuForm;

    @FXML
    private Button ordersButtonCustomer;

    @FXML
    private AnchorPane ordersForm;
    @FXML
    private AnchorPane alaa;

    @FXML
    private Button reserv_tabelButtonCustomer;

    @FXML
    private AnchorPane reserv_tabelForm;
    
    @FXML
    private Tab meals_Button;

    @FXML
    private TableColumn<Dishes, String> meals_Description;

    @FXML
    private TableColumn<Dishes, String> meals_Name;

    @FXML
    private TableColumn<Dishes, Double> meals_Price;

    @FXML
    private TableColumn<Dishes, Integer> meals_prepTime;
    @FXML
    private TableView<Dishes> tableView;
    
    @FXML
    private TableColumn<Dishes, String> appet_Description;

    @FXML
    private TableColumn<Dishes, String> appet_Name;

    @FXML
    private TableColumn<Dishes, Double> appet_Price;

    @FXML
    private TableColumn<Dishes, Integer> appet_prepTime;
    
    @FXML
    private TableView<Dishes> tableViewAppet;
    
    @FXML
    private TableColumn<Dishes, String> drinks_Description;

    @FXML
    private TableColumn<Dishes, String> drinks_Name;

    @FXML
    private TableColumn<Dishes, Double> drinks_Price;

    @FXML
    private TableColumn<Dishes, Integer> drinks_prepTime;
    
    @FXML
    private TableView<Dishes> tableViewDrinks;
    
     @FXML
    private TableColumn<Dishes, String> sweets_Description;

    @FXML
    private TableColumn<Dishes, String> sweets_Name;

    @FXML
    private TableColumn<Dishes, Double> sweets_Price;

    @FXML
    private TableColumn<Dishes, Integer> sweets_prepTime;

    @FXML
    private TableView<Dishes> tableViewSweets;
    
    @FXML
    private TextField textFieldMenuName;
    
    @FXML
    private TableColumn<Orders, String> order_Location;

    @FXML
    private TableColumn<Orders, Integer> order_OrderId;

    @FXML
    private TableColumn<Orders, String> order_Type;

    @FXML
    private TableColumn<Orders, String> order_payAmount;

    @FXML
    private TableColumn<Orders, Integer> order_prepTime;
    
    @FXML
    private TableColumn<Orders, String> order_Date;
    
    @FXML
    private TableView<Orders> tableViewOrders;
    
    public int customerId;
    
    @FXML
    private TextField textFieldDate;

    @FXML
    private TextField textFieldType;
    
    @FXML
    private TextField textFieldPayType;
    
    @FXML
    private TextField textFieldLocation;
    
    @FXML
    private TableView<Tables> tableViewTables;

    @FXML
    private TableColumn<Tables, Integer> tables_Capacity;


    @FXML
    private TableColumn<Tables, String> tables_Status;

    @FXML
    private TableColumn<Tables, Integer> tables_tableId;
    
    @FXML
    private TableColumn<Reservation, String> reservation_EndTime;

    @FXML
    private TableColumn<Reservation, Integer> reservation_NumPeople;

    @FXML
    private TableColumn<Reservation, String> reservation_Phone;

    @FXML
    private TableColumn<Reservation, Integer> reservation_ReservId;

    @FXML
    private TableColumn<Reservation, String> reservation_StartTime;
    
    @FXML
    private TableColumn<Reservation, String> reservation_Date;
    
    @FXML
    private TableView<Reservation> tableViewReservation;
    

    @FXML
    private TextField textFieldDateReserv;

    @FXML
    private TextField textFieldEndTimeReserv;

    @FXML
    private TextField textFieldNumPeopleReserv;
    
    @FXML
    private TextField textFieldPhoneNumReserv;

    @FXML
    private TextField textFieldTablesId;

    @FXML
    private TextField textFieldIDReserv;
    
    @FXML
    private TextField textFieldStartTimeReserv;


    public void setCustomerId(int customerId) {
        this.customerId = customerId;
        loadDataOrders();
        loadDataReservation();
    }
    
    public int getCustomerId(){
        return customerId;
    }
    
    @FXML
    void updateReservation(ActionEvent event) {
        String reservationId = textFieldIDReserv.getText().trim();
        String startTime = textFieldStartTimeReserv.getText().trim();
        String phoneNum = textFieldPhoneNumReserv.getText().trim();
        String numPeople = textFieldNumPeopleReserv.getText().trim();
        String endTime = textFieldEndTimeReserv.getText().trim();
        String date = textFieldDateReserv.getText().trim();

        if (reservationId.isEmpty() || startTime.isEmpty() || phoneNum.isEmpty() || numPeople.isEmpty() || endTime.isEmpty() || date.isEmpty()) {
            showAlert("Invalid Input", "Please fill in all fields.", Alert.AlertType.WARNING);
            return;
        }

        String conn = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String pass = "123456";

        try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
            String updateQuery = "UPDATE reservation SET start_time = ?, phone_number = ?, number_of_people = ?, end_time = ?, date = ? WHERE reservation_id = ? AND ssn = ?";

            try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                updateStmt.setString(1, startTime);
                updateStmt.setString(2, phoneNum);
                updateStmt.setInt(3, Integer.parseInt(numPeople));
                updateStmt.setString(4, endTime);
                updateStmt.setString(5, date);
                updateStmt.setInt(6, Integer.parseInt(reservationId));
                updateStmt.setInt(7, customerId);

                int rowsAffected = updateStmt.executeUpdate();

                if (rowsAffected > 0) {
                    showAlert("Success", "Reservation updated successfully.", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Failure", "No reservation found with the given ID and SSN.", Alert.AlertType.ERROR);
                }

                loadDataReservation();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Database Error", "An error occurred while updating the reservation.", Alert.AlertType.ERROR);
        }
    }

    
    @FXML
    void deleteReservation(ActionEvent event) {
        String deleteReserv = textFieldIDReserv.getText().trim();

        if (deleteReserv.isEmpty()) {
            showAlert("Invalid Input", "Please enter a valid Reservation ID.", Alert.AlertType.WARNING);
            return;
        }

        String conn = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String pass = "123456";

        try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
            // أولاً، نحصل على tables_id من جدول table_reservations باستخدام reservation_id
            String getTableIdQuery = "SELECT tables_id FROM table_reservations WHERE reservation_id = ?";
            try (PreparedStatement getTableIdStmt = connection.prepareStatement(getTableIdQuery)) {
                getTableIdStmt.setInt(1, Integer.parseInt(deleteReserv));
                ResultSet resultSet = getTableIdStmt.executeQuery();

                if (resultSet.next()) {
                    int tableId = resultSet.getInt("tables_id");

                    // الآن نقوم بتحديث حالة الطاولة إلى "Available"
                    String updateTableStatusQuery = "UPDATE tables SET status = 'Available' WHERE tables_id = ?";
                    try (PreparedStatement updateTableStmt = connection.prepareStatement(updateTableStatusQuery)) {
                        updateTableStmt.setInt(1, tableId);
                        updateTableStmt.executeUpdate();
                    }
                } else {
                    showAlert("Table Not Found", "No table found for this reservation.", Alert.AlertType.ERROR);
                    return;
                }

                // الآن نقوم بحذف الحجز من جدول reservation
                String deleteQuery = "DELETE FROM reservation WHERE reservation_id = ? AND ssn = ?";
                try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
                    deleteStmt.setInt(1, Integer.parseInt(deleteReserv));
                    deleteStmt.setInt(2, customerId);

                    int rowsAffected = deleteStmt.executeUpdate();

                    if (rowsAffected > 0) {
                        showAlert("Success", "Reservation deleted successfully.", Alert.AlertType.INFORMATION);
                    } else {
                        showAlert("Failure", "No reservation found with the given ID and SSN.", Alert.AlertType.ERROR);
                    }

                    loadDataReservation();
                    loadDataTables();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Database Error", "An error occurred while connecting to the database.", Alert.AlertType.ERROR);
        }
    }



    
    @FXML
    void searchReservation(ActionEvent event) {
        ReservationList.clear();
        String searchReserv = textFieldIDReserv.getText().trim();

        if (searchReserv.isEmpty()) {
            loadDataReservation();
            return;
        }

        String conn = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String pass = "123456";

        try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
            String searchQuery = "SELECT * FROM reservation WHERE reservation_id = ? AND ssn = ?";
            try (PreparedStatement searchStmt = connection.prepareStatement(searchQuery)) {
                searchStmt.setInt(1, Integer.parseInt(searchReserv));
                searchStmt.setInt(2, customerId);
                ResultSet resultSet = searchStmt.executeQuery();

                if (resultSet.next()) {
                    int reservationId = resultSet.getInt("reservation_id");
                    String startTime = resultSet.getString("start_time");
                    String endTime = resultSet.getString("end_time");
                    String date = resultSet.getString("date");
                    int numberPeople = resultSet.getInt("number_of_people");
                    String phoneNumber = resultSet.getString("Phone_number");

                    textFieldIDReserv.setText(String.valueOf(reservationId));
                    textFieldStartTimeReserv.setText(startTime);
                    textFieldEndTimeReserv.setText(endTime);
                    textFieldDateReserv.setText(date);
                    textFieldNumPeopleReserv.setText(String.valueOf(numberPeople));
                    textFieldPhoneNumReserv.setText(phoneNumber);

                    ReservationList.add(new Reservation(reservationId, startTime, endTime, date, numberPeople, phoneNumber));
                    reservation_ReservId.setCellValueFactory(new PropertyValueFactory<>("ReservationId"));
                    reservation_StartTime.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
                    reservation_EndTime.setCellValueFactory(new PropertyValueFactory<>("EndTime"));
                    reservation_Date.setCellValueFactory(new PropertyValueFactory<>("Date"));
                    reservation_NumPeople.setCellValueFactory(new PropertyValueFactory<>("NumberOfPeople"));
                    reservation_Phone.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));

                    tableViewReservation.setItems(ReservationList);
                } else {
                    showAlert("Not Found", "No reservation found with the given ID and SSN.", Alert.AlertType.ERROR);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Error", "An error occurred while searching for the reservation.", Alert.AlertType.ERROR);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Database Error", "An error occurred while connecting to the database.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void insertReservation(ActionEvent event) {
        String startTime = textFieldStartTimeReserv.getText().trim();
        String endTime = textFieldEndTimeReserv.getText().trim();
        String date = textFieldDateReserv.getText().trim();
        String numPeople = textFieldNumPeopleReserv.getText().trim();
        String phoneNumber = textFieldPhoneNumReserv.getText().trim();
        String tableId = textFieldTablesId.getText().trim();

        if (startTime.isEmpty() || endTime.isEmpty() || date.isEmpty() || numPeople.isEmpty() || phoneNumber.isEmpty() || tableId.isEmpty()) {
            showAlert("Invalid Input", "Please fill in all fields.", Alert.AlertType.WARNING);
            return;
        }

        int numberOfPeople = Integer.parseInt(numPeople);

        String conn = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String pass = "123456";

        try (Connection connection = DriverManager.getConnection(conn, user, pass)) {

            String checkTableQuery = "SELECT status, capacity FROM tables WHERE tables_id = ?";
            try (PreparedStatement checkTableStmt = connection.prepareStatement(checkTableQuery)) {
                checkTableStmt.setInt(1, Integer.parseInt(tableId));
                ResultSet resultSet = checkTableStmt.executeQuery();

                if (resultSet.next()) {
                    String status = resultSet.getString("status");
                    int capacity = resultSet.getInt("capacity");

                    if (status.equalsIgnoreCase("Occupied")) {
                        showAlert("Table Unavailable", "The table is currently occupied. Please choose another table.", Alert.AlertType.ERROR);
                        return;
                    }

                    if (numberOfPeople > capacity) {
                        showAlert("Capacity Exceeded", "The number of people exceeds the table's capacity. Please choose a table with sufficient capacity.", Alert.AlertType.ERROR);
                        return;
                    }
                } else {
                    showAlert("Table Not Found", "No table found with the given ID.", Alert.AlertType.ERROR);
                    return;
                }
            }

            String insertQuery = "INSERT INTO reservation (start_time, end_time, date, number_of_people, phone_number, ssn) VALUES (?, ?, ?, ?, ?, ?) RETURNING reservation_id";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                insertStmt.setString(1, startTime);
                insertStmt.setString(2, endTime);
                insertStmt.setString(3, date);
                insertStmt.setInt(4, numberOfPeople);
                insertStmt.setString(5, phoneNumber);
                insertStmt.setInt(6, customerId);

                ResultSet resultSet = insertStmt.executeQuery();

                if (resultSet.next()) {
                    int generatedReservationId = resultSet.getInt("reservation_id");

                    String insertTableReservationQuery = "INSERT INTO table_reservations (tables_id, reservation_id) VALUES (?, ?)";
                    try (PreparedStatement insertTableReservationStmt = connection.prepareStatement(insertTableReservationQuery)) {
                        insertTableReservationStmt.setInt(1, Integer.parseInt(tableId));
                        insertTableReservationStmt.setInt(2, generatedReservationId);
                        insertTableReservationStmt.executeUpdate();
                    }

                    String updateTableStatusQuery = "UPDATE tables SET status = 'Occupied' WHERE tables_id = ?";
                    try (PreparedStatement updateTableStmt = connection.prepareStatement(updateTableStatusQuery)) {
                        updateTableStmt.setInt(1, Integer.parseInt(tableId));
                        updateTableStmt.executeUpdate();
                    }

                    showAlert("Success", "Reservation added successfully with ID: " + generatedReservationId, Alert.AlertType.INFORMATION);
                    loadDataReservation();
                    loadDataTables();
                } else {
                    showAlert("Failure", "Failed to add reservation. Please try again.", Alert.AlertType.ERROR);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Database Error", "An error occurred while inserting the reservation.", Alert.AlertType.ERROR);
        }
    }



    
    private final ObservableList<Orders> OrdersList = FXCollections.observableArrayList();
    
    @FXML
    public void loadDataOrders(){
        OrdersList.clear();
        String conn = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String pass = "123456";
        
        try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
            String query = "SELECT * FROM orders WHERE ssn = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1,customerId);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int orderId = resultSet.getInt("order_id");
                    String type = resultSet.getString("type");
                    String payType = resultSet.getString("pay_type");
                    int prepTime = resultSet.getInt("preparation_time");
                    String location = resultSet.getString("location");
                    String date = resultSet.getString("date");
                    OrdersList.add(new Orders(orderId, type, payType, prepTime,location,date));
                }
                order_OrderId.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
                order_Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
                order_payAmount.setCellValueFactory(new PropertyValueFactory<>("PayType"));
                order_prepTime.setCellValueFactory(new PropertyValueFactory<>("PreparationTime"));
                order_Location.setCellValueFactory(new PropertyValueFactory<>("Location"));
                order_Date.setCellValueFactory(new PropertyValueFactory<>("Date"));       
                tableViewOrders.setItems(OrdersList);
                
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }catch (SQLException e) {
                e.printStackTrace();
        }
    }
    
    
    @FXML
    void searchMenuName(ActionEvent event) {
        String searchName = textFieldMenuName.getText().trim();

        if (searchName.isEmpty()) {
            loadDataMenu();
            return;
        }

        String conn = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String pass = "123456";

        try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
            String searchQuery = "SELECT * FROM dishes WHERE name ILIKE ?";
            try (PreparedStatement searchStmt = connection.prepareStatement(searchQuery)) {
                searchStmt.setString(1, "%" + searchName + "%");

                try (ResultSet rs = searchStmt.executeQuery()) {

                    boolean dishFound = false;
                    
                    while (rs.next()) {
                        dishFound = true;
                        String menuName = rs.getString("name_menu");
                        String name = rs.getString("name");
                        int prepTime = rs.getInt("preparation_time");
                        String description = rs.getString("description");
                        BigDecimal price = rs.getBigDecimal("price");
                        
                        if (menuName.equalsIgnoreCase("Meals")) {
                            dishesListMeals.clear();
                            dishesListMeals.add(new Dishes(name, prepTime, description, price));
                        } else if (menuName.equalsIgnoreCase("Appetizers")) {
                            dishesListAppet.clear();
                            dishesListAppet.add(new Dishes(name, prepTime, description, price));
                        } else if (menuName.equalsIgnoreCase("Drinks")) {
                            dishesListDrinks.clear();
                            dishesListDrinks.add(new Dishes(name, prepTime, description, price));
                        } else if (menuName.equalsIgnoreCase("Sweets")) {
                            dishesListSweets.clear();
                            dishesListSweets.add(new Dishes(name, prepTime, description, price));
                        }
                    }

                    tableView.setItems(dishesListMeals);
                    tableViewAppet.setItems(dishesListAppet);
                    tableViewDrinks.setItems(dishesListDrinks);
                    tableViewSweets.setItems(dishesListSweets);

                    if (!dishFound) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Dish Not Found");
                        alert.setHeaderText(null);
                        alert.setContentText("The dish '" + searchName + "' was not found in the menu.");
                        alert.showAndWait();
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    
    
    private final ObservableList<Dishes> dishesListMeals = FXCollections.observableArrayList();
    private final ObservableList<Dishes> dishesListAppet = FXCollections.observableArrayList();
    private final ObservableList<Dishes> dishesListDrinks = FXCollections.observableArrayList();
    private final ObservableList<Dishes> dishesListSweets = FXCollections.observableArrayList();
    
    @FXML
    public void loadDataMenu(){
        
        dishesListMeals.clear();
        dishesListAppet.clear();
        dishesListDrinks.clear();
        dishesListSweets.clear();
                    
        String conn = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String pass = "123456";
      try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
        String mealsQuery = "SELECT * FROM dishes WHERE name_menu = ?";
        try (PreparedStatement mealsStmt = connection.prepareStatement(mealsQuery)) {
            mealsStmt.setString(1, "Meals");
            try (ResultSet rs = mealsStmt.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    int prepTime = rs.getInt("preparation_time");
                    String description = rs.getString("description");
                    BigDecimal price = rs.getBigDecimal("price");
                    dishesListMeals.add(new Dishes(name, prepTime, description, price));
                }
            }
        }
 
        String appetizersQuery = "SELECT * FROM dishes WHERE name_menu = ?";
        try (PreparedStatement appetizersStmt = connection.prepareStatement(appetizersQuery)) {
            appetizersStmt.setString(1, "Appetizers");
            try (ResultSet rs = appetizersStmt.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    int prepTime = rs.getInt("preparation_time");
                    String description = rs.getString("description");
                    BigDecimal price = rs.getBigDecimal("price");
                    dishesListAppet.add(new Dishes(name, prepTime, description, price));
                }
            }
        }
        
        String drinksQuery = "SELECT * FROM dishes WHERE name_menu = ?";
        try (PreparedStatement drinksStmt = connection.prepareStatement(drinksQuery)) {
            drinksStmt.setString(1, "Drinks");
            try (ResultSet rs = drinksStmt.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    int prepTime = rs.getInt("preparation_time");
                    String description = rs.getString("description");
                    BigDecimal price = rs.getBigDecimal("price");
                    dishesListDrinks.add(new Dishes(name, prepTime, description, price));
                }
            }
        }
        
        String sweetsQuery = "SELECT * FROM dishes WHERE name_menu = ?";
        try (PreparedStatement sweetsStmt = connection.prepareStatement(sweetsQuery)) {
            sweetsStmt.setString(1, "Sweets");
            try (ResultSet rs = sweetsStmt.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    int prepTime = rs.getInt("preparation_time");
                    String description = rs.getString("description");
                    BigDecimal price = rs.getBigDecimal("price");
                    dishesListSweets.add(new Dishes(name, prepTime, description, price));
                }
            }
        }
        
        meals_Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        meals_prepTime.setCellValueFactory(new PropertyValueFactory<>("PreparationTime"));
        meals_Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        meals_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableView.setItems(dishesListMeals);

        appet_Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        appet_prepTime.setCellValueFactory(new PropertyValueFactory<>("PreparationTime"));
        appet_Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        appet_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableViewAppet.setItems(dishesListAppet);
        
        drinks_Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        drinks_prepTime.setCellValueFactory(new PropertyValueFactory<>("PreparationTime"));
        drinks_Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        drinks_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableViewDrinks.setItems(dishesListDrinks);
        
        sweets_Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        sweets_prepTime.setCellValueFactory(new PropertyValueFactory<>("PreparationTime"));
        sweets_Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        sweets_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableViewSweets.setItems(dishesListSweets);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void logout(ActionEvent event){
        Alert alert=new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> option=alert.showAndWait();
        try{
            if(option.isPresent() && option.get().equals(ButtonType.OK)){
                Stage stage = (Stage) Logout.getScene().getWindow();
                stage.close();
                Parent root=FXMLLoader.load(getClass().getResource("SignIn.fxml"));
                Stage newstage=new Stage();
                Scene scene=new Scene(root);
                newstage.setScene(scene);
                newstage.show();
            }
        }catch(Exception ex){ex.printStackTrace();}
    }
    @FXML
    public void switchForm(ActionEvent event){
        if(event.getSource()==menuButtonCustomer){
            menuForm.setVisible(true);
            ordersForm.setVisible(false);
            reserv_tabelForm.setVisible(false);
            alaa.setVisible(false);
            
            menuButtonCustomer.setStyle("-fx-background-color:#ffffff55");
            ordersButtonCustomer.setStyle("-fx-background-color:transparent");
            reserv_tabelButtonCustomer.setStyle("-fx-background-color:transparent");
        }
       else if(event.getSource()==ordersButtonCustomer){
            menuForm.setVisible(false);
            ordersForm.setVisible(true);
            reserv_tabelForm.setVisible(false);
            alaa.setVisible(false);
                    
            menuButtonCustomer.setStyle("-fx-background-color:transparent");
            ordersButtonCustomer.setStyle("-fx-background-color:#ffffff55");
            reserv_tabelButtonCustomer.setStyle("-fx-background-color:transparent");
            billsButtonCustomer.setStyle("-fx-background-color:transparent");
        }
       else if(event.getSource()==reserv_tabelButtonCustomer){
            menuForm.setVisible(false);
            ordersForm.setVisible(false);
            reserv_tabelForm.setVisible(true);
            alaa.setVisible(false);
            
            
            menuButtonCustomer.setStyle("-fx-background-color:transparent");
            ordersButtonCustomer.setStyle("-fx-background-color:transparent");
            reserv_tabelButtonCustomer.setStyle("-fx-background-color:#ffffff55");
            billsButtonCustomer.setStyle("-fx-background-color:transparent");
        }
        
        else if(event.getSource()==billsButtonCustomer){
            menuForm.setVisible(false);
            ordersForm.setVisible(false);
            reserv_tabelForm.setVisible(false);
            alaa.setVisible(true);
            
            menuButtonCustomer.setStyle("-fx-background-color:transparent");
            ordersButtonCustomer.setStyle("-fx-background-color:transparent");
            reserv_tabelButtonCustomer.setStyle("-fx-background-color:transparent");
            billsButtonCustomer.setStyle("-fx-background-color:#ffffff55");
        }
    }
    
//    @FXML
//    void ButtonOrderClear(ActionEvent event) throws SQLException {
//        String conn = "jdbc:postgresql://localhost:5432/postgres";
//        String user = "postgres";
//        String pass = "alaa.taha.2004";
//
//        try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
//            String orderId = textFieldOrderId.getText();
//
//            if (orderId == null || orderId.isEmpty()) {
//                showAlert("Invalid Input", "Please enter a valid ID.", Alert.AlertType.WARNING);
//                return;
//            }
//
//            try {
//                int id = Integer.parseInt(orderId);
//                String query = "DELETE FROM orders WHERE order_id = ? AND ssn = ?";
//
//                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//                    preparedStatement.setInt(1, id);
//                    preparedStatement.setInt(2, customerId);
//
//                    int rowsAffected = preparedStatement.executeUpdate();
//
//                    if (rowsAffected > 0) {
//                        showAlert("Successful", "The request has been deleted.", Alert.AlertType.INFORMATION);
//                        loadDataOrders();
//                    } else {
//                        showAlert("Not Found", "The order ID is not found.", Alert.AlertType.ERROR);
//                    }
//                }
//            } catch (NumberFormatException e) {
//                showAlert("Invalid Input", "Please enter a numeric ID.", Alert.AlertType.ERROR);
//            }
//        } catch (SQLException e) {
//            showAlert("Database Error", "An error occurred while connecting to the database.", Alert.AlertType.ERROR);
//            e.printStackTrace();
//        }
//    }
    @FXML
    TextField textFieldOrderId1;
@FXML
void ButtonOrderSearch(ActionEvent event) {
    OrdersList.clear();
    String orderid = textFieldOrderId1.getText().trim();  // Assuming textFieldType is for the order ID
    String date = textFieldDate.getText().trim();    // date field is not used anymore in search
    
    String conn = "jdbc:postgresql://localhost:5432/postgres";
    String user = "postgres";
    String pass = "123456";

    try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
        if (!orderid.isEmpty()) {
            // Search only by order_id
            String searchOrderIdQuery = "SELECT * FROM Orders WHERE order_id = ? AND ssn = ?";
            try (PreparedStatement searchOrderIdStmt = connection.prepareStatement(searchOrderIdQuery)) {
                searchOrderIdStmt.setInt(1, Integer.parseInt(orderid));  // Assuming order_id is numeric
                searchOrderIdStmt.setInt(2, customerId);

                try (ResultSet rs = searchOrderIdStmt.executeQuery()) {
                    while (rs.next()) {
                        int orderId = rs.getInt("order_id");
                        String type = rs.getString("type");
                        String payType = rs.getString("pay_type");
                        int prepTime = rs.getInt("preparation_time");
                        String location = rs.getString("location");
                        String date1 = rs.getString("date");
                        OrdersList.add(new Orders(orderId, type, payType, prepTime, location, date1));
                    }
                }
                tableViewOrders.setItems(OrdersList);
            }
        } else {
            // If orderid is empty, you can decide whether to load all data or handle it in a different way
            loadDataOrders();  // This function can load all orders or handle the case when no search term is entered
        }
    } catch (SQLException e) {
        e.printStackTrace(); // You should handle the exception better in a production environment
    }
}

    
    @FXML
void ButtonOrderClear(ActionEvent event) {
    String orderid = textFieldOrderId1.getText().trim();  // Assuming textFieldOrderId1 is for the order ID
    
    String conn = "jdbc:postgresql://localhost:5432/postgres";
    String user = "postgres";
    String pass = "123456";

    if (!orderid.isEmpty()) {
        try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
            // Delete the order by order_id
            String deleteOrderQuery = "DELETE FROM Orders WHERE order_id = ? AND ssn = ?";
            try (PreparedStatement deleteOrderStmt = connection.prepareStatement(deleteOrderQuery)) {
                deleteOrderStmt.setInt(1, Integer.parseInt(orderid));  // Assuming order_id is numeric
                deleteOrderStmt.setInt(2, customerId);

                int affectedRows = deleteOrderStmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Order deleted successfully.");
                    // You can also reload or refresh the orders list after deletion
                    loadDataOrders(); // Reload data after deletion
                } else {
                    System.out.println("Order not found or not deleted.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in a production environment
        }
    } else {
        System.out.println("Order ID cannot be empty.");
    }
}

    
    @FXML
    void InsertButtonOrders(ActionEvent event) {
        String type = textFieldType.getText().trim();
        String date = textFieldDate.getText().trim();
        String payType = textFieldPayType.getText().trim();
        String location = textFieldLocation.getText().trim();
        int orderId = 0;

        if (type.isEmpty() || date.isEmpty() || payType.isEmpty() || location.isEmpty()) {
            showAlert("Error", "All fields must be filled.", Alert.AlertType.ERROR);
            return;
        }

        String conn = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String pass = "123456";

        try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
            String insertQuery = "INSERT INTO orders (type, pay_type, location, date,preparation_time,ssn) VALUES (?, ?, ?, ?, ? ,?) RETURNING order_id";

            try (PreparedStatement stmt = connection.prepareStatement(insertQuery)) {
                stmt.setString(1, type);
                stmt.setString(2, payType);
                stmt.setString(3, location);
                stmt.setString(4, date);
                stmt.setInt(5, 45);
                stmt.setInt(6, customerId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        orderId = rs.getInt("order_id");
                        showAlert("Success", "Order added successfully! Order ID: " + orderId, Alert.AlertType.INFORMATION);
                    }
                }

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
                Parent root = fxmlLoader.load();
                MenuController menuController = fxmlLoader.getController();
                menuController.setOrderId(orderId);
                Stage stage = new Stage();
                stage.setTitle("Menu");
                stage.setScene(new Scene(root));
                stage.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to insert data into orders table.", Alert.AlertType.ERROR);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open the Menu screen.", Alert.AlertType.ERROR);
        }
    }
    
    private final ObservableList<Tables> TablesList = FXCollections.observableArrayList();
    
    @FXML
    public void loadDataTables(){
        TablesList.clear();
        String conn = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String pass = "123456";
        
        try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
            String query = "SELECT * FROM tables";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int tablesId = resultSet.getInt("tables_id");
                    int capacity = resultSet.getInt("capacity");
                    String status = resultSet.getString("status");
                    TablesList.add(new Tables(tablesId, capacity, status));
                }
                tables_tableId.setCellValueFactory(new PropertyValueFactory<>("TablesId"));
                tables_Capacity.setCellValueFactory(new PropertyValueFactory<>("Capacity"));
                tables_Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
                tableViewTables.setItems(TablesList);
                
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }catch (SQLException e) {
                e.printStackTrace();
        }
    }
    
    private final ObservableList<Reservation> ReservationList = FXCollections.observableArrayList();
    
    @FXML
    public void loadDataReservation(){
        ReservationList.clear();
        String conn = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String pass = "123456";
        
        try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
            String query = "SELECT * FROM reservation WHERE ssn = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, customerId);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int reservationId = resultSet.getInt("reservation_id");
                    String startTime = resultSet.getString("start_time");
                    String endTime = resultSet.getString("end_time");
                    String date = resultSet.getString("date");
                    int numberPeople = resultSet.getInt("number_of_people");
                    String Phone_number = resultSet.getString("Phone_number");
                    ReservationList.add(new Reservation(reservationId, startTime, endTime, date ,numberPeople,Phone_number));
                }
                reservation_ReservId.setCellValueFactory(new PropertyValueFactory<>("ReservationId"));
                reservation_StartTime.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
                reservation_EndTime.setCellValueFactory(new PropertyValueFactory<>("EndTime"));
                reservation_Date.setCellValueFactory(new PropertyValueFactory<>("Date"));
                reservation_NumPeople.setCellValueFactory(new PropertyValueFactory<>("NumberOfPeople"));
                reservation_Phone.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
                
                tableViewReservation.setItems(ReservationList);
                
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }catch (SQLException e) {
                e.printStackTrace();
        }
    }
    
    @FXML
    void searchTables(ActionEvent event) {
        TablesList.clear();
        String searchTables = textFieldTablesId.getText().trim();
        
        if (searchTables.isEmpty()) {
            loadDataTables();
            return;
        }
            
        String conn = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String pass = "123456";

        try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
            String searchQuery = "SELECT * FROM tables WHERE tables_id = ?";
            try (PreparedStatement searchStmt = connection.prepareStatement(searchQuery)) {
                searchStmt.setInt(1, Integer.parseInt(searchTables));
                ResultSet resultSet = searchStmt.executeQuery();
                while (resultSet.next()) {
                    int tablesId = resultSet.getInt("tables_id");
                    int capacity = resultSet.getInt("capacity");
                    String status = resultSet.getString("status");
                    TablesList.add(new Tables(tablesId, capacity, status));
                }
                tables_tableId.setCellValueFactory(new PropertyValueFactory<>("TablesId"));
                tables_Capacity.setCellValueFactory(new PropertyValueFactory<>("Capacity"));
                tables_Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
                tableViewTables.setItems(TablesList);

                
            }catch(Exception ex){ex.printStackTrace();}
        }catch(Exception ex){ex.printStackTrace();}
    }
    
    @FXML
    void buttonBills(ActionEvent event){
        try{
            InputStream ins = new FileInputStream(new File("C:/Users/Alaa/Documents/NetBeansProjects/Restaurant/Cherry.jrxml"));
            DriverManager.registerDriver(new org.postgresql.Driver());
            String conn = "jdbc:postgresql://localhost:5432/postgres";
            String user = "postgres";
            String pass = "123456";
            Connection connection = DriverManager.getConnection(conn, user, pass);
            
                JasperDesign jd=JRXmlLoader.load(ins);
                
                JasperReport jr=JasperCompileManager.compileReport(jd);
                
                JasperPrint jp = JasperFillManager.fillReport(jr, null,connection);
               
            
                //Map<String, Object> parameters = new HashMap<>();
                //parameters.put("customerId", customerId);
                
                 JasperViewer.viewReport(jp, false);
                
            }catch(Exception ex){}
            
                // TODO add your handling code here:
      
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
        loadDataTables();
        loadDataReservation();
        loadDataMenu();
    } 
}