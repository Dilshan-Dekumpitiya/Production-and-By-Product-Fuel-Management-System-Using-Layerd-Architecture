package lk.ijse.palmoilfactory.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.palmoilfactory.dto.Stock;
import lk.ijse.palmoilfactory.model.OilProductionModel;
import lk.ijse.palmoilfactory.model.StockModel;
import lk.ijse.palmoilfactory.model.SupplierModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;


public class DashboardFormController implements Initializable {
    public Pane dashboardContext;
    public AnchorPane dashboardDetailsPane;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblTotalOilQtyOnHand;

    @FXML
    private Label lblGreeting;

    @FXML
    private BarChart<String, Double>  barChartStockvsOilFuel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateTimeInit();

        try {
            lblTotalOilQtyOnHand.setText(OilProductionModel.getUpdatedOilqty());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String totaloil = null;
        try {
            totaloil = OilProductionModel.getUpdatedOilqty();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        lblTotalOilQtyOnHand.setText(totaloil);

        LocalTime currentTime = LocalTime.now();

        // Extract the hour and minute from the current time
        int hour = currentTime.getHour();
    //    int minute = currentTime.getMinute();

        // Display a greetings message based on the time of day

        if (hour >= 0 && hour < 12) {
            lblGreeting.setText("Good Morning !!!");
        } else if (hour >= 12 && hour < 17) {
            lblGreeting.setText("Good Afternoon !!!");
        } else {
            lblGreeting.setText("Good Evening !!!");
        }

        // Chart Initialize --> Oil Production
        XYChart.Series seriesOil = new XYChart.Series<>();
        seriesOil.setName("Oil Production Quantity");

        //By product fuel chart
        XYChart.Series seriesByProduct = new XYChart.Series<>();
        seriesByProduct.setName("By Product Fuel Quantity");

        // Add data points

        try {
         //   int stockIdCount = StockModel.getStockIdsCount();
            List<String> stockIds = StockModel.getStockIds();

            Collections.sort(stockIds);

            for (int i = 0; i < stockIds.size(); i++) {
                String stockId = stockIds.get(i);
                String totalOileveryStock1=OilProductionModel.getTotalOileveryStock(stockId);
                Double totalOileveryStock= Double.valueOf(totalOileveryStock1);

                String totalfueleveryStock1=OilProductionModel.getTotalFueleveryStock(stockId);
                Double totalfueleveryStock= Double.valueOf(totalfueleveryStock1);

                seriesOil.getData().add(new XYChart.Data<>(stockId, totalOileveryStock));
                seriesByProduct.getData().add(new XYChart.Data<>(stockId, totalfueleveryStock));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Add the data series to the bar chart
        barChartStockvsOilFuel.getData().addAll(seriesOil,seriesByProduct);
       // barChartStockvsOilFuel.setTitle("Stock Vs Oil Production and By-Product Fuel");
      //  barChartStockvsOilFuel.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc;");
    }

    private void dateTimeInit() {
        //set time
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")))));
        lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a")));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        //set date
        Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(1), event -> lblDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))));
        lblDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        timeline2.setCycleCount(Animation.INDEFINITE);
        timeline2.play();
    }

    private void setUI(String location) throws IOException {

        Parent load = FXMLLoader.load(getClass().getResource(location));
        dashboardDetailsPane.getChildren().clear();
        dashboardDetailsPane.getChildren().add(load);

    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        Stage thisStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader= new FXMLLoader(LoginFormController.class.getResource("/view/dashboard-view-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        thisStage.setScene(scene);
        thisStage.setMaximized(true);
    }

    public void btnSupplierDetailOnAction(ActionEvent actionEvent) throws IOException {
        setUI("/view/supplier-details-form.fxml");
    }

    @FXML
    void btnStockDetailsOnAction(ActionEvent event) throws IOException {
        setUI("/view/stock-details-form.fxml");
    }

    @FXML
    void btnSteamOnAction(ActionEvent event) throws IOException {
        setUI("/view/steam-details-form.fxml");
    }

    @FXML
    void btnOilProductionOnAction(ActionEvent event) throws IOException {
        setUI("/view/oil-production-form.fxml");
    }

    @FXML
    void btnByProductFuelOnAction(ActionEvent event) throws IOException {
        setUI("/view/by-product-fuel-form.fxml");
    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        setUI("/view/employee-details-form.fxml");
    }

    @FXML
    void btnOrderDetailsOnAction(ActionEvent event) throws IOException {
        setUI("/view/order-details-form.fxml");
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login-view-form.fxml"))));

        stage.centerOnScreen();
        stage.show();
    }
}
