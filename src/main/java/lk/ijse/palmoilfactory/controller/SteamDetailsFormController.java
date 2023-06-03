package lk.ijse.palmoilfactory.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import lk.ijse.palmoilfactory.db.DBConnection;
import lk.ijse.palmoilfactory.model.SteamModel;
import lk.ijse.palmoilfactory.model.StockModel;
import lk.ijse.palmoilfactory.model.SupplierModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.nio.file.FileSystems;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class SteamDetailsFormController implements Initializable {
    @FXML
    private JFXTextField txtFruitOutput;

    @FXML
    private JFXTextField txtStockDate;

    @FXML
    private JFXTextField txtStockTime;

    @FXML
    private JFXComboBox<String> cmbStockId;

    @FXML
    private JFXTextField txtEmptyBunchOutput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadStockIds();
    }

    private void loadStockIds() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> iDs = SteamModel.getStockIDs();

            Collections.sort(iDs);

            for (String id : iDs) {
                obList.add(id);
            }
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

            double fruitOutput=ffbinput*0.3;
            txtFruitOutput.setText(String.valueOf(fruitOutput));
            double emptyBunchoutput=ffbinput*0.7;
            txtEmptyBunchOutput.setText(String.valueOf(emptyBunchoutput));

            String date= StockModel.searchByStockIdDate(stockId);
            txtStockDate.setText(date);

            String time = StockModel.searchByStockIdTime(stockId);
            txtStockTime.setText(time);

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
            txtFruitOutput.clear();
            txtEmptyBunchOutput.clear();
            loadStockIds();
        }
    }
    @FXML
    void btnGetReportOnAction(ActionEvent event) {
        Thread t1=new Thread(
                () -> {
                    String reportPath = "E:\\1.GDSE\\1st Semester\\9.My Final Project-1st Semester\\AEN Palm Oil Factory Project\\production-and-fuel-management-system\\src\\main\\resources\\reports\\steamDetailsReport.jrxml";
                    String sql="select * from steam";
                    String path = FileSystems.getDefault().getPath("/reports/steamDetailsReport.jrxml").toAbsolutePath().toString();
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
