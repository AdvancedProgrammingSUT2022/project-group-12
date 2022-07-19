package Client.Views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public class AlertController implements ViewController {

    @FXML
    private ButtonType okayBTN;
    @FXML
    private DialogPane alertWindow;
    @FXML
    private Button okButton;

    public void initialize() {
        okButton.setOnAction(actionEvent -> {

        });
    }
}
