package Project.Client.Views;

import Project.Models.Civilization;
import Project.Models.Database;
import Project.Server.Controllers.GameController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameSettingView implements ViewController {

    @FXML
    private Button muteBtn;
    @FXML
    private Button autosaveBtn;
    private Civilization civilization;

    public void initialize() {
        Civilization civilization = GameController.getGame().getCurrentCivilization();
        if (civilization.isGameOnMute())
            muteBtn.setText("Muted");
        else
            muteBtn.setText("Un Muted");
        if (civilization.isAutoSaveOn())
            autosaveBtn.setText("On");
        else
            autosaveBtn.setText("Off");
        this.civilization = civilization;
    }

    public void saveGame() {
        //todo : fix game save
//        Database.getInstance().saveGame();
    }

    public void autosave() {
        civilization.setAutoSaveOn(!civilization.isAutoSaveOn());
        if (civilization.isAutoSaveOn())
            autosaveBtn.setText("On");
        else
            autosaveBtn.setText("Off");
    }

    public void mute() {
        civilization.setGameOnMute(!civilization.isGameOnMute());
        if (civilization.isGameOnMute())
            muteBtn.setText("Muted");
        else
            muteBtn.setText("Un Muted");
    }
}
