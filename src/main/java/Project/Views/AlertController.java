package Project.Views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;

public class AlertController implements ViewController {

    @FXML
    DialogPane alertWindow;
    @FXML
    Button okButton;

     public void initialize(){
         okButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {

             }
         });





     }
}
