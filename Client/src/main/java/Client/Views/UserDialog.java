package Client.Views;

import Client.Utils.DatabaseQuerier;
import Project.Models.User;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class UserDialog extends Dialog<String> {
    Text score;
    Text name;
    ImageView avatar;

    public UserDialog(User user) {
        score = new Text("Score: " + user.getScore());
        score.setWrappingWidth(50);
        score.setTextAlignment(TextAlignment.CENTER);

        avatar = new ImageView(AvatarView.getAvatarImage(user.getAvatarURL()));
        avatar.setFitHeight(15);
        avatar.setFitWidth(15);
        avatar.setOnMouseClicked(mouseEvent -> DatabaseQuerier.sendFriendRequest(MenuStack.getInstance().getUser().getUsername(),
                user.getUsername()));

        name = new Text(user.getUsername());
        name.setWrappingWidth(110);
        buildUI();
    }
    public void buildUI() {
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
        this.getDialogPane().setContent(vBox);
        this.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
    }
}
