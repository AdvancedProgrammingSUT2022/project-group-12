package Project.Client.Views;

import Project.Enums.TechnologyEnum;
import Project.Models.Civilization;
import Project.Server.Controllers.GameController;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class TechTreeView implements ViewController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ScrollPane mainScrollPane;
    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private TextArea information;
    private boolean hasCity;

    @FXML
    public void initialize() {
        hasCity = GameController.getGame().getCurrentCivilization().getCities().size() != 0;
        setTree();
    }

    private void setTree() {
        HBox hBox = drawTree();
        Rectangle rectangle = new Rectangle();
        anchorPane.getChildren().add(rectangle);
        anchorPane.getChildren().add(hBox);
    }

    private HBox drawTree() {
        Civilization civilization = GameController.getGame().getCurrentCivilization();
        int x = 60;
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        mainLoop:
        for (int i = 0; i < TechnologyEnum.values().length; i++) {
            if (TechnologyEnum.values()[i].name().equals("RESET"))
                continue mainLoop;
            x += 120 + 10;
            boolean disable = false;
            VBox vBox = new VBox();
            vBox.setSpacing(10);
            Circle circle = new Circle(60);
            circle.setFill(new ImagePattern(TechnologyEnum.values()[i].getImage()));
            if (!hasCity || !TechnologyEnum.values()[i].containsAllPreRequisite(civilization.getTechnologies())
                    || civilization.getTechnologies().contains(TechnologyEnum.values()[i]))
                disable = false;
            int selectedI = i;
            circle.setOnMouseClicked(mouseEvent -> civilization.addTechnology(TechnologyEnum.values()[selectedI]));
            circle.setDisable(disable);
            if (disable) {
                circle.setEffect(new DropShadow());
                circle.setStroke(Color.RED);
                if (civilization.getTechnologies().contains(TechnologyEnum.values()[i])) {
                    circle.setStroke(Color.BLUE);
                }
            }
            circle.setOnMouseEntered(mouseEvent -> {
                circle.setCursor(Cursor.HAND);
                circle.setEffect(new DropShadow());
            });
            circle.setOnMouseExited(mouseEvent -> circle.setEffect(null));
            Text text = new Text(TechnologyEnum.values()[i].name());
            text.setOnMouseEntered(mouseEvent -> {
                information.setText(TechnologyEnum.values()[selectedI].info());
            });
            text.setOnMouseExited(mouseEvent -> information.setText(""));
            text.setFill(Color.RED);
            vBox.getChildren().add(text);
            vBox.getChildren().add(circle);
            for (TechnologyEnum leadingTechnology : TechnologyEnum.values()[i].leadsToTech()) {
                Circle leadingCircle = new Circle(30);
                leadingCircle.setStroke(circle.getStroke());
                leadingCircle.setEffect(circle.getEffect());
                leadingCircle.setOnMouseEntered(mouseEvent -> {
                    leadingCircle.setEffect(new DropShadow());
                    leadingCircle.setCursor(Cursor.HAND);
                    // todo : show name
                });
                leadingCircle.setOnMouseExited(mouseEvent -> leadingCircle.setEffect(null));
                leadingCircle.setFill(new ImagePattern(leadingTechnology.getImage()));
                vBox.getChildren().add(leadingCircle);
            }
            vBox.setAlignment(Pos.TOP_CENTER);
            hBox.getChildren().add(vBox);
        }
        return hBox;
    }

    private String capitalize(String input){
        return String.valueOf(input.charAt(0)).toUpperCase() + input.substring(1);
    }

    public void back() {
        MenuStack.getInstance().popMenu();
    }


    public void exit() {
        System.exit(0);
    }
}
