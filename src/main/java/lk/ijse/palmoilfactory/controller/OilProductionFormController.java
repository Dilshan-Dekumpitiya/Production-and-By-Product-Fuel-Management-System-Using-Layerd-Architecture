package lk.ijse.palmoilfactory.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.ijse.palmoilfactory.db.DBConnection;
import lk.ijse.palmoilfactory.model.OilProductionModel;
import lk.ijse.palmoilfactory.model.SteamModel;
import lk.ijse.palmoilfactory.model.StockModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.nio.file.FileSystems;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class OilProductionFormController implements Initializable {
    @FXML
    private JFXTextField txtTotalEBLiquidOutput;

    @FXML
    private JFXTextField txtStockDate;

    @FXML
    private JFXTextField txtStockTime;

    @FXML
    private JFXComboBox<String> cmbStockId;

    @FXML
    private JFXTextField txtTotalPressLiquid;

    @FXML
    private JFXTextField txtTotalOilOutput;

    @FXML
    private Label lblTotalOilQtyOnHand;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadStockIds();

        try {
            lblTotalOilQtyOnHand.setText(OilProductionModel.getUpdatedOilqty());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getTotalOilQtyOnAllStockIDs() {

        try {

            double ffbinput = StockModel.getTotalFFBInput();

            double totalPressLiquid=ffbinput*0.3*0.88;

            double totalEBLiquidOutput=ffbinput*0.7*0.72;

            String totalOilOutput = Double.toString(totalPressLiquid+totalEBLiquidOutput);


            return totalOilOutput;

        } catch (SQLException e) {
             new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        } catch (ClassNotFoundException e) {
             new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
        return null;
    }

    public static String ffbInputOilQty(double ffbInput) {

        double totalPressLiquid=ffbInput*0.3*0.88;

        double totalEBLiquidOutput=ffbInput*0.7*0.72;

        String oilOutput = Double.toString(totalPressLiquid+totalEBLiquidOutput);

        return oilOutput;

    }

    private void loadStockIds() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> iDs = SteamModel.getStockIDs();
           // obList.addAll(iDs);

            Collections.sort(iDs);
            for (String id : iDs) {
                obList.add(id);
            }

            /*for (String id : iDs) {
                if (id.equals("FFB001")) {
                    obList.add(0, id); // Add "FFB001" to the beginning of the list
                } else {
                    obList.add(id); // Add all other IDs to the end of the list
                }
            }
*/
            cmbStockId.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
        }
    }

    @FXML
    void cmbStockIdOnAction(ActionEvent event) {
        String stockId = cmbStockId.getSelectionModel().getSelectedItem();
        try {

            double ffbinput = StockModel.searchByStockIdFFBInput(stockId);

            double totalPressLiquid=ffbinput*0.3*0.88;
            txtTotalPressLiquid.setText(String.valueOf(totalPressLiquid));
            double totalEBLiquidOutput=ffbinput*0.7*0.72;
            txtTotalEBLiquidOutput.setText(String.valueOf(totalEBLiquidOutput));

            String date= StockModel.searchByStockIdDate(stockId);
            txtStockDate.setText(date);

            String time = StockModel.searchByStockIdTime(stockId);
            txtStockTime.setText(time);

            String totalOilOutput = Double.toString(totalPressLiquid+totalEBLiquidOutput);
            txtTotalOilOutput.setText(totalOilOutput);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        if(cmbStockId.getSelectionModel().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "No Stock ID Selected! Please Select Stock ID").show();
        }else {
            cmbStockId.getItems().clear();
            txtStockTime.clear();
            txtStockDate.clear();
            txtTotalEBLiquidOutput.clear();
            txtTotalPressLiquid.clear();
            txtTotalOilOutput.clear();
            loadStockIds();
        }
    }

    @FXML
    void btnGetReportOnAction(ActionEvent event) {
        Thread t1=new Thread(
                () -> {
                    String reportPath = "E:\\1.GDSE\\1st Semester\\9.My Final Project-1st Semester\\AEN Palm Oil Factory Project\\production-and-fuel-management-system\\src\\main\\resources\\reports\\oilProductionDetailsReport.jrxml";
                    String sql="select * from oilproduction";
                    String path = FileSystems.getDefault().getPath("/reports/oilProductionDetailsReport.jrxml").toAbsolutePath().toString();
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
