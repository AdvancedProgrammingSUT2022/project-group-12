package Project.Client.Views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import java.io.IOException;

public class WinCityDialog extends Dialog<String> {

    ToggleButton destroy;
    ToggleButton annexed;
    String inputMessage = null;

    public WinCityDialog() {
        try {
            buildUI();
            setResultConverter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public WinCityDialog(String message) {
        try {
            this.inputMessage = message;
            buildUI();
            setResultConverter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void buildUI() throws IOException {
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefHeight(200.0);
        borderPane.setPrefWidth(333.0);
        VBox vBox = new VBox();
        vBox.setPrefWidth(235.0);
        vBox.setPrefHeight(124.0);
        vBox.setAlignment(Pos.CENTER);
        Text text = initializeText();
        vBox.getChildren().add(text);
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.setPrefSize(179.0, 99.0);
        destroy = new ToggleButton("Destroy");
        destroy.setPrefSize(108.0, 24.0);
        annexed = new ToggleButton("Annexed");
        annexed.setPrefSize(108.0, 24.0);
        hBox.getChildren().addAll(destroy, annexed);
        vBox.getChildren().add(hBox);
        borderPane.setCenter(vBox);
        this.getDialogPane().setContent(borderPane);
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        Button button = (Button) this.getDialogPane().lookupButton(ButtonType.OK);
        button.setDisable(true);
        destroy.setOnAction(actionEvent -> {
            annexed.setSelected(false);
            button.setDisable(false);
        });
        annexed.setOnAction(actionEvent -> {
            destroy.setSelected(false);
            button.setDisable(false);
        });

    }

    private Text initializeText() {
        Text text;
        if (inputMessage != null) {
            text = new Text(inputMessage + "\nDo you want to destroy city or you want to make it annexed ?");
        } else {
            text = new Text("Do you want to destroy city or you want to make it annexed ?");
        }
        text.wrappingWidthProperty().set(176.7);
        return text;
    }

    private void setResultConverter() {
        Callback<ButtonType, String> callback = new Callback<ButtonType, String>() {
            @Override
            public String call(ButtonType buttonType) {
                if (destroy.isSelected()) {
                    return "Destroy";
                } else if (annexed.isSelected()) {
                    return "Annexed";
                }
                //never
                return null;
            }
        };
        this.setResultConverter(callback);
    }

}
