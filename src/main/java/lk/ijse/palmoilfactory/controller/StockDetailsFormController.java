package lk.ijse.palmoilfactory.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import lk.ijse.palmoilfactory.db.DBConnection;
import lk.ijse.palmoilfactory.dto.Stock;
import lk.ijse.palmoilfactory.dto.Supplier;
import lk.ijse.palmoilfactory.dto.tm.StockTM;
import lk.ijse.palmoilfactory.dto.tm.SupplierTM;
import lk.ijse.palmoilfactory.model.OilProductionModel;
import lk.ijse.palmoilfactory.model.StockModel;
import lk.ijse.palmoilfactory.model.SupplierModel;
import lk.ijse.palmoilfactory.util.Regex;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.nio.file.FileSystems;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.time.LocalDateTime.*;
import static java.time.format.DateTimeFormatter.*;
import static lk.ijse.palmoilfactory.controller.OilProductionFormController.getTotalOilQtyOnAllStockIDs;

public class StockDetailsFormController implements Initializable {
    @FXML
    private JFXTextField txtStockId;

    @FXML
    private JFXTextField txtFFBInput;

    @FXML
    private Label lblTime;

    @FXML
    private JFXComboBox<String> cmbSupplierId;

    @FXML
    private TableView<StockTM> tblStockDetails;

    @FXML
    private TableColumn<?, ?> colStockID;

    @FXML
    private TableColumn<?, ?> colFFBInput;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableColumn<?, ?> colAction;

    private Timeline timeline;

    @FXML
    private DatePicker dtpckrDate;

    private String searchText="";

    @FXML
    private JFXButton btnAddStock;

    private ObservableList<StockTM> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadSupplierIds();
        Platform.runLater(() -> txtStockId.requestFocus());
        startTimeline();
        dtpckrDate.setValue(LocalDate.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

        setCellValueFactory();
        getAllStocksToTable(searchText);

        tblStockDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { //Add ActionListener to selected column and display text field values
            //Check select value is not null
            if(null!=newValue) { //newValue!=null --> Get more time to compare (newValue object compare)
                btnAddStock.setText("Update Stock");
                setDataToTextFields(newValue); //Set data to text field of selected row data of table
            }
        });

        /*txtStockId.textProperty().addListener((observable, oldValue, newValue) -> { //Add action listener to txtSearch to search and display table
            tblStockDetails.getItems().clear();
            searchText=newValue;
            getAllStocksToTable(searchText);
        });*/
    }

    private void setDataToTextFields(StockTM stockTM) {
        txtStockId.setText(stockTM.getStockId());
        txtFFBInput.setText(String.valueOf(stockTM.getFfbInput()));
        dtpckrDate.setValue(LocalDate.parse(stockTM.getDate()));
        stopTimeline();
        lblTime.setText(stockTM.getTime());
        cmbSupplierId.setValue(stockTM.getSupplierID());
    }

    private void getAllStocksToTable(String searchText) {
        try {
            List<Stock> stockList = StockModel.getAll();
            for(Stock stock : stockList) {
                if (stock.getStockId().contains(searchText) ){  //Check pass text contains of the supName
                    JFXButton btnDel=new JFXButton("Delete");
                    btnDel.setAlignment(Pos.CENTER);
                    btnDel.setStyle("-fx-background-color: #686de0; ");
                    btnDel.setCursor(Cursor.HAND);

                    StockTM tm=new StockTM(
                            stock.getStockId(),
                            stock.getFfbInput(),
                            stock.getDate(),
                            stock.getTime(),
                            stock.getSupId(),
                            btnDel);

                    obList.add(tm);

                    setDeleteButtonTableOnAction(btnDel);
                }
            }

            tblStockDetails.setItems(obList);
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
                String stockId = txtStockId.getText();

                boolean isDeleted;

                txtStockId.setText(tblStockDetails.getSelectionModel().getSelectedItem().getStockId());
                 btnSearchStockOnAction(e);
                // btnDeleteStockOnAction(e);
             try {
                isDeleted = StockModel.deleteStock(stockId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Stock Deleted Successfully").show();
                    //int ffbInput = StockModel.searchByStockIdFFBInput(stockId);
                    /*String ffbInputOilQty = OilProductionFormController.ffbInputOilQty(ffbInput);
                    OilProductionModel.subtractionOilQty(Double.parseDouble(ffbInputOilQty));*/
                    txtStockId.clear();
                    txtFFBInput.clear();
                    cmbSupplierId.getItems().clear();
                    tblStockDetails.getItems().clear();
                    getAllStocksToTable("");
                    loadSupplierIds();

                } else {
                    new Alert(Alert.AlertType.WARNING, "Delete Fail").show();
                }
            } catch (SQLException exception) {
                new Alert(Alert.AlertType.WARNING, "OOPSSS!! something happened!!!").show();

            } catch (ClassNotFoundException exception) {
                new Alert(Alert.AlertType.WARNING, "OOPSSS!! something happened!!!").show();
            }

                tblStockDetails.getItems().clear();
                getAllStocksToTable(searchText);
            }
        });
    }

    private void setCellValueFactory() {
        colStockID.setCellValueFactory(new PropertyValueFactory<>("stockId")); //StockTM class attributes names
        colFFBInput.setCellValueFactory(new PropertyValueFactory<>("ffbInput"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    public void startTimeline() {
        lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")))));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void stopTimeline() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    private void loadSupplierIds() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> iDs = SupplierModel.getIDs();

            for (String id : iDs) {
                obList.add(id);
            }
            cmbSupplierId.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
        }
    }

    @FXML
    void btnAddStockOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(txtStockId.getText().isEmpty() || txtFFBInput.getText().isEmpty() ){
            new Alert(Alert.AlertType.WARNING,"Please Input data to Add Stock").show();
        }else {
            String stockId = txtStockId.getText();
            double ffbInput = Double.parseDouble(txtFFBInput.getText());
            String date=String.valueOf(dtpckrDate.getValue());
            dtpckrDate.setValue(LocalDate.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("     hh:mm:ss ")));
            String time=lblTime.getText();
            String supId = String.valueOf(cmbSupplierId.getSelectionModel().getSelectedItem());

            boolean isAdded;

            if (btnAddStock.getText().equalsIgnoreCase("Save Stock")){
            try {
               isAdded = StockModel.placeStock(stockId, ffbInput, date,time ,supId); //Transaction

                if (isAdded) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Stock Added").show();

                   String ffbInputOilQty = OilProductionFormController.ffbInputOilQty(ffbInput);
                   OilProductionModel.addOilQtyTototalOil(Double.parseDouble(ffbInputOilQty));
                    txtStockId.clear();
                    txtFFBInput.clear();
                    cmbSupplierId.getItems().clear();
                    tblStockDetails.getItems().clear();
                    getAllStocksToTable("");
                    loadSupplierIds();
                    txtStockId.requestFocus();

                } else {
                    new Alert(Alert.AlertType.WARNING, "Stock Not Added Please Try Again").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
            }
        } else {
                if (txtStockId.getText().isEmpty() || txtFFBInput.getText().isEmpty() || dtpckrDate.getValue() == null || lblTime.getText().isEmpty()) {
                    new Alert(Alert.AlertType.WARNING, "Please Input Stock ID and Search Stock is exist").show();
                } else {
                    if(cmbSupplierId.getSelectionModel().isEmpty()){
                        supId = StockModel.searchByStockIdSupId(stockId);
                    }else {
                        supId=cmbSupplierId.getSelectionModel().getSelectedItem();
                    }

                    boolean isUpdated;

                    try {
                        isUpdated = StockModel.updateStock(stockId, ffbInput, String.valueOf(dtpckrDate.getValue()), lblTime.getText(), supId);
                        if (isUpdated) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Stock Updated").show();
                            txtStockId.clear();
                            txtFFBInput.clear();
                            cmbSupplierId.getItems().clear();
                            tblStockDetails.getItems().clear();
                            getAllStocksToTable("");
                        } else {
                            new Alert(Alert.AlertType.WARNING, "Stock Not Updated Please Try Again").show();
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
    void btnSearchStockOnAction(ActionEvent event) {
        if(txtStockId.getText().isEmpty() && txtFFBInput.getText().isEmpty() ){
            new Alert(Alert.AlertType.WARNING,"Please Input Stock ID to search Stock").show();
        }else {
            String stockId = txtStockId.getText();
            try {
                Stock stock = StockModel.searchStock(stockId);
                if (stock != null) {
                    txtFFBInput.setText(String.valueOf(stock.getFfbInput()));
                    dtpckrDate.setValue(LocalDate.parse(stock.getDate()));
                    stopTimeline();
                    lblTime.setText(stock.getTime());
                    cmbSupplierId.setValue(StockModel.searchByStockIdSupId(stockId));

                } else {
                    new Alert(Alert.AlertType.WARNING, "Stock Not Found Please Try Again").show();
                    txtFFBInput.requestFocus();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.WARNING, "OOPSSS!! something happened!!!").show();

            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.WARNING, "OOPSSS!! something happened!!!").show();
            }
        }
    }

    @FXML
    void txtStockIdOnAction(ActionEvent event) {
        String stockId=txtStockId.getText();
        if (Regex.validateStockId(stockId)){
            btnSearchStockOnAction(event);
        }else {
            txtStockId.clear();
            new Alert(Alert.AlertType.WARNING, "No matching stock ID please Input FFB format!!!").show();
        }
    }

    @FXML
    void txtFFBInputOnAction(ActionEvent event) {
        String ffbInput=txtFFBInput.getText();
        if (Regex.validateFFBInput(ffbInput)){
           // btnSearchStockOnAction(event);
        }else {
            txtFFBInput.clear();
            new Alert(Alert.AlertType.WARNING, "No Matching Input!!!").show();
        }
    }

    @FXML
    void btnDeleteStockOnAction(ActionEvent event) {
        if(txtStockId.getText().isEmpty() || txtFFBInput.getText().isEmpty() || dtpckrDate.getValue() == null || lblTime.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please Input Stock ID and Search Stock is exist").show();
        }else {
            String stockId = txtStockId.getText();

            boolean isDeleted;

            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Delete?", yes, no).showAndWait();

            if (buttonType.get() == yes) {
                txtStockId.setText(tblStockDetails.getSelectionModel().getSelectedItem().getStockId());
                try {

                    isDeleted = StockModel.deleteStock(stockId);
                    if (isDeleted) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Stock Deleted Successfully").show();
                        //int ffbInput = StockModel.searchByStockIdFFBInput(stockId);
                    /*String ffbInputOilQty = OilProductionFormController.ffbInputOilQty(ffbInput);
                    OilProductionModel.subtractionOilQty(Double.parseDouble(ffbInputOilQty));*/
                        txtStockId.clear();
                        txtFFBInput.clear();
                        cmbSupplierId.getItems().clear();
                        tblStockDetails.getItems().clear();
                        getAllStocksToTable("");
                        loadSupplierIds();

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
        txtStockId.clear();
        txtFFBInput.clear();
        cmbSupplierId.getItems().clear();
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        txtStockId.clear();
        txtStockId.requestFocus();
        btnAddStock.setText("Save Stock");
        loadSupplierIds();
    }

    @FXML
    void btnGetReportOnAction(ActionEvent event) {
        Thread t1=new Thread(
                () -> {
                    String reportPath = "E:\\1.GDSE\\1st Semester\\9.My Final Project-1st Semester\\AEN Palm Oil Factory Project\\production-and-fuel-management-system\\src\\main\\resources\\reports\\stockDetailsReport.jrxml";
                    String sql="select * from ffbstock";
                    String path = FileSystems.getDefault().getPath("/reports/stockDetailsReport.jrxml").toAbsolutePath().toString();
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
