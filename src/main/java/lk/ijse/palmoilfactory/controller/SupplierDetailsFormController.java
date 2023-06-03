package lk.ijse.palmoilfactory.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.palmoilfactory.db.DBConnection;
import lk.ijse.palmoilfactory.dto.Supplier;
import lk.ijse.palmoilfactory.dto.tm.SupplierTM;
import lk.ijse.palmoilfactory.model.SupplierModel;
import lk.ijse.palmoilfactory.util.CrudUtil;
import lk.ijse.palmoilfactory.util.Regex;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.nio.file.FileSystems;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierDetailsFormController implements Initializable {

    @FXML
    private JFXTextField txtSupplierId;

    @FXML
    private JFXTextField txtSupplierAddress;

    @FXML
    private JFXTextField txtSupplierName;

    @FXML
    private JFXTextField txtSupplierContact;

    @FXML
    private TableColumn<?, ?> colSupId;

    @FXML
    private TableColumn<?, ?> colSupName;

    @FXML
    private TableColumn<?, ?> colSupAddress;

    @FXML
    private TableColumn<?, ?> colSupContact;

    @FXML
    private TableColumn<?, ?> colSupAction;

    @FXML
    private TableView<SupplierTM> tblSupplier;

    private ObservableList<SupplierTM> obList = FXCollections.observableArrayList();

    @FXML
    private JFXButton btnSaveSupplier;

    @FXML
    private JFXTextField txtSearch;

    private String searchText="";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> txtSearch.requestFocus());
        setCellValueFactory(); //To show table data
        getAllSupplierToTable(searchText); //To get all supplier details to table(Not show)

        tblSupplier.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { //Add ActionListener to selected column and display text field values
            //Check select value is not null
            if(null!=newValue) { //newValue!=null --> Get more time to compare (newValue object compare)
                btnSaveSupplier.setText("Update Supplier");
                setDataToTextFields(newValue); //Set data to text field of selected row data of table
            }
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> { //Add action listener to txtSearch to search and display table
            tblSupplier.getItems().clear();
            searchText=newValue;
            getAllSupplierToTable(searchText);
        });
    }

    private void setDataToTextFields(SupplierTM supplierTM) {
        txtSupplierId.setText(supplierTM.getSupId());
        txtSupplierName.setText(supplierTM.getSupName());
        txtSupplierAddress.setText(supplierTM.getSupAddress());
        txtSupplierContact.setText(supplierTM.getSupContact());
    }

    void setCellValueFactory() {
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supId")); //SupplierTM class attributes names
        colSupName.setCellValueFactory(new PropertyValueFactory<>("supName"));
        colSupAddress.setCellValueFactory(new PropertyValueFactory<>("supAddress"));
        colSupContact.setCellValueFactory(new PropertyValueFactory<>("supContact"));
        colSupAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    void getAllSupplierToTable(String text) {
        try {
            List<Supplier> supList = SupplierModel.getAll();
            for(Supplier supplier : supList) {
                if (supplier.getSupName().contains(text) || supplier.getSupAddress().contains(text)){  //Check pass text contains of the supName
                    JFXButton btnDel=new JFXButton("Delete");
                    btnDel.setAlignment(Pos.CENTER);
                    btnDel.setStyle("-fx-background-color: #686de0; ");
                    btnDel.setCursor(Cursor.HAND);

                    SupplierTM tm=new SupplierTM(
                            supplier.getSupId(),
                            supplier.getSupName(),
                            supplier.getSupAddress(),
                            supplier.getSupContact(),
                            btnDel);

                    obList.add(tm);

                    setDeleteButtonTableOnAction(btnDel);
                }
            }
            tblSupplier.setItems(obList);
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
                txtSupplierId.setText(tblSupplier.getSelectionModel().getSelectedItem().getSupId());
                btnSearchSupplierOnAction(e);
              //  btnDeleteSupplierOnAction(e);
                String supId=txtSupplierId.getText();
                try {

                    boolean isDeleted = SupplierModel.deleteSupplier(supId);
                    if (isDeleted) {
                        tblSupplier.getItems().clear();
                        new Alert(Alert.AlertType.CONFIRMATION, "Supplier Deleted Successfully").show();
                        clearFields();
                        getAllSupplierToTable(searchText);

                    } else {
                        new Alert(Alert.AlertType.WARNING, "Delete Fail").show();
                    }
                } catch (SQLException exception) {
                    new Alert(Alert.AlertType.WARNING, "OOPSSS!! something happened!!!").show();

                } catch (ClassNotFoundException exception) {
                    new Alert(Alert.AlertType.WARNING, "OOPSSS!! something happened!!!").show();
                }
                tblSupplier.getItems().clear();
                getAllSupplierToTable(searchText);
            }
        });
    }

    @FXML
    void btnSaveSupplierOnAction(ActionEvent event) {
        if(txtSupplierId.getText().isEmpty() || txtSupplierName.getText().isEmpty() || txtSupplierAddress.getText().isEmpty() || txtSupplierContact.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please Input Data to Add Supplier").show();
        }else {

            String supId = txtSupplierId.getText();
            String supName = txtSupplierName.getText();
            String supAddress = txtSupplierAddress.getText();
            String supContact = txtSupplierContact.getText();

            boolean isAdded;

            if(btnSaveSupplier.getText().equalsIgnoreCase("Save Supplier")){
                try {
                    isAdded = SupplierModel.addSupplier(supId, supName, supAddress, supContact);
                    if (isAdded) {
                        tblSupplier.getItems().clear();
                        new Alert(Alert.AlertType.CONFIRMATION, "Supplier Added").show();
                        clearFields();
                        getAllSupplierToTable(searchText);

                    } else {
                        new Alert(Alert.AlertType.WARNING, "Supplier Not Added Please Try Again").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
                } catch (ClassNotFoundException e) {
                    new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
                }

            }else{

                if(txtSupplierId.getText().isEmpty() || txtSupplierName.getText().isEmpty() || txtSupplierAddress.getText().isEmpty() || txtSupplierContact.getText().isEmpty()){
                    new Alert(Alert.AlertType.CONFIRMATION,"Please Input Supplier ID and Search Supplier is exist").show();
                }else {
                    boolean isUpdated;
                    try {
                        isUpdated = SupplierModel.updateSupplier(supId, supName, supAddress, supContact);
                        if (isUpdated) {
                            tblSupplier.getItems().clear();
                            new Alert(Alert.AlertType.CONFIRMATION, "Supplier Updated").show();
                            clearFields();
                            getAllSupplierToTable(searchText);

                        } else {
                            new Alert(Alert.AlertType.WARNING, "Supplier Not Updated Please Try Again").show();
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

    @FXML
    void btnSearchSupplierOnAction(ActionEvent event) {
        if(txtSupplierId.getText().isEmpty() && txtSupplierName.getText().isEmpty() && txtSupplierAddress.getText().isEmpty() && txtSupplierContact.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please Input Supplier ID to Search Supplier ").show();
        }else {
            String id = txtSupplierId.getText();
            try {
                Supplier supplier = SupplierModel.searchSupplier(id);

                if (supplier != null) {
                    txtSupplierId.setText(supplier.getSupId());
                    txtSupplierName.setText(supplier.getSupName());
                    txtSupplierAddress.setText(supplier.getSupAddress());
                    txtSupplierContact.setText(supplier.getSupContact());

                } else {
                    new Alert(Alert.AlertType.WARNING, "Supplier Not Found Please Try Again").show();
                    txtSupplierName.requestFocus();
                }

            } catch (SQLException e) {
                new Alert(Alert.AlertType.WARNING, "OOPSSS!! something happened!!!").show();

            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.WARNING, "OOPSSS!! something happened!!!").show();
            }
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        btnClearOnAction(event);
        txtSupplierId.requestFocus();
    }
    @FXML
    void txtSupplierIdOnAction(ActionEvent event) {
        String supId=txtSupplierId.getText();
        if (Regex.validateSupplierId(supId)){
            btnSearchSupplierOnAction(event);
            txtSupplierName.requestFocus();
        }else {
            txtSupplierId.clear();
            new Alert(Alert.AlertType.WARNING, "No matching supplier ID please Input SUP format!!!").show();
        }

    }

    @FXML
    void txtSupplierNameOnAction(ActionEvent event) {
        txtSupplierAddress.requestFocus();
    }

    @FXML
    void txtSupplierAddressOnAction(ActionEvent event) {
        txtSupplierContact.requestFocus();
    }

    @FXML
    void txtSupplierContactOnAction(ActionEvent event) {
        String contact=txtSupplierContact.getText();
        if (Regex.validateContact(contact)){
            btnSaveSupplierOnAction(event);
            txtSearch.requestFocus();
        }else {
            txtSupplierContact.clear();
            new Alert(Alert.AlertType.WARNING, "No matching contact please Input contact format!!!").show();
        }

    }

    @FXML
    void btnDeleteSupplierOnAction(ActionEvent event) {

        txtSupplierId.setText(tblSupplier.getSelectionModel().getSelectedItem().getSupId());
        if(txtSupplierId.getText().isEmpty() || txtSupplierName.getText().isEmpty() || txtSupplierAddress.getText().isEmpty() || txtSupplierContact.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please Input Supplier ID and Search Supplier is exist").show();
        }else {
            String supId = txtSupplierId.getText();

            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Delete?", yes, no).showAndWait();

            if (buttonType.get() == yes) {
                txtSupplierId.setText(tblSupplier.getSelectionModel().getSelectedItem().getSupId());
                try {

                    boolean isDeleted = SupplierModel.deleteSupplier(supId);
                    if (isDeleted) {
                        tblSupplier.getItems().clear();
                        new Alert(Alert.AlertType.CONFIRMATION, "Supplier Deleted Successfully").show();
                        clearFields();
                        getAllSupplierToTable(searchText);

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

    private void clearFields(){
        txtSupplierName.clear();
        txtSupplierId.clear();
        txtSupplierAddress.clear();
        txtSupplierContact.clear();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        txtSearch.clear();
        txtSearch.requestFocus();
        btnSaveSupplier.setText("Save Supplier");
    }

    @FXML
    void btnGetReportOnAction(ActionEvent event)  {
        Thread t1=new Thread(
                () -> {
                    String reportPath = "E:\\1.GDSE\\1st Semester\\9.My Final Project-1st Semester\\AEN Palm Oil Factory Project\\production-and-fuel-management-system\\src\\main\\resources\\reports\\supplierDetailsReport.jrxml";
                    String sql="select * from supplier";
                    String path = FileSystems.getDefault().getPath("/reports/supplierDetailsReport.jrxml").toAbsolutePath().toString();
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
