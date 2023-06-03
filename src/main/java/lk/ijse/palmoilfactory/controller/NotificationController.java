package lk.ijse.palmoilfactory.controller;

import com.sun.nio.sctp.Notification;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class NotificationController {
    public static Notifications notification(String text, String title) {
        Image image =new Image("/img/login-notification.png");
        Notifications notifications=Notifications.create();
        notifications.graphic(new ImageView(image));
        notifications.text(text);
        notifications.title(title);
        notifications.hideAfter(Duration.seconds(5));
        //notifications.darkStyle();
        notifications.show();
        notifications.position(Pos.TOP_RIGHT);
        return notifications;
    }
}
