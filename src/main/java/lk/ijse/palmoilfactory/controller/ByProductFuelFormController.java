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

public class ByProductFuelFormController implements Initializable {
    @FXML
    private JFXTextField txtTotalPressFiber;

    @FXML
    private JFXTextField txtStockDate;

    @FXML
    private JFXTextField txtStockTime;

    @FXML
    private JFXComboBox<String> cmbStockId;

    @FXML
    private JFXTextField txtTotalShell;

    @FXML
    private JFXTextField txtTotalEBFiber;

    @FXML
    private JFXTextField txtTotalFuel;

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

            double totalPressFiber=ffbinput*0.135;
            txtTotalPressFiber.setText(String.valueOf(totalPressFiber));
            double totalShell=ffbinput*0.03;
            txtTotalShell.setText(String.valueOf(totalShell));
            double totalEBFiber=ffbinput*0.03;
            txtTotalEBFiber.setText(String.valueOf(totalEBFiber));

            String date= StockModel.searchByStockIdDate(stockId);
            txtStockDate.setText(date);

            String time = StockModel.searchByStockIdTime(stockId);
            txtStockTime.setText(time);

            double totalFuel = totalPressFiber+totalShell+totalEBFiber;
            txtTotalFuel.setText(Double.toString(totalFuel));

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
            txtTotalEBFiber.clear();
            txtTotalShell.clear();
            txtTotalPressFiber.clear();
            txtTotalFuel.clear();
            loadStockIds();
        }
    }


    @FXML
    void btnGetReportOnAction(ActionEvent event) {
        Thread t1=new Thread(
                () -> {
                    String reportPath = "E:\\1.GDSE\\1st Semester\\9.My Final Project-1st Semester\\AEN Palm Oil Factory Project\\production-and-fuel-management-system\\src\\main\\resources\\reports\\byProductionFuelDetailsReport.jrxml";
                    String sql="select * from byproductfuel";
                    String path = FileSystems.getDefault().getPath("/reports/byProductionFuelDetailsReport.jrxml").toAbsolutePath().toString();
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
