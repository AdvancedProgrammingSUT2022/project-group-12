package Project.Views;

import Project.Models.Database;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ProfileView {
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
    private TextField changeUser;
    private TextField changePass;
    private TextField oldPass;
    private Button userAcceptButton;
    private Button passAcceptButton;
    private boolean passFieldIsOn = false;
    private boolean userFieldIsOn = false;
    private boolean removeFieldIsOn = false;
    private boolean removingAccount = false;
    private boolean isOldPass = false;

    public void selectAvatarClick() {
        MenuStack.getInstance().pushMenu(Menu.loadFromFXML("AvatarPage"));
    }

    public void initialize() {
        this.userAvatar.setImage(MenuStack.getInstance().getUser().getImage());
        this.username.setText("-" + MenuStack.getInstance().getUser().getUsername());
    }

    public void exitClick() {
        System.exit(0);
    }

    public void backClick() {
        MenuStack.getInstance().popMenu();
    }

    public void changeUsernameClick() {
        removeOtherLevelBoxes(1);
        if (!userFieldIsOn) {
            userFieldIsOn = true;
            changeUser = returnChangeField(true);
            userAcceptButton = returnAcceptButton(true);
            changingNickname.getChildren().add(changeUser);
            changingNickname.getChildren().add(userAcceptButton);
        } else {
            userFieldIsOn = false;
            changingNickname.getChildren().remove(changeUser);
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

    public void removeAccountClick() {
        removeOtherLevelBoxes(3);
        if (!removeFieldIsOn) {
            removeFieldIsOn = true;
            removingAccount = true;
            changePass = returnChangeField(false);
            passAcceptButton = returnAcceptButton(false);
            passAcceptButton.setPrefWidth(150);
            rmAccount.getChildren().add(changePass);
            rmAccount.getChildren().add(passAcceptButton);
        } else {
            removeFieldIsOn = false;
            removingAccount = false;
            rmAccount.getChildren().remove(changePass);
            rmAccount.getChildren().remove(passAcceptButton);
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

    private Button returnAcceptButton(boolean isForUsername) {
        Button createdButton = new Button();
        createdButton.setText("Accept");
        createdButton.getStyleClass().add("primaryButton");
        createdButton.setOnMouseClicked(mouseEvent -> {
            if (removingAccount) {
                accountAboutToBeRemoved();
            } else {
                if (isForUsername)
                    usernameAboutToChange();
                else
                    passwordAboutToChange();
            }
        });
        return createdButton;
    }

    private void accountAboutToBeRemoved() {
        if (MenuStack.getInstance().getUser().passwordMatchCheck(changePass.getText())) {
            changePass.setStyle("-fx-border-color: #1aff00; -fx-border-radius: 5; -fx-border-width: 3;");
            Database.getInstance().removeAccount(MenuStack.getInstance().getUser());
            MenuStack.getInstance().popToLogin();
            //todo : go back to login page
        } else {
            changePass.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
        }
    }

    private void usernameAboutToChange() {
        if (Database.getInstance().nicknameAlreadyExists(changeUser.getText()))
            changeUser.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
        else {
            MenuStack.getInstance().getUser().changeNickname(changeUser.getText());
            changeUser.setStyle("-fx-border-color: #1aff00; -fx-border-radius: 5; -fx-border-width: 3;");
        }
    }

    private void passwordAboutToChange() {
        if (MenuStack.getInstance().getUser().passwordMatchCheck(oldPass.getText())) {
            changePass.setStyle("-fx-border-color: #1aff00; -fx-border-radius: 5; -fx-border-width: 3;");
            MenuStack.getInstance().getUser().changePassword(changePass.getText());
        } else {
            oldPass.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
            Text incorrectPassword = new Text("Incorrect Password !!");
            incorrectPassword.setStyle("-fx-fill: #ff0066; -fx-font-size: 10;");
            changingPass.getChildren().add(incorrectPassword);
            changePassword.setDisable(true);
        }
    }

    private void removeOtherLevelBoxes(int currentLevel) {
        while (currentLevel != 3 && rmAccount.getChildren().size() > 1)
            rmAccount.getChildren().remove(rmAccount.getChildren().size() - 1);
        while (currentLevel != 2 && changingPass.getChildren().size() > 1)
            changingPass.getChildren().remove(changingPass.getChildren().size() - 1);
        while (currentLevel != 1 && changingNickname.getChildren().size() > 1)
            changingNickname.getChildren().remove(changingNickname.getChildren().size() - 1);
    }
}
