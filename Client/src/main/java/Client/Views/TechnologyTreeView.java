package Client.Views;

import Client.Utils.DatabaseQuerier;
import Project.Enums.TechnologyEnum;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
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
        drawButtons();
        drawTechTree();
    }

    private void drawButtons() {
        Button button = new Button();
        button.setText("back");
        button.setLayoutX(100);
        button.setLayoutY(370);
        button.setOnMouseClicked(mouseEvent -> back());
        pane.getChildren().add(button);
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
