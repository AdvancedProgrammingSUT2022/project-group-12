package Project.Views;

import Project.App;
import Project.Models.Database;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ProfileView {
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
    private VBox changingUser;
    @FXML
    private VBox changingPass;
    @FXML
    private ImageView userAvatar;
    private TextField changeUser;
    private TextField changePass;
    private Button userAcceptButton;
    private Button passAcceptButton;
    private boolean passFieldIsOn = false;
    private boolean userFieldIsOn = false;
    private boolean removeFieldIsOn = false;
    private boolean removingAccount = false;

    public void initialize() {
        this.username.setText("-" + MenuStack.getInstance().getUser().getUsername());
    }

    public void exitClick() {
        System.exit(0);
    }

    public void backClick() {
        //todo : back to menu page
    }

    public void changeUsernameClick() {
        removeOtherLevelBoxes(1);
        if (!userFieldIsOn) {
            userFieldIsOn = true;
            changeUser = returnChangeField(true);
            userAcceptButton = returnAcceptButton(true);
            changingUser.getChildren().add(changeUser);
            changingUser.getChildren().add(userAcceptButton);
        } else {
            userFieldIsOn = false;
            changingUser.getChildren().remove(changeUser);
            changingUser.getChildren().remove(userAcceptButton);
        }
    }

    public void changePasswordClick() {
        removeOtherLevelBoxes(2);
        if (!passFieldIsOn) {
            passFieldIsOn = true;
            changePass = returnChangeField(false);
            passAcceptButton = returnAcceptButton(false);
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
        if (isUsername) {
            field.setAlignment(Pos.CENTER);
            field.setPromptText("Username");
        } else {
            field.setAlignment(Pos.CENTER);
            field.setPromptText("Password");
        }
        field.prefWidth(100);
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
            // todo : go to login page
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
        changePass.setStyle("-fx-border-color: #1aff00; -fx-border-radius: 5; -fx-border-width: 3;");
        MenuStack.getInstance().getUser().changePassword(changePass.getText());
    }

    private void removeOtherLevelBoxes(int currentLevel) {
        while (currentLevel != 3 && rmAccount.getChildren().size() > 1)
            rmAccount.getChildren().remove(rmAccount.getChildren().size() - 1);
        while (currentLevel != 2 && changingPass.getChildren().size() > 1)
            changingPass.getChildren().remove(changingPass.getChildren().size() - 1);
        while (currentLevel != 1 && changingUser.getChildren().size() > 1)
            changingUser.getChildren().remove(changingUser.getChildren().size() - 1);
    }
}
