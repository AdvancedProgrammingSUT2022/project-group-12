package Client.Models;


import Client.App;
import Client.Utils.DatabaseQuerier;
import Client.Utils.SelectHandler;
import Client.Views.GameView;
import Client.Views.ImageLoader;
import Client.Views.Menu;
import Client.Views.MenuStack;
import Project.Enums.*;
import Project.Models.Cities.City;
import Project.Models.Location;
import Project.Models.Tiles.Tile;
import Project.Models.Units.CombatUnit;
import Project.Models.Units.NonCombatUnit;
import Project.Models.Units.Unit;
import Project.Utils.Constants;
import Project.Utils.Observer;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.util.HashMap;

public class Hex implements Observer<Tile> {

    private static final Image cityImage = new Image(App.getResourcePath("/images/resources/City center.png"));
    private static final Image ruinImage = new Image(App.getResourcePath("/images/resources/City ruins.png"));
    private static final HashMap<UnitEnum, Group> unitGroups = new HashMap<>();

    static {
        for (UnitEnum unitEnum : UnitEnum.values()) {
            unitGroups.put(unitEnum, createUnitGroup(unitEnum));
        }
    }

    private final Polygon polygon;
    private final double multiply;
     final int i;
     final int j;
    private final double w;
    private final double h;
    private final double verticalSpacing;
    private final double horizontalSpacing;
    private final double beginningOfLine;
    private Group group;
    private final Text positionText;
    private final ImageView cityImageView;
    private final ColorAdjust groupColorAdjust = new ColorAdjust();
    private final ImageView resourceImageView;
    private final ImageView ruinImageView;
    private final ImageView improvementImageView;
    private final ImageView featureImageView;

    public Hex(int i, int j) {
        int multiply = Constants.HEX_SIZE_MULTIPLY;
        this.i = i;
        this.j = j;
        this.group = new Group();
        this.multiply = multiply;
        this.verticalSpacing = j * 10 * multiply + 5;
        this.horizontalSpacing = 5 * Math.sqrt(3) * i * multiply + 5;
        beginningOfLine = i % 2 == 0 ? 0 : 15 * multiply;
        w = 20 * multiply;
        h = 10 * Math.sqrt(3) * multiply;
        this.polygon = new Polygon(
                initX(5), initY(0.0),
                initX(15.0), initY(0.0),
                initX(20.0), initY(5 * Math.sqrt(3)),
                initX(15.0), initY(10 * Math.sqrt(3)),
                initX(5.0), initY(10 * Math.sqrt(3)),
                initX(0.0), initY(5 * Math.sqrt(3)));
        this.group.setCursor(Cursor.HAND);
        this.group.setOnMouseEntered(mouseEvent -> {
            this.group.toFront();
            this.groupColorAdjust.setInput(new DropShadow(20, Color.BLACK));
        });
        this.group.setOnMouseExited(mouseEvent -> {
            this.groupColorAdjust.setInput(null);
        });
//        this.group.setOnMouseClicked(mouseEvent -> System.out.println(i + " " + j));
        this.group.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                Tile tile = DatabaseQuerier.getTileByLocation(new Location(i, j));
                MenuStack.getInstance().getCookies().setSelectedTile(tile);
                MenuStack.getInstance().pushMenu(Menu.loadFromFXML("TilePanelPage"));
            }
        });
        this.positionText = new Text(i + ", " + j);
        this.positionText.setLayoutX(this.getCenterX() - this.positionText.getBoundsInLocal().getWidth() / 2);
        this.positionText.setLayoutY(this.getCenterY() - this.multiply * 3);
        this.cityImageView = new ImageView(cityImage);
        this.cityImageView.setLayoutX(this.getCenterX() - cityImageView.getBoundsInLocal().getWidth() / 2);
        this.cityImageView.setLayoutY(this.getCenterY() - this.multiply * 3);
        this.ruinImageView = new ImageView(ruinImage);
        this.ruinImageView.setLayoutX(this.getCenterX() - ruinImageView.getBoundsInLocal().getWidth() / 2);
        this.ruinImageView.setLayoutY(this.getCenterY() - this.multiply * 3);
        setSelectScaleEffect(this.cityImageView);
        this.resourceImageView = new ImageView();
        this.resourceImageView.setLayoutX(this.getCenterX() - resourceImageView.getBoundsInLocal().getWidth() / 2 - this.multiply * 7);
        this.resourceImageView.setLayoutY(this.getCenterY());
        this.improvementImageView = new ImageView();
        this.improvementImageView.setLayoutX(this.getCenterX() - improvementImageView.getBoundsInLocal().getWidth() / 2 - this.multiply * 7);
        this.improvementImageView.setLayoutY(this.getCenterY() + multiply);
        this.featureImageView = new ImageView();
        this.featureImageView.setLayoutX(this.getCenterX() - featureImageView.getBoundsInLocal().getWidth() / 2 + this.multiply * 3);
        this.featureImageView.setLayoutY(this.getCenterY() + multiply);
        this.group.setEffect(this.groupColorAdjust);
    }

    private static Group createUnitGroup(UnitEnum unitEnum) {
        Group group = new Group();
        int unitsDistanceVertically = Constants.UNITS_DISTANCE_VERTICALLY;
        int unitDistanceHorizontally = Constants.UNITS_DISTANCE_HORIZONTALLY;
        int numberOfRowsOfUnits = 3;
        // todo: fix
//        if (this.getHealth() < Constants.UNIT_FULL_HEALTH / 3) {
//            numberOfRowsOfUnits = 1;
//        } else if (this.getHealth() < (Constants.UNIT_FULL_HEALTH * 2) / 3) {
//            numberOfRowsOfUnits = 2;
//        } else {
//            numberOfRowsOfUnits = 3;
//        }
        for (int i = numberOfRowsOfUnits - 1; i >= 0; i--) {
            for (int j = 0; j < 3; j++) {
                ImageView imageView = new ImageView(ImageLoader.getUnitImages().get(unitEnum));
                imageView.setLayoutX(unitDistanceHorizontally - unitDistanceHorizontally * j - imageView.getImage().getWidth() / 2);
                imageView.setLayoutY(unitsDistanceVertically - unitsDistanceVertically * i);
                group.getChildren().add(imageView);
            }
        }
        group.setCursor(Cursor.HAND);
        group.setOnMouseEntered(mouseEvent -> {
            group.setScaleX(1.1);
            group.setScaleY(1.1);
        });
        group.setOnMouseExited((MouseEvent) -> {
            group.setScaleX(1);
            group.setScaleY(1);
        });
        return group;
    }

    private static void setSelectScaleEffect(Node node) {
        node.setCursor(Cursor.HAND);
        node.setOnMouseEntered(mouseEvent -> {
            node.setScaleX(1.1);
            node.setScaleY(1.1);
        });
        node.setOnMouseExited((MouseEvent) -> {
            node.setScaleX(1);
            node.setScaleY(1);
        });
    }

    public Group getGroup() {
        return this.group;
    }

    private double initX(double x) {
        return x * multiply + w * j + verticalSpacing + beginningOfLine;
    }

    private double initY(double y) {
        return y * multiply + h * i - horizontalSpacing;
    }

    public double getMultiply() {
        return multiply;
    }

    public double getCenterX() {
        return (multiply * 10 + this.initX(0));
    }

    public double getCenterY() {
        return (25 * Math.sqrt(3) + this.initY(0));
    }

    private void addUnitToGroup(Unit unit) {
        Group unitGroup = unitGroups.get(unit.getUnitType());
        unitGroup.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                SelectHandler.sendSelectUnitRequest(unit);
                if (DatabaseQuerier.getSelectedUnit() == null) return;
                MenuStack.getInstance().getCookies().setSelectedUnit(unit);
                MenuStack.getInstance().pushMenu(Menu.loadFromFXML("UnitPanelPage"));
            }
        });
        this.group.getChildren().add(unitGroup);
        unitGroup.setTranslateY(this.getCenterY() + multiply * 4);
        unitGroup.setTranslateX(this.getCenterX() + multiply * 3 * (unit instanceof NonCombatUnit ? 1 : -1));
        if (unit.getState() != UnitStates.AWAKE) {
            System.out.println("Good State: " + unit.getState());
            ImageView unitStateImageView = new ImageView(App.getResourcePath("/images/unitstates/" + unit.getState().name().toLowerCase() + ".png"));
            unitStateImageView.setFitWidth(20);
            unitStateImageView.setFitHeight(20);
            unitStateImageView.setTranslateY(this.getCenterY() + multiply * 6.5);
            unitStateImageView.setTranslateX(multiply * 1 + this.getCenterX() + multiply * 3 * (unit instanceof NonCombatUnit ? 1 : -1));
            unitStateImageView.setOpacity(0.7);
            this.group.getChildren().add(unitStateImageView);
        }
    }

    @Override
    public void getNotified(Tile tile) {
//        System.out.println(tile.getLocation() + " got notified");
        updateHex(tile);
    }

    public void updateHex(Tile tile) {
        polygon.setFill(new ImagePattern(ImageLoader.getTerrainImages().get(tile.getTerrain().getTerrainType())));
        this.groupColorAdjust.setBrightness(tile.getState() == VisibilityEnum.VISIBLE ? 0 : -0.5);
        NonCombatUnit nonCombatUnit = tile.getNonCombatUnit();
        CombatUnit combatUnit = tile.getCombatUnit();
        City city = tile.getCity();

        this.group.getChildren().clear();
        this.group.getChildren().add(this.polygon);
        ResourceEnum resource = tile.getVisibleResource(GameView.getTechnologies());
        if (resource != null) this.addResourceToGroup(resource);
        if (!tile.getTerrain().getFeatures().isEmpty()) this.addFeatureToGroup(tile.getTerrain().getFeatures().get(0));
        if (!tile.getImprovements().isEmpty()) this.addImprovementToGroup(tile.getImprovements().get(0));
        if (tile.getCity() != null) this.addCityToGroup(city);
        if (nonCombatUnit != null) this.addUnitToGroup(nonCombatUnit);
        if (combatUnit != null) this.addUnitToGroup(combatUnit);
        if (tile.isARuin()) this.addRuinToGroup();
        this.group.getChildren().add(this.positionText);
    }

    private void addRuinToGroup() {
        this.group.getChildren().add(this.ruinImageView);
    }

    private void addResourceToGroup(ResourceEnum resource) {
        this.resourceImageView.setImage(ImageLoader.getResourceImages().get(resource));
        this.group.getChildren().add(this.resourceImageView);
    }

    private void addImprovementToGroup(ImprovementEnum improvement) {
        this.improvementImageView.setImage(ImageLoader.getImprovementImages().get(improvement));
        this.group.getChildren().add(this.improvementImageView);
    }

    private void addFeatureToGroup(FeatureEnum feature) {
        this.featureImageView.setImage(ImageLoader.getFeatureImages().get(feature));
        this.group.getChildren().add(this.featureImageView);
    }

    private void addCityToGroup(City city) {
        this.group.getChildren().add(this.cityImageView);
        this.cityImageView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                SelectHandler.sendSelectCityRequest(city);
                if (DatabaseQuerier.getSelectedCity() == null) return;
                MenuStack.getInstance().getCookies().setSelectedCity(city);
                MenuStack.getInstance().pushMenu(Menu.loadFromFXML("CityPanelPage"));
            }
        });
    }
}
