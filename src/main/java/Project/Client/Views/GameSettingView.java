package Project.Client.Views;

import Project.Client.Models.SettingOptions;
import Project.Server.Models.Civilization;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameSettingView implements ViewController {

    @FXML
    private Button muteBtn;
    @FXML
    private Button autosaveBtn;
    private Civilization civilization;

    public void initialize() {
        SettingOptions settingOptions = MenuStack.getInstance().getCookies().getSettingOptions();
        if (settingOptions.isGameOnMute())
            muteBtn.setText("Muted");
        else
            muteBtn.setText("Un Muted");
        if (settingOptions.isAutoSaveOn())
            autosaveBtn.setText("On");
        else
            autosaveBtn.setText("Off");
    }

    public void saveGame() {
        //todo : fix game save
//        Database.getInstance().saveGame();
    }

    public void autosave() {
        SettingOptions settingOptions = MenuStack.getInstance().getCookies().getSettingOptions();
        settingOptions.setAutoSaveOn(!settingOptions.isAutoSaveOn());
        if (settingOptions.isAutoSaveOn())
            autosaveBtn.setText("On");
        else
            autosaveBtn.setText("Off");
    }

    public void mute() {
        SettingOptions settingOptions = MenuStack.getInstance().getCookies().getSettingOptions();
        settingOptions.setGameOnMute(!settingOptions.isGameOnMute());
        if (settingOptions.isGameOnMute())
            muteBtn.setText("Muted");
        else
            muteBtn.setText("Un Muted");
    }

    public void back(ActionEvent actionEvent) {
        MenuStack.getInstance().popMenu();
    }
}
