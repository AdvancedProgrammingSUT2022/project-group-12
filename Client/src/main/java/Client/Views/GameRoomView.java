package Client.Views;

import Client.Utils.DatabaseQuerier;
import Project.Models.OpenGame;
import Project.Models.User;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class GameRoomView implements ViewController {
    private @FXML Text gameNameText;
    private @FXML Text adminNameText;
    private @FXML Text limitText;
    private @FXML VBox playersList;
    private OpenGame openGame;

    private String getTitleOfUser(User user) {
        return user.getNickname() + " (" + user.getUsername() + ")";
    }

    public void initialize() {
        openGame = MenuStack.getInstance().getCookies().getOpenRoom();
        this.gameNameText.setText(openGame.getName());
        this.adminNameText.setText(getTitleOfUser(openGame.getAdmin()));
        this.limitText.setText(String.valueOf(openGame.getPlayerLimit()));
        this.loadPlayersListFrom(openGame.getPlayers());
    }

    private void loadPlayersListFrom(ArrayList<User> players) {
        this.playersList.getChildren().clear();
        for (User player : players) {
            Text text = new Text(getTitleOfUser(player));
            this.playersList.getChildren().add(text);
        }
    }

    public void startGame() {
        if (openGame.getPlayers().size() < 2) return;
        StringBuilder command = new StringBuilder("start game -t " + openGame.getToken());
    }

    public void openChatRoom() {

    }

    public void leaveGame() {
        String loginToken = MenuStack.getInstance().getCookies().getLoginToken();
        DatabaseQuerier.leaveFromRoom(loginToken, openGame.getToken());
        MenuStack.getInstance().popMenu();
    }
}
