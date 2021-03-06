package Client.Views;

import Client.Utils.RequestSender;
import Project.Utils.CommandResponse;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class MainMenuView implements ViewController {

    @FXML
    public Text welcomeText;

    public void initialize() {
        welcomeText.setText("Welcome, " + MenuStack.getInstance().getUser().getNickname());
    }

    public void logout() {
        String command = "logout";
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        MenuStack.getInstance().popMenu();
    }

    public void gotoScoreboard() {
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("ScoreboardPage"));
    }

    public void gotoProfileMenu() {
        String command = "goto profile menu";
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("ProfilePage"));
    }

    public void gotoChatMenu() {
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("ChatSelectPage"));
    }

    public void gotoSelectGame() {
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("StartGamePage"));

    }
}