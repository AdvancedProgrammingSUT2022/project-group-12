package Project.Client.Views;

import Project.Enums.TechnologyEnum;
import Project.Models.Civilization;
import Project.Server.Controllers.GameController;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.TreeMap;

public class TechTreeView implements ViewController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ScrollPane mainScrollPane;
    @FXML
    private AnchorPane mainAnchorPane;
    private boolean hasCity;
    private TreeMap<String, Text> names;
    private TreeMap<String, VBox> techBoxes;
    @FXML
    private MenuButton chooseTechMenu;
    private VBox selectedBox;

    @FXML
    public void initialize() {
        hasCity = GameController.getGame().getCurrentCivilization().getCities().size() != 0;
        this.names = new TreeMap<>();
        this.selectedBox = null;
        this.techBoxes = new TreeMap<>();
        setTree();
        initChooseBox();
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
        for (var tech : TechnologyEnum.RESET.getAllTech().entrySet()) {
            x += 120 + 10;
            boolean disable = false;
            VBox vBox = new VBox();
            vBox.setSpacing(10);
            Circle circle = new Circle(60);
            circle.setFill(new ImagePattern(tech.getValue().getImage()));
            if (!hasCity || !tech.getValue().containsAllPreRequisite(civilization.getTechnologies())
                    || civilization.getTechnologies().contains(tech.getValue()))
                disable = true;
            circle.setOnMouseClicked(mouseEvent -> civilization.addTechnology(tech.getValue()));
            circle.setDisable(disable);
            if (disable) {
                circle.setEffect(new DropShadow());
                circle.setStroke(Color.RED);
                if (civilization.getTechnologies().contains(tech.getValue())) {
                    circle.setStroke(Color.BLUE);
                }
            }
            circle.setOnMouseEntered(mouseEvent -> {
                circle.setCursor(Cursor.HAND);
                circle.setEffect(new DropShadow());
            });
            circle.setOnMouseExited(mouseEvent -> circle.setEffect(null));
            Text text = new Text(tech.getValue().name());
            text.setFill(Color.RED);
            names.put(tech.getValue().name(), text);
            vBox.getChildren().add(text);
            vBox.getChildren().add(circle);
            for (TechnologyEnum leadingTechnology : tech.getValue().leadsToTech()) {
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
            techBoxes.put(tech.getKey(), vBox);
            hBox.getChildren().add(vBox);
        }
        return hBox;
    }

    private String capitalize(String input) {
        return String.valueOf(input.charAt(0)).toUpperCase() + input.substring(1);
    }

    private void initChooseBox() {
        for (var tech : techBoxes.entrySet()) {
            MenuItem item = new MenuItem(tech.getKey());
            item.setOnAction(actionEvent -> {
                if (selectedBox != null)
                    selectedBox.setBackground(Background.EMPTY);
                techBoxes.get(tech.getKey()).setBackground(Background.fill(Color.VIOLET));
                chooseTechMenu.setText(tech.getKey());
                selectedBox = techBoxes.get(tech.getKey());
                //todo : fix zoom
//                mainScrollPane.setHvalue(names.get(tech.getKey()).getLayoutX());
            });
            chooseTechMenu.getItems().add(item);
        }
    }

    public void back() {
        MenuStack.getInstance().popMenu();
    }


    public void exit() {
        System.exit(0);
    }
}
