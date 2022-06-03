package Views;

import Applications.App;
import Models.Database;
import Models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LoginView {

    @FXML
    private Button registerButton;
    @FXML
    private Button loginButton;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField nickname;
    @FXML
    private VBox usernameBox;
    @FXML
    private VBox nicknameBox;
    @FXML
    private VBox passwordBox;

    public void enterUsername() {
        removeAdditional();
        if (username.getText().length() > 12) {
            username.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
            Text lengthError = new Text("Username's Length Can't Be More Than 12");
            lengthError.setStyle("-fx-fill: #ff0066; -fx-font-size: 10");
            usernameBox.getChildren().add(lengthError);
            loginButton.setDisable(true);
            registerButton.setDisable(true);
        }
    }

    public void enterNickname() {
        removeAdditional();
        if (nickname.getText().length() > 12) {
            nickname.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
            Text lengthError = new Text("Nickname's Length Can't Be More Than 12");
            lengthError.setStyle("-fx-fill: #ff0066; -fx-font-size: 10");
            nicknameBox.getChildren().add(lengthError);
            loginButton.setDisable(true);
            registerButton.setDisable(true);
        }
    }

    public void enterPassword() {
        removeAdditional();
    }

    private void removeAdditional() {
        username.setStyle("-fx-border-color: none;");
        password.setStyle("-fx-border-color: none;");
        if (usernameBox.getChildren().size() > 2)
            usernameBox.getChildren().remove(2);
        if (passwordBox.getChildren().size() > 2)
            passwordBox.getChildren().remove(2);
        if (nicknameBox.getChildren().size() > 2)
            nicknameBox.getChildren().remove(2);
        loginButton.setDisable(false);
        registerButton.setDisable(false);
    }

    public void loginClick() {
        removeAdditional();
        if (emptyUsernameAndOrPassword())
            return;
        if (Database.getInstance().checkForUsername(username.getText())) {
            if (Database.getInstance().checkPassword(username.getText(), password.getText())) {
                MenuStack.getInstance().setUser(Database.getInstance().getUser(username.getText()));
                App.setScene("MenuPage"); //todo : login
            } else {
                if (passwordBox.getChildren().size() > 2)
                    return;
                password.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
                Text incorrectPassword = new Text("Incorrect Password !!");
                incorrectPassword.setStyle("-fx-fill: #ff0066; -fx-font-size: 10;");
                passwordBox.getChildren().add(incorrectPassword);
            }
        } else {
            if (usernameBox.getChildren().size() > 2)
                return;
            username.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
            Text usernameDoesNotExists = new Text("Username Doesn't Exists !!");
            usernameDoesNotExists.setStyle("-fx-fill: #ff0066; -fx-font-size: 10");
            usernameBox.getChildren().add(usernameDoesNotExists);
        }
    }

    public void registerClick() {
        removeAdditional();
        if (emptyUsernameAndOrPassword())
            return;
        if (Database.getInstance().checkForUsername(username.getText())) {
            if (usernameBox.getChildren().size() > 2)
                return;
            Text usernameExists = new Text("Username Already Exists !!");
            usernameExists.setStyle("-fx-fill: #ff0066; -fx-font-size: 10");
            usernameBox.getChildren().add(usernameExists);
            username.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
        } else if (Database.getInstance().nicknameAlreadyExists(nickname.getText())) {
            if (nicknameBox.getChildren().size() > 2)
                return;
            Text nicknameExists = new Text("Nickname Already Exists !!");
            nicknameExists.setStyle("-fx-fill: #ff0066; -fx-font-size: 10");
            nicknameBox.getChildren().add(nicknameExists);
            nickname.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
        } else {
            new User(username.getText(), password.getText(), nickname.getText());
            username.setStyle("-fx-border-color: #1aff00; -fx-border-radius: 5; -fx-border-width: 3;");
            password.setStyle("-fx-border-color: #1aff00; -fx-border-radius: 5; -fx-border-width: 3;");
        }
    }

    private boolean emptyUsernameAndOrPassword() {
        boolean isEmpty = false;
        if (username.getText().length() == 0) {
            username.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
            isEmpty = true;
        }
        if (password.getText().length() == 0) {
            password.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
            isEmpty = true;
        }
        return isEmpty;
    }

    public void exitClick() {
        System.exit(0);
    }
}
