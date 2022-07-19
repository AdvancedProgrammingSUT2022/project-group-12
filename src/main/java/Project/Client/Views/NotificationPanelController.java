package Project.Client.Views;

import Project.Client.Utils.DatabaseQuerier;
import Project.Models.*;
import Project.Server.Views.RequestHandler;
import Project.Utils.CommandResponse;
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
        System.out.println("before get");
        DatabaseQuerier.getCurrentCivNotifications();
        for (Notification notif :
                DatabaseQuerier.getCurrentCivNotifications()) {
            HBox hBox = new HBox(new Text(notif.getMessage()));
            hBox.setAlignment(Pos.CENTER_LEFT);
            if(notif instanceof DeclareWar declareWar){
                initalizeDeclareWarBoxes(hBox,declareWar);
            } else {
                initalizeDemandAndTradeAndPeaceBoxes(hBox,notif);
            }
            notificationPanel.getChildren().add(hBox);
        }
//        Button testing = new Button("test");
//        testing.setOnAction(e -> {
//            System.out.println(currentCiv.getResources());
//        });
//        notificationPanel.getChildren().add(testing);

    }


    private void initalizeDeclareWarBoxes(HBox hBox, DeclareWar declareWar) {
        Button ok = new Button("Ok");
        ok.setOnAction(e -> {
            String command = "declare war seen -n " + declareWar.getName();
            CommandResponse response = RequestHandler.getInstance().handle(command);
            notificationPanel.getChildren().remove(hBox);
        });
        VBox vBox = new VBox(ok); vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);
        hBox.getChildren().addAll(vBox);
        hBox.setSpacing(20);
    }

    private void initalizeDemandAndTradeAndPeaceBoxes(HBox hBox, Notification notif) {
        Button reject = new Button("reject");
        Button accept = new Button("accept");
        reject.setOnAction(e -> {
            String command = null;
            if(notif instanceof Demand) {
                command = "demand reject -d " + notif.getName();
            } else if (notif instanceof Peace) {
                command = "peace reject -n " + notif.getName();
            } else if (notif instanceof Trade) {
                command = "trade reject -n " + notif.getName();
            }
            CommandResponse response = RequestHandler.getInstance().handle(command);
            if(!response.isOK()){
                MenuStack.getInstance().showError(response.toString());
                return;
            } else {
                MenuStack.getInstance().showSuccess(response.getMessage());
            }
            notificationPanel.getChildren().remove(hBox);
        });
        accept.setOnAction(e -> {
            String command = null;
            if(notif instanceof Demand) {
                command = "demand accept -d " + notif.getName();
            } else if (notif instanceof Peace) {
                command = "peace accept -n " + notif.getName();
            } else if (notif instanceof Trade) {
                command = "trade accept -n " + notif.getName();
            }
            CommandResponse response = RequestHandler.getInstance().handle(command);
            if(!response.isOK()){
                MenuStack.getInstance().showError(response.toString());
                return;
            } else {
                MenuStack.getInstance().showSuccess(response.getMessage());
            }
            notificationPanel.getChildren().remove(hBox);
        });
        VBox vBox = new VBox(reject, accept);
        vBox.setSpacing(5);
        hBox.getChildren().addAll(vBox);
        hBox.setSpacing(20);
    }


    public void back() {
        MenuStack.getInstance().popMenu();
    }
}
