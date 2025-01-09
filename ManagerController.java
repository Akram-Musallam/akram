/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.restaurant;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import tables.Employee;
/**
 * FXML Controller class
 *
 * @author Alaa
 */
public class ManagerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadDataEmployee();
    }
     @FXML
    private AnchorPane employeeForm;
     @FXML
    private Button Logout;

    @FXML
    private Button employeeButtonManager;

    @FXML
    private Button statisticsButtonManager;

    @FXML
    private AnchorPane statisticsForm;
    
    @FXML
    private TableColumn<Employee, String> Employee_address;

    @FXML
    private TableColumn<Employee, String> Employee_email;

    @FXML
    private TableColumn<Employee, String> Employee_firstName;

    @FXML
    private TableColumn<Employee, String> Employee_jobType;

    @FXML
    private TableColumn<Employee, String> Employee_lastName;

    @FXML
    private TableColumn<Employee, String> Employee_phoneNumber;

    @FXML
    private TableColumn<Employee, String> Employee_salary;

    @FXML
    private TableColumn<Employee, BigDecimal> Employee_ssn;
        
    @FXML
    private TableView<Employee> tableView;
    
    @FXML
    private TextField textFieldAddress;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private TextField textFieldFirstName;

    @FXML
    private TextField textFieldLastName;

    @FXML
    private TextField textFieldPhoneNumber;

    @FXML
    private TextField textFieldSsn;
    
    @FXML
    private TextField textFieldJobType;
    
    @FXML
    private TextField textFieldSalary;
    
    ObservableList<Employee> employeeList = FXCollections.observableArrayList();

    
     @FXML
    void ClearButton(ActionEvent event) {
        
        String ssn = textFieldSsn.getText().trim();
        if (ssn.isEmpty()) {
            showAlert("Invalid Input", "Please enter the SSN of the employee to delete.", Alert.AlertType.WARNING);
            return;
        }

        String conn = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String pass = "123456";

        try (Connection connection = DriverManager.getConnection(conn, user, pass)) {

            String deleteQuery = "DELETE FROM employee WHERE ssn = ?";
            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
                deleteStmt.setInt(1, Integer.parseInt(ssn));

                int rowsAffected = deleteStmt.executeUpdate();

                if (rowsAffected > 0) {
                    showAlert("Success", "Employee with SSN: " + ssn + " has been deleted successfully.", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Not Found", "No employee found with SSN: " + ssn, Alert.AlertType.ERROR);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Database Error", "An error occurred while deleting the employee.", Alert.AlertType.ERROR);
        }

        textFieldSsn.clear();
    }

    @FXML
    void insertButton(ActionEvent event) {

        String firstName = textFieldFirstName.getText().trim();
        String lastName = textFieldLastName.getText().trim();
        String phoneNumber = textFieldPhoneNumber.getText().trim();
        String email = textFieldEmail.getText().trim();
        String address = textFieldAddress.getText().trim();
        String jobType = textFieldJobType.getText().trim();
        String salaryText = textFieldSalary.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || address.isEmpty() || jobType.isEmpty() || salaryText.isEmpty()) {
            showAlert("Invalid Input", "Please fill in all fields.", Alert.AlertType.WARNING);
            return;
        }

        BigDecimal salary;
        try {
            salary = new BigDecimal(salaryText);
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Salary must be a valid number.", Alert.AlertType.WARNING);
            return;
        }

        String conn = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String pass = "123456";

        try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
            String insertQuery = "INSERT INTO employee (first_name, last_name, phone_number, email, address, job_type, salary) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {

                insertStmt.setString(1, firstName);
                insertStmt.setString(2, lastName);
                insertStmt.setString(3, phoneNumber);
                insertStmt.setString(4, email);
                insertStmt.setString(5, address);
                insertStmt.setString(6, jobType);
                insertStmt.setBigDecimal(7, salary);

                int rowsInserted = insertStmt.executeUpdate();

                if (rowsInserted > 0) {
                    showAlert("Success", "Employee inserted successfully.", Alert.AlertType.INFORMATION);
                    loadDataEmployee();
                } else {
                    showAlert("Failure", "Failed to insert employee. Please try again.", Alert.AlertType.ERROR);
                }
            }
            loadDataEmployee();
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Database Error", "An error occurred while inserting the employee.", Alert.AlertType.ERROR);
        }

    }

    
   @FXML
    void searchButton(ActionEvent event) {
        String jobType = textFieldJobType.getText().trim();
        String ssn = textFieldSsn.getText().trim();

        if (jobType.isEmpty() && ssn.isEmpty()) {
            loadDataEmployee();
            return;
        }

        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM employee WHERE ");
        List<String> conditions = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();

        if (!jobType.isEmpty()) {
            conditions.add("job_type LIKE ?");
            parameters.add("%" + jobType + "%");
        }

        if (!ssn.isEmpty()) {
            conditions.add("ssn = ?");
            parameters.add(Integer.parseInt(ssn));
        }

        queryBuilder.append(String.join(" AND ", conditions));

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "alaa.taha.2004");
             PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<Employee> employeeList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                Employee employee = new Employee(
                    resultSet.getInt("ssn"),
                    resultSet.getString("last_name"),
                    resultSet.getString("first_name"),
                    resultSet.getString("phone_number"),
                    resultSet.getString("email"),
                    resultSet.getString("address"),
                    resultSet.getString("job_type"),
                    resultSet.getBigDecimal("salary")
                );
                employeeList.add(employee);
            }

            tableView.setItems(employeeList);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Database Error", "An error occurred while searching for employees.", Alert.AlertType.ERROR);
        }
    }


    
    @FXML
    void updateButton(ActionEvent event) {
        
        String ssn = textFieldSsn.getText().trim();
        String firstName = textFieldFirstName.getText().trim();
        String lastName = textFieldLastName.getText().trim();
        String phoneNumber = textFieldPhoneNumber.getText().trim();
        String email = textFieldEmail.getText().trim();
        String address = textFieldAddress.getText().trim();
        String jobType = textFieldJobType.getText().trim();
        String salary = textFieldSalary.getText().trim();

        if (ssn.isEmpty()) {
            showAlert("Invalid Input", "Please enter the SSN to update the employee's information.", Alert.AlertType.WARNING);
            return;
        }

        String updateQuery = "UPDATE employee SET first_name = ?, last_name = ?, phone_number = ?, email = ?, address = ?, job_type = ?, salary = ? WHERE ssn = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "alaa.taha.2004");
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, phoneNumber);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, jobType);
            preparedStatement.setBigDecimal(7, new BigDecimal(salary));
            preparedStatement.setInt(8, Integer.parseInt(ssn));

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                showAlert("Success", "Employee information updated successfully.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "No employee found with the given SSN.", Alert.AlertType.ERROR);
            }
            loadDataEmployee();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Database Error", "An error occurred while updating the employee's information.", Alert.AlertType.ERROR);
        }
    }

    
   
    @FXML
    void loadDataEmployee() {
        
        employeeList.clear();
        String conn = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String pass = "123456";

       

        try (Connection connection = DriverManager.getConnection(conn, user, pass)) {
            String query = "SELECT ssn, first_name, last_name, phone_number, email, address, job_type, salary FROM employee";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                ResultSet resultSet = stmt.executeQuery();

                while (resultSet.next()) {
                    // Create Employee object from ResultSet
                    Employee employee = new Employee(
                            resultSet.getInt("ssn"),
                            resultSet.getString("last_name"),
                            resultSet.getString("first_name"),
                            resultSet.getString("phone_number"),
                            resultSet.getString("email"),
                            resultSet.getString("address"),
                            resultSet.getString("job_type"),
                            resultSet.getBigDecimal("salary")
                    );

                    // Add Employee to the list
                    employeeList.add(employee);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Database Error", "An error occurred while fetching employee data.", Alert.AlertType.ERROR);
        }

        // Bind the TableView columns with the Employee properties
        Employee_ssn.setCellValueFactory(new PropertyValueFactory<>("ssn"));
        Employee_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        Employee_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        Employee_phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        Employee_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        Employee_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        Employee_jobType.setCellValueFactory(new PropertyValueFactory<>("jobType"));
        Employee_salary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        // Set the data in the TableView
        tableView.setItems(employeeList);
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
    public void switchForm(ActionEvent event){
        if(event.getSource()==statisticsButtonManager){
            statisticsForm.setVisible(true);
            employeeForm.setVisible(false);
            
            statisticsButtonManager.setStyle("-fx-background-color:#ffffff55");
            employeeButtonManager.setStyle("-fx-background-color:transparent");
            
        }
        else if(event.getSource()==employeeButtonManager){
            statisticsForm.setVisible(false);
            employeeForm.setVisible(true);
            
            statisticsButtonManager.setStyle("-fx-background-color:transparent");
            employeeButtonManager.setStyle("-fx-background-color:#ffffff55");
        }
    }
    
    private void showAlert(String title, String message, Alert.AlertType alertType) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
    } 
    
    @FXML
    void buttonStat(ActionEvent event){
        try{
            InputStream ins = new FileInputStream(new File("C:\\Users\\Alaa\\Documents\\NetBeansProjects\\mavenproject2\\Leaf_Violet11.jrxml"));
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
    
}
