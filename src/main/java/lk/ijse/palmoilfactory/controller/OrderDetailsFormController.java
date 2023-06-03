package lk.ijse.palmoilfactory.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.palmoilfactory.db.DBConnection;
import lk.ijse.palmoilfactory.dto.Orders;
import lk.ijse.palmoilfactory.dto.tm.OrderTM;
import lk.ijse.palmoilfactory.model.OilProductionModel;
import lk.ijse.palmoilfactory.model.OrderModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.nio.file.FileSystems;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class OrderDetailsFormController implements Initializable {
    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOilQuantityOnHand;

    @FXML
    private TableView<OrderTM> tblOrderDetails;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colOrderDate;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private DatePicker dtpckrOrdersDate;

    private ObservableList<OrderTM> obList = FXCollections.observableArrayList();

    private String text="";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> txtQty.requestFocus());

        setOrderDate();
        generateNextOrderId();
        setCellValueFactory(); //To show table data
        getOrderDetailToTable(text);  //To get all orders details to table(Not show)

        try {
            lblOilQuantityOnHand.setText(OilProductionModel.getUpdatedOilqty());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        dtpckrOrdersDate.setOnAction(actionEvent -> { //Add action listener to dtpckrOrdersDate to search and display table
            tblOrderDetails.getItems().clear();
            text= String.valueOf(dtpckrOrdersDate.getValue());
            getOrderDetailToTable(text);
        });

    }

    private void setOrderDate() {
        lblOrderDate.setText(String.valueOf(LocalDate.now()));
    }

    private void generateNextOrderId() {
        try {
            String nextId = OrderModel.generateNextOrderId();
            lblOrderId.setText(nextId);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Something Happened!").show();
        }
    }

    private void setCellValueFactory() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId")); //OrderTM class attributes names
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void getOrderDetailToTable(String text) {
        try {
            List<Orders> orderList = OrderModel.getAll();
            for(Orders orders : orderList) {
                if (orders.getOrderDate().contains(text) ){  //Check pass text contains of the order date

                    OrderTM tm=new OrderTM(
                            orders.getOrderId(),
                            orders.getOrderDate(),
                            orders.getQuantity(),
                            orders.getPrice());

                    obList.add(tm);

                }
            }
            tblOrderDetails.setItems(obList);

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }
    private boolean checkOilQty(double qty) throws SQLException, ClassNotFoundException {
        try {
            String totalOilQuantity=OilProductionModel.getUpdatedOilqty();
            double value= Double.parseDouble(totalOilQuantity);
            if (value>=qty){
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
           e.printStackTrace();
         } catch (ClassNotFoundException e) {
          e.printStackTrace();
       }
        return false;
      }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

        String orderId = lblOrderId.getText();
        String orderDate = lblOrderDate.getText();
        double qty = Double.parseDouble(txtQty.getText());
        double price = Double.parseDouble(txtPrice.getText());

        boolean isPlaced;

            try {
                    if(checkOilQty(qty)==true){
                        isPlaced = OrderModel.placeOrder(orderId, orderDate, qty, price); //transaction --> autoCommit(false)
                        if (isPlaced) {
                            tblOrderDetails.getItems().clear();
                            new Alert(Alert.AlertType.CONFIRMATION, "Order Added and Place Order").show();
                            clearFields();
                            txtQty.requestFocus();
                            generateNextOrderId();
                            lblOilQuantityOnHand.setText(OilProductionModel.getUpdatedOilqty());
                            //    OilProductionModel.updateQty(qty);

                            getOrderDetailToTable("");

                        } else {
                            new Alert(Alert.AlertType.WARNING, "Order Not Added Please Try Again").show();
                            return;
                        }
                    }else{
                        new Alert(Alert.AlertType.WARNING, "Not suffieicent oil quantitiy to place order").show();
                        clearFields();
                        txtQty.requestFocus();
                    }


            } catch (SQLException | ClassNotFoundException e) {
                   new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
            }
    }

    private void clearFields() {
        txtQty.clear();
        txtPrice.clear();
    }

    @FXML
    void txtPriceOnAction(ActionEvent event) {
        btnPlaceOrderOnAction(event);
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        txtPrice.requestFocus();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtQty.clear();
        txtPrice.clear();
        dtpckrOrdersDate.getEditor().clear();
        tblOrderDetails.getItems().clear();
        getOrderDetailToTable("");
        txtQty.requestFocus();
    }

    @FXML
    void btnGetReportOnAction(ActionEvent event) {
        Thread t1=new Thread(
                () -> {
                    String reportPath = "E:\\1.GDSE\\1st Semester\\9.My Final Project-1st Semester\\AEN Palm Oil Factory Project\\production-and-fuel-management-system\\src\\main\\resources\\reports\\orderDetailReport.jrxml";
                    String sql="select * from orders";
                    String path = FileSystems.getDefault().getPath("/reports/orderDetailReport.jrxml").toAbsolutePath().toString();
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
