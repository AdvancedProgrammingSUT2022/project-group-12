package Client.Views;

import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

public class EndGameDialog extends Dialog<String> {


    public EndGameDialog(HashMap<String, Integer> civsScore) {
        buildUI(civsScore);
    }

    private void buildUI(HashMap<String,Integer> civsScore) {
        BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setAlignment(Pos.CENTER);
        borderPane.setCenter(vBox);
        for (Map.Entry<String, Integer> entry:
                civsScore.entrySet()) {
            HBox hBox = new HBox(new Text(entry.getKey() + " ------- " + entry.getValue())); hBox.setAlignment(Pos.CENTER);
            vBox.getChildren().add(hBox);
        }
        vBox.getChildren().add(new Text("The Winner is " + civsScore.keySet().stream().findFirst().get()));
        this.getDialogPane().setContent(borderPane);
        this.getDialogPane().getButtonTypes().add(ButtonType.OK);
    }
}
