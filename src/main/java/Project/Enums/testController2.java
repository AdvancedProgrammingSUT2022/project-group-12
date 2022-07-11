package Project.Enums;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class testController2 {
    @FXML
    private Button btn;


    public void initialize(){
        System.out.println("test2 ejra shod");
    }

    public void btnAction(ActionEvent actionEvent) throws IOException {
        Test.changeRoot("test");
    }
}
