package Project.Views;

import Project.Models.Database;
import Project.Models.User;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ScoreboardView {
    public ImageView userAvatar;
    public VBox box1;
    public VBox box2;
    public VBox box3;
    public VBox box4;
    @FXML
    private Text text;

    public void initialize() {
        showList(new ArrayList<>(Database.getInstance().getUsers()));
    }

    private void showList(ArrayList<User> users) {
        Text user;
        for (int i = 0; i < Math.min(40, users.size()); i++) {
            StringBuilder userInfo = new StringBuilder("  ");
            if (users.get(i).getUsername().equals(MenuStack.getInstance().getUser().getUsername()))
                userInfo.append("* ");
            userInfo.append(i + 1).append(" - ").append(users.get(i).getUsername());
            if (users.get(i).getUsername().equals(MenuStack.getInstance().getUser().getUsername()))
                userInfo.append(" *");
            userInfo.append("  ");
            user = new Text(userInfo.toString());
            user.setStyle("-fx-font-size: 10;");
            if (i == 0)
                user.setStyle("-fx-fill: red; -fx-font-size: 10");
            if (i == 1)
                user.setStyle("-fx-fill: blue; -fx-font-size: 10");
            if (i == 2)
                user.setStyle("-fx-fill: green; -fx-font-size: 10");
            if (i % 5 == 0)
                box1.getChildren().add(user);
            else if (i % 5 == 1)
                box2.getChildren().add(user);
            else if (i % 5 == 3)
                box3.getChildren().add(user);
            else if (i % 5 == 4)
                box4.getChildren().add(user);
        }
    }

    public void exitClicked() {
        System.exit(0);
    }

    public void backClicked() {
        MenuStack.getInstance().popMenu();
    }
}
