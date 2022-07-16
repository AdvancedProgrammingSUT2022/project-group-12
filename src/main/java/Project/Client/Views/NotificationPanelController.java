package Project.Client.Views;

import Project.Client.Views.ViewController;
import Project.Enums.ResourceEnum;
import Project.Models.Civilization;
import Project.Models.Notification;
import Project.Models.Trade;
import Project.Server.Controllers.GameController;
import Project.Server.Views.RequestHandler;
import Project.Utils.CommandResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class NotificationPanelController implements ViewController {


    @FXML
    private VBox notificationPanel;

    public void initialize() {
        Civilization currentCiv = GameController.getGame().getCurrentCivilization();
        Civilization nextCiv = GameController.getGame().getNextCivilization();
        for (Notification notif :
                currentCiv.getNotifications()) {
            HBox hBox = new HBox(new Text(notif.getMessage()));
            hBox.setAlignment(Pos.CENTER_LEFT);
            if (notif instanceof Trade trade) {
                Button reject = new Button("reject");
                Button accept = new Button("accept");
                reject.setOnAction(e -> {
                    String command = "trade reject -n " + trade.getName();
                    CommandResponse commandResponse = RequestHandler.getInstance().handle(command);
                    notificationPanel.getChildren().remove(hBox);
                });
                accept.setOnAction(e -> {
                    String command = "trade accept -n " + trade.getName();
                    CommandResponse commandResponse = RequestHandler.getInstance().handle(command);
                    notificationPanel.getChildren().remove(hBox);
                });
                VBox vBox = new VBox(reject, accept);
                vBox.setSpacing(5);
                hBox.getChildren().addAll(vBox);
                hBox.setSpacing(20);
            }
            notificationPanel.getChildren().add(hBox);
        }
//        Button testing = new Button("test");
//        testing.setOnAction(e -> {
//            System.out.println(currentCiv.getResources());
//        });
//        notificationPanel.getChildren().add(testing);

    }

    public void back() {
        MenuStack.getInstance().popMenu();
    }
}
