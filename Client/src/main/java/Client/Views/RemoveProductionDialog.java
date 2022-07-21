package Client.Views;

import Project.Models.Buildings.Building;
import Project.Models.Production;
import Project.Models.Units.Unit;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;

public class RemoveProductionDialog extends Dialog<StringProperty> {
    static StringProperty result;

    RemoveProductionDialog(ArrayList<Production> productions) {
        buildUI(productions);
        this.setResultConverter(new Callback<ButtonType, StringProperty>() {
            @Override
            public StringProperty call(ButtonType buttonType) {
                return result;
            }
        });
    }

    private void buildUI(ArrayList<Production> productions) {
        BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox(); vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        int i = 1;
        for (Production pro :
                productions) {
            vBox.getChildren().add(new ProductionHBox(pro, i));
            ++i;
        }
        Text text = new Text();
        text.textProperty().bind(result);
        borderPane.setCenter(vBox);
        this.getDialogPane().setContent(borderPane);
    }
}
class ProductionHBox extends HBox {

    String message = null;

    ProductionHBox(Production product, int index) {
        if (product instanceof Unit unit) {
            initializeMessage(unit.getUnitType().name().toLowerCase(), (int) unit.getRemainedProduction(), index);
        } else if (product instanceof Building building) {
            initializeMessage(building.getBuildingType().name().toLowerCase(), (int) building.getRemainedProduction(), index);
        }
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(new Text(message));
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ProductionHBox.this.setStyle("-fx-border-color: Blue;");
            }
        });
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ProductionHBox.this.setStyle("-fx-border-color: transparent;");
            }
        });
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                RemoveProductionDialog.result = new SimpleStringProperty(String.valueOf(index));
            }
        });
    }

    public void initializeMessage(String type, double remainsProduct, int index) {
        message = index + "  " + new DecimalFormat("0.00").format(remainsProduct) + " ----------- " + remainsProduct;
    }
}





