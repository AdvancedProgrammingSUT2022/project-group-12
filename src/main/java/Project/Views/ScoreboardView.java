package Project.Views;

import Project.Models.Database;
import Project.Models.User;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ScoreboardView implements ViewController {
    public VBox box1;
    public ScrollPane pane;

    public void initialize() {
        box1 = new VBox();
        box1.setSpacing(10);
        showList(new ArrayList<>(Database.getInstance().getUsers()));
        box1.setStyle("-fx-background-color: transparent;");
        pane.setContent(box1);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
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
            user.setStyle("-fx-font-size: 15;");
            if (i == 0)
                user.setStyle("-fx-fill: red; -fx-font-size: 15");
            if (i == 1)
                user.setStyle("-fx-fill: blue; -fx-font-size: 15");
            if (i == 2)
                user.setStyle("-fx-fill: green; -fx-font-size: 15");
            box1.getChildren().add(user);
        }
    }

    public void exitClicked() {
        System.exit(0);
    }

    public void backClicked() {
        MenuStack.getInstance().popMenu();
    }
}
