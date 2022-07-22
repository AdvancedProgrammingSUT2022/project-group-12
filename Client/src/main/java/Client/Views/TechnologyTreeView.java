package Client.Views;

import Project.Enums.TechnologyEnum;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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
    private final List<List<TechnologyEnum>> techLevels = new ArrayList<>();
    private final List<TechnologyEnum> mark = new ArrayList<>();
    private final Map<TechnologyEnum, VBox> techItems = new HashMap<>();
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

    public void initialize() {
//        int i = 0;
//        for (var tech : roots) {
//            Image image = ImageLoader.getTechnologyImages().get(tech);
//            ImageView imageView = new ImageView(image);
//            imageView.setTranslateX(i * 100);
//            anchorPane.getChildren().add(imageView);
//            ++i;
//        }
        for (var tech : TechnologyEnum.values()) {
            if (tech == TechnologyEnum.RESET) continue;
            dfs(tech);
        }
        int maxNumber = 0;
        for (var level : techLevels) {
            maxNumber = Math.max(maxNumber, level.size());
        }

        int horizontalGap = width / techLevels.size();

        for (int level = 0; level < techLevels.size(); ++level) {
            int verticalGap = height / (techLevels.get(level).size() + 1);
            for (int i = 0; i < techLevels.get(level).size(); ++i) {
                TechnologyEnum tech = techLevels.get(level).get(i);
                Image image = ImageLoader.getTechnologyImages().get(tech);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(itemSize);
                imageView.setFitHeight(itemSize);
                Text text = new Text(tech.name().toLowerCase().replace('_', ' '));
                text.setStyle("-fx-font-size:12; -fx-fill:white");
                VBox vBox = new VBox(imageView, text);
                vBox.setTranslateX(level * horizontalGap);
                vBox.setTranslateY((i + 1) * verticalGap);
                vBox.setAlignment(Pos.CENTER);
                techItems.put(tech, vBox);
            }
        }
        for (var tech : TechnologyEnum.values()) {
            if (tech == TechnologyEnum.RESET) continue;
            VBox techItem = techItems.get(tech);
            for (var child : tech.getLeadsToTechs()) {
                VBox childItem = techItems.get(child);
                Line line = new Line(techItem.getTranslateX() + itemSize, techItem.getTranslateY() + itemSize / 2.0,
                        childItem.getTranslateX() + 0, childItem.getTranslateY() + itemSize / 2.0);
                line.setStyle("-fx-stroke:white");
                anchorPane.getChildren().add(line);
            }
        }
        for (var tech : TechnologyEnum.values()) {
            if (tech == TechnologyEnum.RESET) continue;
            VBox techItem = techItems.get(tech);
            anchorPane.getChildren().add(techItem);
        }
    }
}
