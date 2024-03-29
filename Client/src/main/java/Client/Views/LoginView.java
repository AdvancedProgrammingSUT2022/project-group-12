package Client.Views;

import Client.Utils.DatabaseQuerier;
import Client.Utils.RequestSender;
import Client.Utils.UpdateTracker;
import Project.Utils.CommandResponse;
import Project.Utils.Constants;
import Project.Utils.Request;
import Project.Utils.RequestType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.net.Socket;

//todo : improve line 80
public class LoginView implements ViewController {

    private final String[] choiceBoxOptions = {"Register", "Login"};
    @FXML
    private HBox buttonBox;
    @FXML
    private VBox mainBox;
    @FXML
    private ChoiceBox<String> choiceBox;
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
    private boolean nicknameFieldOn;

    public void initialize() {
        choiceBox.setValue("Login");
        createButton();
        nicknameFieldOn = false;
        choiceBox.getItems().addAll(choiceBoxOptions);
        choiceBox.setOnAction(this::choiceBoxAction);
        createNicknameField();
    }

    private void choiceBoxAction(ActionEvent event) {
        if (choiceBox.getValue().equals("Login")) {
            mainBox.getChildren().remove(nicknameBox);
            createNicknameField();
            nicknameFieldOn = false;
            buttonBox.getChildren().remove(0);
            buttonBox.getChildren().add(loginButton);
        } else if (choiceBox.getValue().equals("Register")) {
            nicknameFieldOn = true;
            if (!mainBox.getChildren().contains(nicknameBox))
                mainBox.getChildren().add(1, nicknameBox);
            buttonBox.getChildren().remove(0);
            buttonBox.getChildren().add(registerButton);
        }
    }

    private void createButton() {
        loginButton = new Button("Login");
        loginButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loginClick();
            }
        });
        registerButton = new Button("Register");
        registerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                registerClick();
            }
        });
    }

    public void createNicknameField() {
        Text nicknameLabel = new Text("NICKNAME");
        nicknameLabel.setTextAlignment(TextAlignment.CENTER);
        this.nickname = new TextField();
        nickname.setPromptText("Nickname");
        nickname.setAlignment(Pos.CENTER);
        nickname.setOnKeyPressed(keyEvent -> LoginView.this.enterNickname());
        this.nicknameBox = new VBox(nicknameLabel, nickname);
        nicknameBox.setSpacing(10);
        nicknameBox.setAlignment(Pos.CENTER);
    }


    public void enterUsername() {
        removeAdditional();
        if (username.getText().length() > 12) {
            username.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
            Text lengthError = new Text("Username's Length Can't Be More Than 12");
            lengthError.setStyle("-fx-fill: #ff0066; -fx-font-size: 10");
            usernameBox.getChildren().add(lengthError);
        }
    }

    public void enterNickname() {

        removeAdditional();
        if (nickname.getText().length() > 12) {
            nickname.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
            Text lengthError = new Text("Nickname's Length Can't Be More Than 12");
            lengthError.setStyle("-fx-fill: #ff0066; -fx-font-size: 10");
            nicknameBox.getChildren().add(lengthError);
        }
    }

    public void enterPassword() {
        removeAdditional();

    }

    private void removeAdditional() {
        username.setStyle("-fx-border-color: none;");
        password.setStyle("-fx-border-color: none;");
        nickname.setStyle("-fx-border-color: none;");
        if (usernameBox.getChildren().size() > 2)
            usernameBox.getChildren().remove(2);
        if (passwordBox.getChildren().size() > 2)
            passwordBox.getChildren().remove(2);
        if (nicknameBox.getChildren().size() > 2)
            nicknameBox.getChildren().remove(2);
    }

    public void loginClick() {
        removeAdditional();
        if (emptyUsernameAndOrPassword())
            return;
        if (!RequestSender.getInstance().isInitialized()) {
            if (!connectToServer()) return;
        }
        Request request = new Request(RequestType.LOGIN_USER, null);
        request.addParameter("username", username.getText());
        request.addParameter("password", password.getText());
        CommandResponse response = RequestSender.getInstance().sendRequest(request);
        switch (response) {
            case OK -> {
                String loginToken = RequestSender.getInstance().getParameter("loginToken");
                MenuStack.getInstance().getCookies().setLoginToken(loginToken);
                MenuStack.getInstance().setUser(DatabaseQuerier.getUser(username.getText()));

                UpdateTracker updateTracker = new UpdateTracker();
                Thread thread = new Thread(updateTracker);
                thread.setDaemon(true);
                thread.start();

                MenuStack.getInstance().pushMenu(Menu.loadFromFXML("MainPage"));
            }
            case PASSWORD_DOES_NOT_MATCH -> {
                if (passwordBox.getChildren().size() > 2)
                    return;
                password.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
                Text incorrectPassword = new Text("Incorrect Password !!");
                incorrectPassword.setStyle("-fx-fill: #ff0066; -fx-font-size: 10;");
                passwordBox.getChildren().add(incorrectPassword);
            }
            case USER_DOES_NOT_EXISTS -> {
                if (usernameBox.getChildren().size() > 2)
                    return;
                username.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
                Text usernameDoesNotExists = new Text("Username Doesn't Exists !!");
                usernameDoesNotExists.setStyle("-fx-fill: #ff0066; -fx-font-size: 10");
                usernameBox.getChildren().add(usernameDoesNotExists);
            }
            case USER_ALREADY_LOGGED_IN -> {
                Text usernameDoesNotExists = new Text(response.getMessage());
                usernameDoesNotExists.setStyle("-fx-fill: #ff0066; -fx-font-size: 10");
                usernameBox.getChildren().add(usernameDoesNotExists);
            }
            default -> throw new RuntimeException();
        }
    }

    public void registerClick() {
        removeAdditional();
        if (emptyUsernameAndOrPassword())
            return;
        if (!RequestSender.getInstance().isInitialized()) {
            if (!connectToServer()) return;
        }
        Request request = new Request(RequestType.CREATE_USER, null);
        request.addParameter("username", username.getText());
        request.addParameter("nickname", nickname.getText());
        request.addParameter("password", password.getText());
        CommandResponse response = RequestSender.getInstance().sendRequest(request);
        switch (response) {
            case OK -> {
                username.setStyle("-fx-border-color: #1aff00; -fx-border-radius: 5; -fx-border-width: 3;");
                password.setStyle("-fx-border-color: #1aff00; -fx-border-radius: 5; -fx-border-width: 3;");
                nickname.setStyle("-fx-border-color: #1aff00; -fx-border-radius: 5; -fx-border-width: 3;");
            } case NICKNAME_ALREADY_EXISTS -> {
                if (nicknameBox.getChildren().size() > 2)
                    return;
                Text nicknameExists = new Text("Nickname Already Exists !!");
                nicknameExists.setStyle("-fx-fill: #ff0066; -fx-font-size: 10");
                nicknameBox.getChildren().add(nicknameExists);
                nickname.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
            } case USERNAME_ALREADY_EXISTS -> {
                if (usernameBox.getChildren().size() > 2)
                    return;
                Text usernameExists = new Text("Username Already Exists !!");
                usernameExists.setStyle("-fx-fill: #ff0066; -fx-font-size: 10");
                usernameBox.getChildren().add(usernameExists);
                username.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
            }
            default -> {
                MenuStack.getInstance().showError(response.getMessage());
            }
        }
    }

    private boolean connectToServer() {
        Socket socket;
        try {
            socket = new Socket(Constants.SERVER_HOST, Constants.SERVER_PORT);
            System.out.println("connected to server at " + Constants.SERVER_HOST + ":" + Constants.SERVER_PORT);
        } catch (IOException e) {
            Text errorText = new Text("can't connect to server at " + Constants.SERVER_HOST + ":" + Constants.SERVER_PORT);
            errorText.setStyle("-fx-fill: #ff0066; -fx-font-size: 12; -fx-font-weight: 900");
            usernameBox.getChildren().add(errorText);
            return false;
        }
        RequestSender.getInstance().initialize(socket);
        return true;
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
        if (nickname.getText().length() == 0 && nicknameFieldOn) {
            nickname.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
            isEmpty = true;
        }
        return isEmpty;
    }

    public void exitClick() {
        System.exit(0);
    }
}
