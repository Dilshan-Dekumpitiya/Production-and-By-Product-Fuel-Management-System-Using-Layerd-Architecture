package lk.ijse.palmoilfactory.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import lk.ijse.palmoilfactory.model.ScheduleModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ScheduleDetailsFormController implements Initializable {

    @FXML
    private Pane scheduleDetailsPane;

    @FXML
    private JFXComboBox<String> cmbSchId;

    @FXML
    private JFXComboBox<String> cmbTimeRange;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadScheduleIds();
        loadTimeRange();
    }

    private void loadTimeRange() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> timeRanges = ScheduleModel.getTimeRange();

            for (String timeRange : timeRanges) {
                obList.add(timeRange);
            }
            cmbTimeRange.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
        }

    }

    private void loadScheduleIds() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> scheduleIds = ScheduleModel.getScheduleIds();

            for (String scheduleId : scheduleIds) {
                obList.add(scheduleId);
            }
            cmbSchId.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
        }

    }

    @FXML
    void cmbSchIdOnAction(ActionEvent event) {
        String schId = cmbSchId.getSelectionModel().getSelectedItem();
        try {

            String timeRange = ScheduleModel.searchBySchIdTimeRange(schId);

            cmbTimeRange.setValue(timeRange);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @FXML
    void cmbTimeRange(ActionEvent event) {
        String timeRange = cmbTimeRange.getSelectionModel().getSelectedItem();
        try {

            String schId = ScheduleModel.searchByTimeRangeSchId(timeRange);

            cmbSchId.setValue(schId);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        scheduleDetailsPane.setVisible(false);
    }

}
