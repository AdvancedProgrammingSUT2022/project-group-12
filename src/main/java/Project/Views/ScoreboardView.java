package Project.Views;

import Project.Models.Database;
import Project.Models.User;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ScoreboardView implements ViewController {
    private VBox usersBox;
    @FXML
    private ScrollPane pane;

    public void initialize() {
        usersBox = new VBox();
        usersBox.setSpacing(10);
        showList(new ArrayList<>(Database.getInstance().getUsers()));
        usersBox.setStyle("-fx-background-color: transparent;");
        pane.setContent(usersBox);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    }

    private void showList(ArrayList<User> users) {
        Text user;
        for (int i = 0; i < users.size(); i++) {
            StringBuilder userInfo = new StringBuilder("  ");
            if (users.get(i).getUsername().equals(MenuStack.getInstance().getUser().getUsername()))
                userInfo.append("* ");
            userInfo.append(i + 1).append(" - ").append(users.get(i).getUsername());
            if (users.get(i).getUsername().equals(MenuStack.getInstance().getUser().getUsername()))
                userInfo.append(" *");
            userInfo.append("  ");
            user = new Text(userInfo.toString());
            user.setStyle("-fx-font-size: 15;");
            if (users.get(i).getUsername().equals(MenuStack.getInstance().getUser().getUsername()))
                user.setStyle("-fx-fill: white; -fx-font-size: 15;");
            if (i == 0)
                user.setStyle("-fx-fill: gold; -fx-font-size: 15;");
            if (i == 1)
                user.setStyle("-fx-fill: silver; -fx-font-size: 15;");
            if (i == 2)
                user.setStyle("-fx-fill: brown; -fx-font-size: 15;");
            usersBox.getChildren().add(user);
        }
    }

    public void exitClicked() {
        System.exit(0);
    }

    public void backClicked() {
        MenuStack.getInstance().popMenu();
    }
}
