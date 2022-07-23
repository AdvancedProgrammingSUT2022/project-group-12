package Client.Views;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ErrorDialog extends Dialog<String> {

    String errorMessage;

    ErrorDialog(String message,String type){
        this.errorMessage = message;
        buildUI(type);
    }

    private void buildUI(String type) {
        BorderPane borderPane = new BorderPane();
        Text text;
        if(type.equals("error")) text = new Text("Error : ");
        else text = new Text("Command successfully : ");
        text.setStyle("-fx-font-size: 20px;");
        VBox vBox = new VBox(); vBox.setPrefHeight(100);
        vBox.setSpacing(10);
        vBox.getChildren().add(text);
        Text errorMessage = new Text(this.errorMessage);
        vBox.getChildren().add(errorMessage);
        this.getDialogPane().setContent(vBox);
        this.getDialogPane().getButtonTypes().add(ButtonType.OK);
    }

}
