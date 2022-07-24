package Client.Views;

import Client.Utils.DatabaseQuerier;
import Project.Models.User;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.text.SimpleDateFormat;

public class UserDialog extends Dialog<String> {
    private Text score;
    private Text name;
    private ImageView avatar;
    private User user;
    private boolean isOk;
    private boolean isOnline;
    private Text loginDate;

    public UserDialog(User user) {
        this.user = user;
        score = new Text("Score: " + user.getScore());
        score.setWrappingWidth(50);
        score.setTextAlignment(TextAlignment.CENTER);

        avatar = new ImageView(AvatarView.getAvatarImage(user.getAvatarURL()));
        avatar.setFitHeight(15);
        avatar.setFitWidth(15);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        avatar.setOnMouseClicked(mouseEvent -> DatabaseQuerier.sendFriendRequest(MenuStack.getInstance().getUser().getUsername(),
                user.getUsername()));
        loginDate = new Text("Last Login: " + (user.getLastLoginDate() == null ? "N/A" : simpleDateFormat.format(user.getLastLoginDate())));
        loginDate.setTextAlignment(TextAlignment.CENTER);
        loginDate.setWrappingWidth(150);
        boolean online = DatabaseQuerier.getIsUsernameOnline(user.getUsername());
        loginDate.setStyle("-fx-fill:" + (online ? "green" : "black"));
        loginDate.setText((online ? "online " : "offline ") + loginDate.getText());
        name = new Text(user.getUsername());
        name.setWrappingWidth(110);
        buildUI();
    }

    public void buildUI() {
        isOk = false;
        BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox();
        vBox.setPrefHeight(100);
        vBox.setSpacing(10);
        HBox hBox = new HBox();
        hBox.getChildren().add(avatar);
        hBox.getChildren().add(name);
        hBox.setSpacing(20);
        vBox.getChildren().add(hBox);
        vBox.getChildren().add(score);
        vBox.getChildren().add(loginDate);
        this.getDialogPane().setContent(vBox);
        Text note = new Text("For sending request click yes, and for canceling the process click no");
        vBox.getChildren().add(note);
        this.getDialogPane().getButtonTypes().add(ButtonType.YES);
        this.getDialogPane().getButtonTypes().add(ButtonType.NO);
        this.setTitle("Friend Accept Page");
        Button yesBtn = (Button) getDialogPane().lookupButton(ButtonType.YES);
        Button noBtn = (Button) getDialogPane().lookupButton(ButtonType.NO);
        yesBtn.addEventFilter(ActionEvent.ACTION, event -> isOk = true);
        noBtn.addEventFilter(ActionEvent.ACTION, event -> isOk = false);
    }

    public boolean isOk() {
        return isOk;
    }
}
