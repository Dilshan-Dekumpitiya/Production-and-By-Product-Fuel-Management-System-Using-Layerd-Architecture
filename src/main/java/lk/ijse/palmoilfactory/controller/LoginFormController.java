package lk.ijse.palmoilfactory.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.palmoilfactory.bo.BOFactory;
import lk.ijse.palmoilfactory.bo.custom.UserBO;
import lk.ijse.palmoilfactory.dto.UserDTO;
import lk.ijse.palmoilfactory.util.Regex;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {

    public AnchorPane loginContext;

    public JFXTextField txtUsername;

    @FXML
    private JFXPasswordField txtPassword;

    private UserBO userBO= BOFactory.getInstance().getBO(BOFactory.BOTypes.USER);

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
        String userId="";

        if(Regex.validateUsername(username)&&Regex.validatePassword(password)){

            try {
                boolean isUserVerified = userBO.userCheckedInDB(new UserDTO(userId,username,password));//LoginModel.userCheckedInDB(username,password); //check in the DB
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


