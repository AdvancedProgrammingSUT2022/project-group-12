package Client.Views;

import Client.Utils.RequestSender;
import Project.Utils.CommandResponse;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ProfileView implements ViewController {
    public ImageView userAvatar;
    @FXML
    private Button changeUsername;
    @FXML
    private Button changePassword;
    @FXML
    private VBox box;
    @FXML
    private Text username;
    @FXML
    private Button removeAccount;
    @FXML
    private VBox rmAccount;
    @FXML
    private VBox changingNickname;
    @FXML
    private VBox changingPass;
    private TextField changeNick;
    private TextField changePass;
    private TextField oldPass;
    private Button userAcceptButton;
    private Button passAcceptButton;
    private boolean passFieldIsOn = false;
    private boolean userFieldIsOn = false;
    private boolean removingAccount = false;
    private boolean isOldPass = false;

    public void selectAvatarClick() {
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("AvatarPage"));
    }

    public void initialize() {
        removeAccount = new Button();
        rmAccount = new VBox();
        this.userAvatar.setImage(AvatarView.getAvatarImage(MenuStack.getInstance().getUser().getAvatarURL()));
        this.username.setText("-" + MenuStack.getInstance().getUser().getUsername());
    }

    public void exitClick() {
        System.exit(0);
    }

    public void backClick() {
        String command = "menu exit";
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        MenuStack.getInstance().popMenu();
    }

    public void changeUsernameClick() {
        removeOtherLevelBoxes(1);
        if (!userFieldIsOn) {
            userFieldIsOn = true;
            changeNick = returnChangeField(true);
            userAcceptButton = returnAcceptButton(true);
            changingNickname.getChildren().add(changeNick);
            changingNickname.getChildren().add(userAcceptButton);
        } else {
            userFieldIsOn = false;
            changingNickname.getChildren().remove(changeNick);
            changingNickname.getChildren().remove(userAcceptButton);
        }
    }

    public void changePasswordClick() {
        removeOtherLevelBoxes(2);
        if (!passFieldIsOn) {
            passFieldIsOn = true;
            isOldPass = true;
            oldPass = returnChangeField(false);
            changePass = returnChangeField(false);
            passAcceptButton = returnAcceptButton(false);
            changingPass.getChildren().add(oldPass);
            changingPass.getChildren().add(changePass);
            changingPass.getChildren().add(passAcceptButton);
        } else {
            passFieldIsOn = false;
            changingPass.getChildren().remove(changePass);
            changingPass.getChildren().remove(passAcceptButton);
        }
    }

    private TextField returnChangeField(boolean isUsername) {
        TextField field = new TextField();
        if (isOldPass) {
            field.setAlignment(Pos.CENTER);
            field.setPromptText("Old Password");
        } else if (isUsername) {
            field.setAlignment(Pos.CENTER);
            field.setPromptText("Username");
        } else {
            field.setAlignment(Pos.CENTER);
            field.setPromptText("New Password");
        }
        field.prefWidth(100);
        isOldPass = false;
        return field;
    }

    private Button returnAcceptButton(boolean isForNickname) {
        Button createdButton = new Button();
        createdButton.setText("Accept");
        createdButton.getStyleClass().add("primaryButton");
        createdButton.setOnMouseClicked(mouseEvent -> {
            if (removingAccount) {
                accountAboutToBeRemoved();
            } else {
                if (isForNickname)
                    nicknameAboutToChange();
                else
                    passwordAboutToChange();
            }
        });
        return createdButton;
    }

    private void accountAboutToBeRemoved() {
        if (MenuStack.getInstance().getUser().passwordMatchCheck(changePass.getText())) {
            changePass.setStyle("-fx-border-color: #1aff00; -fx-border-radius: 5; -fx-border-width: 3;");
            MenuStack.getInstance().popToLogin();
        } else {
            changePass.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
        }
    }

    private void nicknameAboutToChange() {
        String command = "change nickname -n " + changeNick.getText();
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        switch (response) {
            case OK -> {
                MenuStack.getInstance().getUser().changeNickname(changeNick.getText());
                changeNick.setStyle("-fx-border-color: #1aff00; -fx-border-radius: 5; -fx-border-width: 3;");
            }
            case NICKNAME_ALREADY_EXISTS -> {
                changeNick.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
            }
            default -> throw new RuntimeException();
        }
    }

    private void passwordAboutToChange() {
        String command = "change password -o " + oldPass.getText() + " -n " + changePass.getText();
        CommandResponse response = RequestSender.getInstance().sendCommand(command);
        if (response.isOK()) {
            command = "change password -o " + oldPass.getText() + " -n " + changePass.getText();
            RequestSender.getInstance().sendCommand(command);
            backClick();
            ((MainMenuView) MenuStack.getInstance().getTopMenu().getController()).logout();
        }
//        if (MenuStack.getInstance().getUser().passwordMatchCheck(oldPass.getText())) {
//            changePass.setStyle("-fx-border-color: #1aff00; -fx-border-radius: 5; -fx-border-width: 3;");
//            MenuStack.getInstance().getUser().changePassword(changePass.getText());
//        } else {
//            oldPass.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
//            Text incorrectPassword = new Text("Incorrect Password !!");
//            incorrectPassword.setStyle("-fx-fill: #ff0066; -fx-font-size: 10;");
//            changingPass.getChildren().add(incorrectPassword);
//            changePassword.setDisable(true);
//        }
    }

    private void removeOtherLevelBoxes(int currentLevel) {
        while (currentLevel != 2 && changingPass.getChildren().size() > 1)
            changingPass.getChildren().remove(changingPass.getChildren().size() - 1);
        while (currentLevel != 1 && changingNickname.getChildren().size() > 1)
            changingNickname.getChildren().remove(changingNickname.getChildren().size() - 1);
    }

    public void gotoFriendsPage() {
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("FriendsPage"));

    }
}
