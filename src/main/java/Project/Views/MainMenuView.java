package Project.Views;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class MainMenuView {

    @FXML
    public Text welcomeText;

    public void initialize() {
        welcomeText.setText("Welcome, " + MenuStack.getInstance().getUser().getNickname());
    }

    public void logout() {
        MenuStack.getInstance().popMenu();
    }

    public void gotoScoreboard() {
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("ScoreboardPage"));
    }

    public void gotoProfileMenu() {
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("ProfilePage"));
    }

    public void gotoChatMenu() {
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("ChatSelectPage"));
    }
}
