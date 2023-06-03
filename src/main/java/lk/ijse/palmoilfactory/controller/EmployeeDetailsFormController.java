package lk.ijse.palmoilfactory.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lk.ijse.palmoilfactory.db.DBConnection;
import lk.ijse.palmoilfactory.dto.Employee;
import lk.ijse.palmoilfactory.dto.Stock;
import lk.ijse.palmoilfactory.dto.Supplier;
import lk.ijse.palmoilfactory.dto.tm.EmployeeTM;
import lk.ijse.palmoilfactory.dto.tm.SupplierTM;
import lk.ijse.palmoilfactory.model.EmployeeModel;
import lk.ijse.palmoilfactory.model.StockModel;
import lk.ijse.palmoilfactory.model.SupplierModel;
import lk.ijse.palmoilfactory.util.Regex;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeDetailsFormController implements Initializable {

    @FXML
    private Pane empdetailsPane;

    @FXML
    private JFXTextField txtEmployeeId;

    @FXML
    private JFXTextField txtEmployeeAddress;

    @FXML
    private JFXTextField txtEmployeeName;

    @FXML
    private JFXTextField txtEmployeeContact;

    @FXML
    private JFXTextField txtEmployeeSalary;

    @FXML
    private JFXComboBox<String> cmbEmployeetype;

    @FXML
    private JFXComboBox<String> cmbEmployeeSchId;

    @FXML
    private TableView<EmployeeTM> tblEmployeeDetails;

    @FXML
    private TableColumn<?, ?> colEMPID;

    @FXML
    private TableColumn<?, ?> colEMPName;

    @FXML
    private TableColumn<?, ?> colEMPAddress;

    @FXML
    private TableColumn<?, ?> colEMPContact;

    @FXML
    private TableColumn<?, ?> colEMPSalary;

    @FXML
    private TableColumn<?, ?> colEMPType;

    @FXML
    private TableColumn<?, ?> colSCHId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private JFXButton btnAddEmployee;

    private ObservableList<EmployeeTM> obList = FXCollections.observableArrayList();


    private String searchText="";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> txtEmployeeId.requestFocus());
        loadSchIds();
        loadEmpType();

        setCellValueFactory(); //To show table data
        getAllEmployeesToTable(searchText); //To get all supplier details to table(Not show)

        tblEmployeeDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { //Add ActionListener to selected column and display text field values
            //Check select value is not null
            if(null!=newValue) { //newValue!=null --> Get more time to compare (newValue object compare)
                btnAddEmployee.setText("Update Employee");
                setDataToTextFields(newValue); //Set data to text field of selected row data of table
            }
        });

        /*txtEmployeeId.textProperty().addListener((observable, oldValue, newValue) -> { //Add action listener to txtSearch to search and display table
            tblEmployeeDetails.getItems().clear();
            searchText=newValue;
            getAllEmployeesToTable(searchText);
        });*/
    }

    private void setDataToTextFields(EmployeeTM employeeTM) {
        txtEmployeeId.setText(employeeTM.getEmpId());
        txtEmployeeName.setText(employeeTM.getEmpName());
        txtEmployeeAddress.setText(employeeTM.getEmpAddress());
        txtEmployeeContact.setText(employeeTM.getEmpContact());
        txtEmployeeSalary.setText(String.valueOf(employeeTM.getEmpSalary()));
        cmbEmployeetype.setValue(employeeTM.getEmpType());
        cmbEmployeeSchId.setValue(employeeTM.getSchId());
    }

    private void getAllEmployeesToTable(String searchText) {
        try {
            List<Employee> empList = EmployeeModel.getAll();
            for(Employee employee : empList) {
                if (employee.getEmpName().contains(searchText) || employee.getEmpAddress().contains(searchText)){  //Check pass text contains of the supName
                    JFXButton btnDel=new JFXButton("Delete");
                    btnDel.setAlignment(Pos.CENTER);
                    btnDel.setStyle("-fx-background-color: #e74c3c; ");
                    btnDel.setCursor(Cursor.HAND);

                    EmployeeTM tm=new EmployeeTM(
                            employee.getEmpId(),
                            employee.getEmpName(),
                            employee.getEmpAddress(),
                            employee.getEmpContact(),
                            employee.getEmpSalary(),
                            employee.getEmpType(),
                            employee.getEmpSchId(),
                            btnDel);

                    obList.add(tm);

                    setDeleteButtonTableOnAction(btnDel);
                }
            }
            tblEmployeeDetails.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    private void setDeleteButtonTableOnAction(JFXButton btnDel) {
        btnDel.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Delete?", yes, no).showAndWait();

            if (buttonType.get() == yes) {
                txtEmployeeId.setText(tblEmployeeDetails.getSelectionModel().getSelectedItem().getEmpId());
                btnSearchEmployeeOnAction(e);
              //  btnDeleteEmployeeOnAction(e);

                String empId = txtEmployeeId.getText();
                try {

                    boolean isDeleted = EmployeeModel.deleteEmployee(empId);
                    if (isDeleted) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Employee Deleted Successfully").show();
                        clearFields();
                        tblEmployeeDetails.getItems().clear();
                        getAllEmployeesToTable("");
                        loadSchIds();
                        loadEmpType();

                    } else {
                        new Alert(Alert.AlertType.WARNING, "Delete Fail").show();
                    }
                } catch (SQLException exception) {
                    new Alert(Alert.AlertType.WARNING, "OOPSSS!! something happened!!!").show();

                } catch (ClassNotFoundException exception) {
                    new Alert(Alert.AlertType.WARNING, "OOPSSS!! something happened!!!").show();
                }

                tblEmployeeDetails.getItems().clear();
                getAllEmployeesToTable("");
            }
        });

    }

    private void setCellValueFactory() {
        colEMPID.setCellValueFactory(new PropertyValueFactory<>("empId")); //EmployeeTM class attributes names
        colEMPName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colEMPAddress.setCellValueFactory(new PropertyValueFactory<>("empAddress"));
        colEMPContact.setCellValueFactory(new PropertyValueFactory<>("empContact"));
        colEMPSalary.setCellValueFactory(new PropertyValueFactory<>("empSalary"));
        colEMPContact.setCellValueFactory(new PropertyValueFactory<>("empContact"));
        colEMPType.setCellValueFactory(new PropertyValueFactory<>("empType"));
        colSCHId.setCellValueFactory(new PropertyValueFactory<>("schId"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void loadEmpType() {
        ArrayList<String> empTypeList=new ArrayList<> ();
        empTypeList.add("Oil Production");
        empTypeList.add("By Production");
        empTypeList.add("Other");

        ObservableList<String> dataSet = FXCollections.observableArrayList(empTypeList);
        cmbEmployeetype.setItems(dataSet);

    }

    private void loadSchIds() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> schIDs = EmployeeModel.getSchIDs();

            for (String schId : schIDs) {
                obList.add(schId);
            }
            cmbEmployeeSchId.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
        }
    }

    @FXML
    void btnAddEmployeeOnAction(ActionEvent event) {
        if(txtEmployeeId.getText().isEmpty() || txtEmployeeName.getText().isEmpty() || txtEmployeeAddress.getText().isEmpty() || txtEmployeeContact.getText().isEmpty() || txtEmployeeSalary.getText().isEmpty() || cmbEmployeetype.getSelectionModel().isEmpty() || cmbEmployeeSchId.getSelectionModel().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please Input Data to Add Employee").show();
        }else {
            String empId = txtEmployeeId.getText();
            String empName = txtEmployeeName.getText();
            String empAddress = txtEmployeeAddress.getText();
            String empContact = txtEmployeeContact.getText();
            double empSalary = Double.parseDouble(txtEmployeeSalary.getText());
            String empType = cmbEmployeetype.getSelectionModel().getSelectedItem();
            String empSchId = cmbEmployeeSchId.getSelectionModel().getSelectedItem();

            boolean isAdded;

            if(btnAddEmployee.getText().equalsIgnoreCase("Add Employee")) {

                try {
                    isAdded = EmployeeModel.addEmployee(empId, empName, empAddress, empContact, empSalary, empType, empSchId);
                    if (isAdded) {
                        tblEmployeeDetails.getItems().clear();
                        new Alert(Alert.AlertType.CONFIRMATION, "Employee Added").show();
                        clearFields();
                        getAllEmployeesToTable(searchText);
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Employee Not Added Please Try Again").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
                } catch (ClassNotFoundException e) {
                    new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
                }
            }else {
                if (txtEmployeeId.getText().isEmpty() || txtEmployeeName.getText().isEmpty() || txtEmployeeAddress.getText().isEmpty() || txtEmployeeContact.getText().isEmpty() || txtEmployeeSalary.getText().isEmpty() || cmbEmployeetype.getSelectionModel().isEmpty() || cmbEmployeeSchId.getSelectionModel().isEmpty()) {
                    new Alert(Alert.AlertType.WARNING, "Please Input Employee ID and Search Employee is exist").show();
                } else {
                    /*String empId = txtEmployeeId.getText();
                    String empName = txtEmployeeName.getText();
                    String empAddress = txtEmployeeAddress.getText();
                    String empContact = txtEmployeeContact.getText();
                    double empSalary = Double.parseDouble(txtEmployeeSalary.getText());
                    String empType = null;
                    String empSchId = null;*/

                    if(cmbEmployeetype.getSelectionModel().isEmpty() || cmbEmployeeSchId.getSelectionModel().isEmpty()){
                        try {
                            empType = EmployeeModel.searchByempIdEmployeeType(empId);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        try {
                            empSchId=EmployeeModel.searchByempIdEmployeeSchId(empId);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }else {
                        empType=cmbEmployeetype.getSelectionModel().getSelectedItem();
                        empSchId=cmbEmployeeSchId.getSelectionModel().getSelectedItem();
                    }

                    boolean isUpdated;

                    try {
                        isUpdated = EmployeeModel.updateEmployee(empId, empName, empAddress, empContact, empSalary,empType,empSchId);
                        if (isUpdated) {
                            tblEmployeeDetails.getItems().clear();
                            new Alert(Alert.AlertType.CONFIRMATION, "Employee Updated").show();
                            clearFields();
                            getAllEmployeesToTable(searchText);
                        } else {
                            new Alert(Alert.AlertType.WARNING, "Employee Not Updated Please Try Again").show();
                        }
                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
                    } catch (ClassNotFoundException e) {
                        new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
                    }
                }
            }
        }
    }

    private void clearFields() {
        txtEmployeeId.clear();
        txtEmployeeName.clear();
        txtEmployeeAddress.clear();
        txtEmployeeContact.clear();
        txtEmployeeSalary.clear();
        cmbEmployeetype.getItems().clear();
        cmbEmployeeSchId.getItems().clear();
    }

    @FXML
    void btnSearchEmployeeOnAction(ActionEvent event) {
       if(txtEmployeeId.getText().isEmpty() ){
            new Alert(Alert.AlertType.WARNING,"Please Input Employee ID to search Employee").show();
        }else {
            String empId = txtEmployeeId.getText();
            try {
                Employee employee = EmployeeModel.searchEmployee(empId);
                if (employee != null) {
                    txtEmployeeName.setText(String.valueOf(employee.getEmpName()));
                    txtEmployeeAddress.setText(employee.getEmpAddress());
                    txtEmployeeContact.setText(employee.getEmpContact());
                    txtEmployeeSalary.setText(String.valueOf(employee.getEmpSalary()));
                    cmbEmployeetype.setValue(EmployeeModel.searchByempIdEmployeeType(empId));
                    cmbEmployeeSchId.setValue(EmployeeModel.searchByempIdEmployeeSchId(empId));

                } else {
                    new Alert(Alert.AlertType.WARNING, "Employee Not Found Please Try Again").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.WARNING, "OOPSSS!! something happened!!!").show();

            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.WARNING, "OOPSSS!! something happened!!!").show();
            }
        }
    }

    @FXML
    void txtEmployeeIdOnAction(ActionEvent event) {
        String empId=txtEmployeeId.getText();
        if (Regex.validateEMPID(empId)){
            btnSearchEmployeeOnAction(event);
            txtEmployeeName.requestFocus();
        }else {
            txtEmployeeId.clear();
            new Alert(Alert.AlertType.WARNING, "No matching employee ID please Input EMP format!!!").show();
        }
       // btnSearchEmployeeOnAction(event);
    }

    @FXML
    void txtEmployeeNameOnAction(ActionEvent event) {
        txtEmployeeAddress.requestFocus();
    }

    @FXML
    void txtEmployeeAddressOnAction(ActionEvent event) {
        txtEmployeeContact.requestFocus();
    }

    @FXML
    void txtEmployeeContactOnAction(ActionEvent event) {
        String contact=txtEmployeeContact.getText();
        if (Regex.validateContact(contact)){
            txtEmployeeSalary.requestFocus();
        }else {
            new Alert(Alert.AlertType.WARNING, "No matching contact number please Input correct format!!!").show();
            txtEmployeeContact.clear();
        }

    }

    @FXML
    void btnDeleteEmployeeOnAction(ActionEvent event) {
        if (txtEmployeeId.getText().isEmpty() || txtEmployeeName.getText().isEmpty() || txtEmployeeAddress.getText().isEmpty() || txtEmployeeContact.getText().isEmpty() || txtEmployeeSalary.getText().isEmpty() || cmbEmployeetype.getSelectionModel().isEmpty() || cmbEmployeeSchId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please Input Employee ID and Search Employee is exist").show();
        } else {
            String empId = txtEmployeeId.getText();

            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Delete?", yes, no).showAndWait();

            if (buttonType.get() == yes) {
                txtEmployeeId.setText(tblEmployeeDetails.getSelectionModel().getSelectedItem().getEmpId());
                btnSearchEmployeeOnAction(event);
                try {

                    boolean isDeleted = EmployeeModel.deleteEmployee(empId);
                    if (isDeleted) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Employee Deleted Successfully").show();
                        clearFields();
                        tblEmployeeDetails.getItems().clear();
                        getAllEmployeesToTable("");
                        loadEmpType();
                        loadSchIds();

                    } else {
                        new Alert(Alert.AlertType.WARNING, "Delete Fail").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.WARNING, "OOPSSS!! something happened!!!").show();

                } catch (ClassNotFoundException e) {
                    new Alert(Alert.AlertType.WARNING, "OOPSSS!! something happened!!!").show();
                }
            }
        }
    }

    @FXML
    void btnEditScheduleDetailsOnAction(ActionEvent event) throws IOException {
        TranslateTransition transition = new TranslateTransition();
        Parent load = FXMLLoader.load(getClass().getResource("/view/schedule-details-form.fxml"));
        transition.setNode(load);
        transition.setDuration(Duration.seconds(0.5));

        transition.setToX(390);

        empdetailsPane.getChildren().add(load);

        transition.play();

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        txtEmployeeId.clear();
        txtEmployeeId.requestFocus();
        btnAddEmployee.setText("Add Employee");
        loadEmpType();
        loadSchIds();
    }

    @FXML
    void btnGetReportOnAction(ActionEvent event) {
        Thread t1=new Thread(
                () -> {
                    String reportPath = "E:\\1.GDSE\\1st Semester\\9.My Final Project-1st Semester\\AEN Palm Oil Factory Project\\production-and-fuel-management-system\\src\\main\\resources\\reports\\employeeDetailsReport.jrxml";
                    String sql="select * from employee";
                    String path = FileSystems.getDefault().getPath("/reports/employeeDetailsReport.jrxml").toAbsolutePath().toString();
                    JasperDesign jasdi = null;
                    try {
                        jasdi = JRXmlLoader.load(reportPath);
                        JRDesignQuery newQuery = new JRDesignQuery();
                        newQuery.setText(sql);
                        jasdi.setQuery(newQuery);
                        JasperReport js = JasperCompileManager.compileReport(jasdi);
                        JasperPrint jp = JasperFillManager.fillReport(js, null, DBConnection.getInstance().getConnection());
                        JasperViewer viewer = new JasperViewer(jp, false);
                        viewer.show();
                    } catch (JRException e) {
                        e.printStackTrace();
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                });

        t1.start();
    }


}
