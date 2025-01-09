/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.restaurant;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import tables.Orders;
import tables.Tables;
import tables.Waiter;

/**
 * FXML Controller class
 *
 * @author Alaa
 */
public class WaiterController implements Initializable {

    /**
     * Initializes the controller class.
     */
   
    
    @FXML
    private Button Logout;
    
    @FXML
    private Button ordersButtonWaiter;

    @FXML
    private AnchorPane ordersForm;

    @FXML
    private Button tabelButtonWaiter;

    @FXML
    private AnchorPane tabelForm;
    
    @FXML
    private TableColumn<Orders, String> Order_preparationTime;

    @FXML
    private TableColumn<Orders, String> Orders_Location;

    @FXML
    private TableColumn<Orders, String> Orders_Type;

    @FXML
    private TableColumn<Orders, String> Orders_date;

    @FXML
    private TableColumn<Orders, Integer> Orders_orderId;

    @FXML
    private TableColumn<Orders, String> Orders_paymentType;
    
    @FXML
    private TableView<Orders> tableView;
    
     @FXML
    private TextField textFieldDate;

    @FXML
    private TextField textFieldOrderId;

    @FXML
    private TextField textFieldLocation;

    @FXML
    private TextField textFieldPaymentType;

    @FXML
    private TextField textFieldType;
    
     public int WaiterId;
    
   ObservableList<Orders> OrdersList = FXCollections.observableArrayList();
   
   @FXML
    private TableColumn<Tables, String> Tabel__Status;

    @FXML
    private TableColumn<Tables, Integer> Table_Capacity;

    @FXML
    private TableColumn<Tables, Integer> Table_tableId;
    
    @FXML
    private TableView<Tables> tableViewTabel;
    @FXML
    private TextField textFieldStatus;

    @FXML
    private TextField textFieldTabelId;

     @FXML
    private TextField textFieldCapacity;
     
     ObservableList<Tables> TablesList = FXCollections.observableArrayList();
     
     
     
             
     
     

    @FXML 
    void loadDataTables(){
        TablesList.clear();
        String conn = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String pass = "123456";

        try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
            // استعلام لجلب الطلبات الخاصة بـ Waiter معين
            String query = "SELECT o.* FROM tables o " +
                           "JOIN waitertables mo ON o.tables_id = mo.tables_id " +
                           "WHERE mo.ssn = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, WaiterId);  // waiterId هو المعرف الخاص بالـ Waiter الذي ترغب في استعراض طلباته
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int tablesId = resultSet.getInt("tables_id");
                    int capacity = resultSet.getInt("capacity");
                    String status = resultSet.getString("status");
                    TablesList.add(new Tables(tablesId, capacity, status));
                }

                // تعيين البيانات لكل عمود في الـ TableView
                Table_tableId.setCellValueFactory(new PropertyValueFactory<>("TablesId"));
                Table_Capacity.setCellValueFactory(new PropertyValueFactory<>("Capacity"));
                Tabel__Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
                tableViewTabel.setItems(TablesList);

                // تعيين البيانات في الـ TableView
                tableViewTabel.setItems(TablesList);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
void insertButtonTables(ActionEvent event) {
    String conn = "jdbc:postgresql://localhost:5432/postgres";
    String user = "postgres";
    String pass = "123456";

    String capacity = textFieldCapacity.getText().trim();
    String status = textFieldStatus.getText().trim();

    try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
        // الاستعلام لإدخال البيانات في جدول tables وإرجاع الـ table_id
        String queryInsertTable = "INSERT INTO tables (capacity, status) VALUES (?, ?) RETURNING tables_id";
        
        try (PreparedStatement stmt = connection.prepareStatement(queryInsertTable)) {
            stmt.setInt(1, Integer.parseInt(capacity)); // تعيين القيمة لـ capacity
            stmt.setString(2, status);   // تعيين القيمة لـ status

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    int tableId = resultSet.getInt("tables_id");  // الحصول على القيمة المرجعة

                    System.out.println("Inserted table with ID: " + tableId); // طباعة الـ table_id
                    
                    // بعد إضافة الـ table، الآن نقوم بإضافة entry في جدول waitertables
                    String queryInsertWaiterTable = "INSERT INTO waitertables (tables_id, ssn) VALUES (?, ?)";
                    try (PreparedStatement stmtWaiterTable = connection.prepareStatement(queryInsertWaiterTable)) {
                        stmtWaiterTable.setInt(1, tableId);   // تعيين القيمة لـ tables_id
                        stmtWaiterTable.setInt(2, WaiterId);  // تعيين القيمة لـ waiter_id (موجود في المتغير waiterId)

                        stmtWaiterTable.executeUpdate();  // تنفيذ الاستعلام لإضافة البيانات في جدول waitertables
                        System.out.println("Inserted into waitertables with table_id: " + tableId + " and waiter_id: " + WaiterId);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        showAlert("Error", "Failed to insert data into waitertables.", Alert.AlertType.ERROR);
                    }
                }
                loadDataTables();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to retrieve inserted table ID.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to insert data into tables.", Alert.AlertType.ERROR);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        showAlert("Database Connection Error", "An error occurred while connecting to the database.", Alert.AlertType.ERROR);
    }
}

    @FXML
void clearButtonTables(ActionEvent event) {
    String tabelId = textFieldTabelId.getText().trim();

    
    if (tabelId.isEmpty()) {
        loadDataTables();
        return;
    }

    String conn = "jdbc:postgresql://localhost:5432/postgres";
    String user = "postgres";
    String pass = "123456";

    try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
        // استعلام حذف من جدول orders بناءً على orderId و ssnWaiter (WaiterId) من جدول managerorders
        String deleteQuery = "DELETE FROM Tables o " +
                             "USING waitertables mo " +
                             "WHERE o.tables_id = mo.tables_id " +
                             "AND mo.ssn = ? " + // matching the waiter id
                             "AND o.tables_id = ?"; // matching the order id

        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
            deleteStmt.setInt(1, WaiterId);
            deleteStmt.setInt(2, Integer.parseInt(tabelId));

            int rowsAffected = deleteStmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Success", "Tables deleted successfully.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Failure", "No matching order found for the given Order ID and Waiter ID.", Alert.AlertType.ERROR);
            }
        }
        loadDataTables();
    } catch (SQLException ex) {
        ex.printStackTrace();
        showAlert("Database Error", "An error occurred while deleting the table.", Alert.AlertType.ERROR);
    }
}



    @FXML
void searchButtonTables(ActionEvent event) {
     ObservableList<Tables> tablesList = FXCollections.observableArrayList(); // قائمة لتخزين النتائج

    String tableId = textFieldTabelId.getText().trim(); // قراءة بيانات الـ order_id

    if (tableId.isEmpty()) {
        loadDataTables();
    }

    String conn = "jdbc:postgresql://localhost:5432/postgres";
    String user = "postgres";
    String pass = "123456";

    // الاستعلام مع دمج الشروط
    String query = "SELECT o.* FROM tables o " +
                   "JOIN waitertables mo ON o.tables_id = mo.tables_id " +
                   "WHERE mo.ssn = ? " +
                   "AND o.tables_id = ?"; // استعلام يعتمد على WaiterId و order_id

    try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, WaiterId);  // تعيين WaiterId
            stmt.setInt(2, Integer.parseInt(tableId));   // تعيين order_id

            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    int tablesId = resultSet.getInt("tables_id");
                    int capacity = resultSet.getInt("capacity");
                    String status = resultSet.getString("status");
                    tablesList.add(new Tables(tablesId, capacity, status));
                }
                // ربط البيانات مع الجدول

                // إعداد الأعمدة في الـ TableView
                Table_tableId.setCellValueFactory(new PropertyValueFactory<>("TablesId"));
                Table_Capacity.setCellValueFactory(new PropertyValueFactory<>("Capacity"));
                Tabel__Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
                tableViewTabel.setItems(tablesList);

                // تعيين البيانات في الـ TableView
                tableViewTabel.setItems(tablesList);
                if (tablesList.isEmpty()) {
                    showAlert("No Results", "No table found with the given criteria.", Alert.AlertType.INFORMATION);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                showAlert("Error", "An error occurred while fetching the table.", Alert.AlertType.ERROR);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Database Error", "An error occurred while executing the query.", Alert.AlertType.ERROR);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        showAlert("Connection Error", "An error occurred while connecting to the database.", Alert.AlertType.ERROR);
    }
}

@FXML
void clearButton(ActionEvent event) {
    String orderId = textFieldOrderId.getText().trim();

    
    if (orderId.isEmpty()) {
        loadDataOrders();
        return;
    }

    String conn = "jdbc:postgresql://localhost:5432/postgres";
    String user = "postgres";
    String pass = "123456";

    try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
        // استعلام حذف من جدول orders بناءً على orderId و ssnWaiter (WaiterId) من جدول managerorders
        String deleteQuery = "DELETE FROM orders o " +
                             "USING managerorders mo " +
                             "WHERE o.order_id = mo.order_id " +
                             "AND mo.ssn = ? " + // matching the waiter id
                             "AND o.order_id = ?"; // matching the order id

        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
            deleteStmt.setInt(1, WaiterId);
            deleteStmt.setInt(2, Integer.parseInt(orderId));

            int rowsAffected = deleteStmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Success", "Tables deleted successfully.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Failure", "No matching order found for the given Tables ID and Waiter ID.", Alert.AlertType.ERROR);
            }
        }
        loadDataOrders();
    } catch (SQLException ex) {
        ex.printStackTrace();
        showAlert("Database Error", "An error occurred while deleting the Tables.", Alert.AlertType.ERROR);
    }
}

@FXML
void updateButtonTables(ActionEvent event) {
    String tabelId = textFieldTabelId.getText().trim();
    if (tabelId.isEmpty()) {
        loadDataTables();
        return;
    }

    String conn = "jdbc:postgresql://localhost:5432/postgres";
    String user = "postgres";
    String pass = "123456";
    
    try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
        // استعلام التحديث
        String updateQuery = "UPDATE tables " +
                             "SET capacity = ?, status = ? " +  // بدون الاسم المستعار
                             "FROM waitertables mo " +
                             "WHERE tables.tables_id = mo.tables_id " +
                             "AND mo.ssn = ? " +  // تطابق ssn مع WaiterId
                             "AND tables.tables_id = ?"; 

        try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
            // تحديد القيم الجديدة التي ستُحدّث
            updateStmt.setInt(1, Integer.parseInt(textFieldCapacity.getText().trim()));  // قيمة capacity الجديدة
            updateStmt.setString(2, textFieldStatus.getText().trim());  // قيمة status الجديدة
            updateStmt.setInt(3, WaiterId);  // تطابق ssn مع WaiterId
            updateStmt.setInt(4, Integer.parseInt(tabelId));  // تطابق tables_id مع tabelId

            int rowsAffected = updateStmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Success", "Tables updated successfully.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Failure", "No matching tables found for the given Table ID and Waiter ID.", Alert.AlertType.ERROR);
            }
        }
        loadDataTables(); // إعادة تحميل البيانات بعد التحديث
    } catch (SQLException ex) {
        ex.printStackTrace();
        showAlert("Database Error", "An error occurred while updating the table.", Alert.AlertType.ERROR);
    }
}


@FXML
void searchButton(ActionEvent event) {
    ObservableList<Orders> ordersList = FXCollections.observableArrayList(); // قائمة لتخزين النتائج

    String orderId = textFieldOrderId.getText().trim(); // قراءة بيانات الـ order_id

    if (orderId.isEmpty()) {
        loadDataOrders();
    }

    String conn = "jdbc:postgresql://localhost:5432/postgres";
    String user = "postgres";
    String pass = "123456";

    // الاستعلام مع دمج الشروط
    String query = "SELECT o.* FROM orders o " +
                   "JOIN managerorders mo ON o.order_id = mo.order_id " +
                   "WHERE mo.ssn = ? " +
                   "AND o.order_id = ?"; // استعلام يعتمد على WaiterId و order_id

    try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, WaiterId);  // تعيين WaiterId
            stmt.setInt(2, Integer.parseInt(orderId));   // تعيين order_id

            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    int o1rderId = resultSet.getInt("order_id");
                    String type = resultSet.getString("type");
                    String payType = resultSet.getString("pay_type");
                    int prepTime = resultSet.getInt("preparation_time");
                    String location = resultSet.getString("location");
                    String date = resultSet.getString("date");
                    ordersList.add(new Orders(o1rderId, type, payType, prepTime,location,date));
                }

                // ربط البيانات مع الجدول
                tableView.setItems(ordersList);

                // إعداد الأعمدة في الـ TableView
                Orders_orderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
                Order_preparationTime.setCellValueFactory(new PropertyValueFactory<>("preparationTime"));
                Orders_Location.setCellValueFactory(new PropertyValueFactory<>("location"));
                Orders_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
                Orders_date.setCellValueFactory(new PropertyValueFactory<>("date"));
                Orders_paymentType.setCellValueFactory(new PropertyValueFactory<>("paymentType"));

                if (ordersList.isEmpty()) {
                    showAlert("No Results", "No orders found with the given criteria.", Alert.AlertType.INFORMATION);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                showAlert("Error", "An error occurred while fetching the orders.", Alert.AlertType.ERROR);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Database Error", "An error occurred while executing the query.", Alert.AlertType.ERROR);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        showAlert("Connection Error", "An error occurred while connecting to the database.", Alert.AlertType.ERROR);
    }
}



   

/*
    @FXML
    void updatButton(ActionEvent event) {
        String tabelId = textFieldTabelId.getText().trim();
    if (tabelId.isEmpty()) {
        loadDataTables();
        return;
    }

    String conn = "jdbc:postgresql://localhost:5432/postgres";
    String user = "postgres";
    String pass = "alaa.taha.2004";
    
    try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
        
        String updateQuery = "UPDATE orders " +
                             "SET  = ?, status = ? " +  // بدون الاسم المستعار
                             "FROM waitertables mo " +
                             "WHERE tables.tables_id = mo.tables_id " +
                             "AND mo.ssn = ? " +  // تطابق ssn مع WaiterId
                             "AND tables.tables_id = ?"; 

        try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
            // تحديد القيم الجديدة التي ستُحدّث
            updateStmt.setInt(1, Integer.parseInt(textFieldCapacity.getText().trim()));  // قيمة capacity الجديدة
            updateStmt.setString(2, textFieldStatus.getText().trim());  // قيمة status الجديدة
            updateStmt.setInt(3, WaiterId);  // تطابق ssn مع WaiterId
            updateStmt.setInt(4, Integer.parseInt(tabelId));  // تطابق tables_id مع tabelId

            int rowsAffected = updateStmt.executeUpdate();

            if (rowsAffected > 0) {
                showAlert("Success", "Tables updated successfully.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Failure", "No matching tables found for the given Table ID and Waiter ID.", Alert.AlertType.ERROR);
            }
        }
        loadDataTables(); // إعادة تحميل البيانات بعد التحديث
    } catch (SQLException ex) {
        ex.printStackTrace();
        showAlert("Database Error", "An error occurred while updating the table.", Alert.AlertType.ERROR);
    }
    }
    */

@FXML
private TextField textFieldSsn;
  @FXML
    void InsertButtonOrders(ActionEvent event) throws IOException {
        CustomerController customer=new CustomerController();
     String customerId=textFieldSsn.getText().trim();
     
        String type = textFieldType.getText().trim();
        String date = textFieldDate.getText().trim();
        String payType = textFieldPaymentType.getText().trim();
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
                stmt.setInt(6, Integer.parseInt(customerId));

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
    
   
    
    
    
   @FXML
    void loadDataOrders() {

        OrdersList.clear();
        String conn = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String pass = "123456";

        try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
            // استعلام لجلب الطلبات الخاصة بـ Waiter معين
            String query = "SELECT o.* FROM orders o " +
                           "JOIN managerorders mo ON o.order_id = mo.order_id " +
                           "WHERE mo.ssn = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, WaiterId);  // waiterId هو المعرف الخاص بالـ Waiter الذي ترغب في استعراض طلباته
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

                // تعيين البيانات لكل عمود في الـ TableView
                Orders_orderId.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
                Orders_Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
                Orders_paymentType.setCellValueFactory(new PropertyValueFactory<>("PayType"));
                Order_preparationTime.setCellValueFactory(new PropertyValueFactory<>("PreparationTime"));
                Orders_Location.setCellValueFactory(new PropertyValueFactory<>("Location"));
                Orders_date.setCellValueFactory(new PropertyValueFactory<>("Date"));

                // تعيين البيانات في الـ TableView
                tableView.setItems(OrdersList);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    
    @FXML
    public void logout(ActionEvent event){
        
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
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
    void switchForm(ActionEvent event) {
        if(event.getSource()==ordersButtonWaiter){
            ordersForm.setVisible(true);
            tabelForm.setVisible(false);
            
            ordersButtonWaiter.setStyle("-fx-background-color:#ffffff55");
            tabelButtonWaiter.setStyle("-fx-background-color:transparent");
            
        }
        else if(event.getSource()==tabelButtonWaiter){
            ordersForm.setVisible(false);
            tabelForm.setVisible(true);
            
            ordersButtonWaiter.setStyle("-fx-background-color:transparent");
            tabelButtonWaiter.setStyle("-fx-background-color:#ffffff55");
        }
    }
    
    public void setWaiterId(int WaiterId) {
        this.WaiterId = WaiterId;
        loadDataOrders();
        loadDataTables();
        
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
       //loadDataTables();
       
    }
}
