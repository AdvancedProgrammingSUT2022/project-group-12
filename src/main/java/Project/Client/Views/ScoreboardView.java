package Project.Client.Views;

import Project.Client.Utils.DatabaseQuerier;
import Project.Models.User;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ScoreboardView implements ViewController {
    @FXML
    private ScrollPane pane;

    public void initialize() {
        VBox vBox = new VBox();
//        vBox.setSpacing(10);
        vBox.setLayoutY(100);
        ArrayList<User> users = DatabaseQuerier.getAllUsers();
        users.sort(User::comparator);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        for (int i = -1; i < users.size(); ++i) {
            User user = i == -1 ? null : users.get(i);
            HBox hBox = new HBox();
            hBox.setSpacing(5);
            hBox.setPadding(new Insets(5));

            Text rank = new Text(i >= 0 ? String.valueOf(i + 1) : "Rank");
            rank.setWrappingWidth(50);
            rank.setTextAlignment(TextAlignment.CENTER);
            hBox.getChildren().add(rank);

            if (i >= 0) {
                ImageView avatar = new ImageView(user.getImage());
                avatar.setFitHeight(15);
                avatar.setFitWidth(15);
                hBox.getChildren().add(avatar);
            }

            Text name = new Text(i < 0 ? "User" : user.getUsername());
            name.setWrappingWidth((i < 0 ? 20 : 0) + 110);
            hBox.getChildren().add(name);

            Text score = new Text(i < 0 ? "Score" : String.valueOf(user.getScore()));
            score.setTextAlignment(TextAlignment.CENTER);
            score.setWrappingWidth(40);
            hBox.getChildren().add(score);

            Text lastWin = new Text(i < 0 ? "Last Win" : user.getLastWinDate() == null ? "N/A" : simpleDateFormat.format(user.getLastWinDate()));
            lastWin.setTextAlignment(TextAlignment.CENTER);
            lastWin.setWrappingWidth(150);
            hBox.getChildren().add(lastWin);

            Text loginDate = new Text(i < 0 ? "Last Login" : user.getLastLoginDate() == null ? "N/A" : simpleDateFormat.format(user.getLastLoginDate()));
            loginDate.setTextAlignment(TextAlignment.CENTER);
            loginDate.setWrappingWidth(150);
            hBox.getChildren().add(loginDate);

            double opacity = 0.5;
            if (i == -1 || user.getUsername().equals(MenuStack.getInstance().getUser().getUsername())) opacity = 0.9;

            String color = "255, 255, 255";
            if (i == 0) color = "255, 215, 0";
            else if (i == 1) color = "192, 192, 192";
            else if (i == 2) color = "210, 105, 30";
            hBox.setStyle("-fx-background-color: rgba(" + color + ", " + opacity + ");");
            vBox.getChildren().add(hBox);
        }
//        usersBox.setStyle("-fx-background-color: transparent;");
//        pane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        pane.setContent(vBox);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    public void exitClicked() {
        System.exit(0);
    }

    public void backClicked() {
        MenuStack.getInstance().popMenu();
    }
}
