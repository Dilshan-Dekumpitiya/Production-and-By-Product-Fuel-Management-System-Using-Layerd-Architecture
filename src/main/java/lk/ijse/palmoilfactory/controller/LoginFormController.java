package lk.ijse.palmoilfactory.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.sun.nio.sctp.Notification;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import lk.ijse.palmoilfactory.model.LoginModel;
import lk.ijse.palmoilfactory.util.Regex;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginFormController implements Initializable {

    public AnchorPane loginContext;

    public JFXTextField txtUsername;

    @FXML
    private JFXPasswordField txtPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> txtUsername.requestFocus());
    }

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {

        Stage stage = (Stage) loginContext.getScene().getWindow();

        
        Stage stage2 = new Stage();
        stage2.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard-view-form.fxml"))));
        stage2.setTitle("Dashboard");

        stage2.setMaximized(true);

        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if(Regex.validateUsername(username)&&Regex.validatePassword(password)){

            try {
                boolean isUserVerified = LoginModel.userCheckedInDB(username,password); //check in the DB
                if (isUserVerified) {

                    stage.close();

                    stage2.show();

                    Notifications notification = NotificationController.notification("Login Successful", "Login Alert");
                    notification.show();

                    Thread mailThread=new Thread(()->{
                        try {
                            EmailController.sendMail("aenpalmoilprocessingpvtltd@gmail.com");
                        } catch (Exception e) {
                           // System.out.println("Failed to send e-mail.Network err!");
                           //e.printStackTrace();
                            System.out.println(e);
                        }
                    });

                    mailThread.start();

                } else {
                    new Alert(Alert.AlertType.WARNING, "User Not Found in DB!!!").show();
                    txtUsername.clear();
                    txtPassword.clear();
                    txtUsername.requestFocus();
                }
            }catch (SQLException exception ){
                new Alert(Alert.AlertType.ERROR,"Oops something wrong!!!").show();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR,"Oops something wrong!!!").show();
            }

        }else {
            new Alert(Alert.AlertType.ERROR,"Invalid Input !").show();
            txtUsername.clear();
            txtPassword.clear();
            txtUsername.requestFocus();
            stage.show();
        }

    }

    @FXML
    void txtUsernameOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) throws IOException {
        btnLoginOnAction(event);
    }

}


