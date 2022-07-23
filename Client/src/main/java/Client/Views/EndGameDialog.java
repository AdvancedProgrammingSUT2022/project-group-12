package Client.Views;

import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.*;

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
        ArrayList<Map.Entry<String,Integer>> sorted = new ArrayList<>(civsScore.entrySet());
        Collections.sort(sorted,getEntryComparator());
        borderPane.setCenter(vBox);
        for (int i = sorted.size() - 1; i >= 0 ; i--) {
            HBox hBox = new HBox(new Text(sorted.get(i).getKey() + " ------- " + sorted.get(i).getValue())); hBox.setAlignment(Pos.CENTER);
            vBox.getChildren().add(hBox);
        }
        vBox.getChildren().add(new Text("The Winner is " + sorted.get(sorted.size() - 1)));
        this.getDialogPane().setContent(borderPane);
        this.getDialogPane().getButtonTypes().add(ButtonType.OK);
    }

    private Comparator<Map.Entry<String, Integer>> getEntryComparator() {
        return new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if (o1.getValue() > o2.getValue()) {
                    return 1;
                }
                return -1;
            }
        };
    }
}
