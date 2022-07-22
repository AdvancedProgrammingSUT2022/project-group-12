package Client.Views;

import Client.Utils.DatabaseQuerier;
import Project.Enums.TechnologyEnum;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TechnologyTreeView implements ViewController {
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public Pane pane;
    private TextField navigateTextField;
    private final List<List<TechnologyEnum>> techLevels = new ArrayList<>();
    private final List<TechnologyEnum> mark = new ArrayList<>();
    private final Map<TechnologyEnum, Node> techItems = new HashMap<>();
    private final Map<TechnologyEnum, Integer> heights = new HashMap<>();
    private final static int height = 1500;
    private final static int width = 2000;
    private final static int itemSize = 100;

    private void dfs(TechnologyEnum tech) {
        mark.add(tech);
        int height = 0;
        for (var child : tech.getPrerequisiteTechs()) {
            if (!mark.contains(child)) {
                dfs(child);
            }
            height = Math.max(height, heights.get(child) + 1);
        }
        if (techLevels.size() <= height) techLevels.add(new ArrayList<>());
        techLevels.get(height).add(tech);
        heights.put(tech, height);
    }

    Node createTechnologyItem(TechnologyEnum tech, Color shadowColor) {
        Image image = ImageLoader.getTechnologyImages().get(tech);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(itemSize);
        imageView.setFitHeight(itemSize);
        Text text = new Text(tech.name().toLowerCase().replace('_', ' '));
        text.setStyle("-fx-font-size:12; -fx-fill:white");
        VBox vBox = new VBox(imageView, text);
        vBox.setAlignment(Pos.CENTER);
        if (shadowColor == null) {
            vBox.setOpacity(0.5);
        } else {
            imageView.setEffect(new DropShadow(30, shadowColor));
        }
        return vBox;
    }

    public void initialize() {
        drawControls();
        drawTechTree();
        drawHelps();
    }

    private void drawControls() {
        Button backButton = new Button();
        backButton.setText("back");
        backButton.setLayoutX(100);
        backButton.setLayoutY(370);
        backButton.setOnMouseClicked(mouseEvent -> back());
        pane.getChildren().add(backButton);

        navigateTextField = new TextField();
        navigateTextField.setLayoutX(300);
        navigateTextField.setLayoutY(370);
        pane.getChildren().add(navigateTextField);

        Button navigateButton = new Button();
        navigateButton.setText("navigate");
        navigateButton.setLayoutX(500);
        navigateButton.setLayoutY(370);
        navigateButton.setOnMouseClicked(mouseEvent -> navigateToTech());
        pane.getChildren().add(navigateButton);
    }

    private void navigateToTech() {
        String text = navigateTextField.getText();
        TechnologyEnum tech;
        try {
            tech = TechnologyEnum.valueOf(text.toUpperCase().replace(' ', '_'));
            navigateTextField.setStyle("-fx-border-radius: 0;");
        } catch (IllegalArgumentException e) {
            navigateTextField.setStyle("-fx-border-color: #ff0066; -fx-border-radius: 5; -fx-border-width: 3;");
            return;
        }
        Node item = techItems.get(tech);
        scrollPane.setVvalue(item.getTranslateY() / anchorPane.getLayoutBounds().getHeight() * 1.3);
        scrollPane.setHvalue(item.getTranslateX() / anchorPane.getLayoutBounds().getWidth() * 1.2);
    }

    private Text createText(String string, String color, int x, int y) {
        Text text = new Text(string);
        text.setStyle("-fx-fill:" + color);
        text.setLayoutX(x);
        text.setLayoutY(y);
        return text;
    }

    private void drawHelps() {
        pane.getChildren().add(createText("Researched", "green", 5, 305));
        pane.getChildren().add(createText("Researchable", "yellow", 5, 320));
        pane.getChildren().add(createText("Researching", "orange", 5, 335));
    }

    private void drawTechTree() {
        ArrayList<TechnologyEnum> currentTechnologies = DatabaseQuerier.getCurrentTechnologies();
        List<TechnologyEnum> researchingTechnologies = DatabaseQuerier.getResearchingTechnologies().keySet().stream().toList();
        TechnologyEnum researchingTechnology = DatabaseQuerier.getResearchingTechnology();
        for (var tech : TechnologyEnum.values()) {
            if (tech == TechnologyEnum.RESET) continue;
            dfs(tech);
        }
        int horizontalGap = width / techLevels.size();
        for (int level = 0; level < techLevels.size(); ++level) {
            int verticalGap = height / (techLevels.get(level).size() + 1);
            for (int i = 0; i < techLevels.get(level).size(); ++i) {
                TechnologyEnum tech = techLevels.get(level).get(i);
                Color techColor = null;
                if (currentTechnologies.contains(tech)) techColor = Color.GREEN;
                else if (tech == researchingTechnology) techColor = Color.ORANGE;
                else if (researchingTechnologies.contains(tech)) techColor = Color.YELLOW;
                else if (currentTechnologies.containsAll(tech.getPrerequisiteTechs())) techColor = Color.WHITE;
                Node item = createTechnologyItem(tech, techColor);
                item.setTranslateX(level * horizontalGap);
                item.setTranslateY((i + 1) * verticalGap);
                techItems.put(tech, item);
            }
        }
        for (var tech : TechnologyEnum.values()) {
            if (tech == TechnologyEnum.RESET) continue;
            Node techItem = techItems.get(tech);
            for (var child : tech.getLeadsToTechs()) {
                Node childItem = techItems.get(child);
                Line line = new Line(techItem.getTranslateX() + itemSize, techItem.getTranslateY() + itemSize / 2.0,
                        childItem.getTranslateX() + 0, childItem.getTranslateY() + itemSize / 2.0);
                line.setStyle("-fx-stroke:white");
                anchorPane.getChildren().add(line);
            }
        }
        for (var tech : TechnologyEnum.values()) {
            if (tech == TechnologyEnum.RESET) continue;
            Node techItem = techItems.get(tech);
            anchorPane.getChildren().add(techItem);
        }
    }

    public void back() {
        MenuStack.getInstance().popMenu();
    }
}
