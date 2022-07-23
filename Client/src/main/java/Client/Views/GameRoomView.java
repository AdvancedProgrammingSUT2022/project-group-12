package Client.Views;

import Client.Utils.DatabaseQuerier;
import Project.Models.OpenGame;
import Project.Models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class GameRoomView implements ViewController {
    private @FXML Text gameNameText;
    private @FXML Text adminNameText;
    private @FXML Text limitText;
    private @FXML VBox playersList;
    private @FXML Button startButton;
    private OpenGame openGame;

    private String getTitleOfUser(User user) {
        return user.getNickname() + " (" + user.getUsername() + ")";
    }

    public void initialize() {
        openGame = MenuStack.getInstance().getCookies().getOpenRoom();
        this.updateOpenGame(openGame);
    }

    public void updateOpenGame(OpenGame openGame) {
        this.gameNameText.setText(openGame.getName());
        this.adminNameText.setText(getTitleOfUser(openGame.getAdmin()));
        this.limitText.setText(String.valueOf(openGame.getPlayerLimit()));
        String username = MenuStack.getInstance().getUser().getUsername();
        if (!openGame.getAdmin().getUsername().equals(username)) this.startButton.setVisible(false);
        this.loadPlayersListFrom(openGame.getPlayers());
    }

    public void loadPlayersListFrom(ArrayList<User> players) {
        this.playersList.getChildren().clear();
        for (User player : players) {
            Text text = new Text(getTitleOfUser(player));
            this.playersList.getChildren().add(text);
        }
        this.startButton.setDisable(players.size() < 2);
    }

    public void startGame() {
        System.out.println("clicked");
//        if (openGame.getPlayers().size() < 2) return;
        System.out.println("reached");
        DatabaseQuerier.startGame(openGame.getToken());
    }

    public void openChatRoom() {

    }

    public void leaveGame() {
        String loginToken = MenuStack.getInstance().getCookies().getLoginToken();
        DatabaseQuerier.leaveFromRoom(loginToken, openGame.getToken());
        MenuStack.getInstance().popMenu();
    }

    public OpenGame getOpenGame() {
        return openGame;
    }
}
