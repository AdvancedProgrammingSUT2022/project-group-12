package Client.Views;

import Project.Models.Buildings.Building;
import Project.Models.Production;
import Project.Models.Units.Unit;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;

public class RemoveProductionDialog extends Dialog<String> {
    static String result = null;

    RemoveProductionDialog(ArrayList<Production> productions) {
        buildUI(productions);
        this.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        this.getDialogPane().getButtonTypes().add(ButtonType.OK);
        Button button = (Button) this.getDialogPane().lookupButton(ButtonType.OK);
        this.setResultConverter(new Callback<ButtonType, String>() {
            @Override
            public String call(ButtonType buttonType) {
                if (buttonType == ButtonType.CANCEL) {
                    return "no unit selected";
                }
                //never
                return null;
            }
        });
        button.setVisible(false);
    }

    private void buildUI(ArrayList<Production> productions) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(200, 200);
        borderPane.setPadding(new Insets(10));
        Text headerText = new Text("List of production Queue : ");
        borderPane.setTop(headerText);
        ScrollPane productionsScrollPane = new ScrollPane();
        VBox productionBox = new VBox();
        productionBox.setAlignment(Pos.CENTER);
        productionBox.setSpacing(10);
        System.out.println("productions.size() = " + productions.size());
        int i = 1;
        for (Production pro :
                productions) {
            ProductionHBox productionHBox = new ProductionHBox(pro, i);
            int finalI = i;
            productionHBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    result = String.valueOf(finalI);
                    Button button = (Button) RemoveProductionDialog.this.getDialogPane().lookupButton(ButtonType.OK);
                    RemoveProductionDialog.this.setResultConverter(new Callback<ButtonType, String>() {
                        @Override
                        public String call(ButtonType buttonType) {
                            return result;
                        }
                    });
                    button.fire();
                }
            });
            productionBox.getChildren().add(productionHBox);
            ++i;
        }
        productionsScrollPane.setPrefSize(80, 160);
        productionsScrollPane.setContent(productionBox);
        productionsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        productionsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        borderPane.setCenter(productionsScrollPane);
        this.getDialogPane().setContent(borderPane);
    }
}

class ProductionHBox extends HBox {
    int index;

    String message = null;

    ProductionHBox(Production product, int index) {
        if (product instanceof Unit unit) {
            initializeMessage(unit.getUnitType().name().toLowerCase(), (int) unit.getRemainedProduction(), index);
        } else if (product instanceof Building building) {
            initializeMessage(building.getBuildingType().name().toLowerCase(), (int) building.getRemainedProduction(), index);
        }
        this.index = index;
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
    }

    public void initializeMessage(String type, double remainsProduct, int index) {
        message = index + " " + type +   " --- remains production : " + new DecimalFormat("0.00").format(remainsProduct);
    }

    public int getIndex() {
        return index;
    }
}





